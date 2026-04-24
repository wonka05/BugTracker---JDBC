import java.sql.*;
import java.util.ArrayList;

public class userDAO {

    private Connection getConnection() throws Exception {
        String password = System.getenv("DB_PASSWORD");

        if (password == null) {
            password = "your_password"; // fallback for local run
        }

        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/bugtracker",
                "root",
                password
        );
    }

    public void registerUser(String name, String email, String password, String role) {
        try (Connection con = getConnection()) {
            String query = "INSERT INTO users(name, email, password, role) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, role);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object[][] getUsers() {
        try (Connection con = getConnection()) {
            String query = "SELECT * FROM users";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            ArrayList<Object[]> list = new ArrayList<>();

            while (rs.next()) {
                list.add(new Object[]{
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("role")
                });
            }

            return list.toArray(new Object[0][]);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Object[0][0];
    }

    public void createIssue(String title, String desc, String priority, String status, int createdBy, int assignedTo) {
        try (Connection con = getConnection()) {
            String query = "INSERT INTO issues(title, description, priority, status, created_by, assigned_to) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, desc);
            ps.setString(3, priority);
            ps.setString(4, status);
            ps.setInt(5, createdBy);
            ps.setInt(6, assignedTo);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object[][] getIssues() {
        try (Connection con = getConnection()) {
            String query = "SELECT i.issue_id, i.title, i.priority, i.status, " +
                    "u1.name AS created_by, u2.name AS assigned_to " +
                    "FROM issues i " +
                    "JOIN users u1 ON i.created_by = u1.user_id " +
                    "JOIN users u2 ON i.assigned_to = u2.user_id";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            ArrayList<Object[]> list = new ArrayList<>();

            while (rs.next()) {
                list.add(new Object[]{
                        rs.getInt("issue_id"),
                        rs.getString("title"),
                        rs.getString("priority"),
                        rs.getString("status"),
                        rs.getString("created_by"),
                        rs.getString("assigned_to")
                });
            }

            return list.toArray(new Object[0][]);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Object[0][0];
    }

    public void updateStatus(int issueId, String status) {
        try (Connection con = getConnection()) {
            String query = "UPDATE issues SET status=? WHERE issue_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, status);
            ps.setInt(2, issueId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addComment(int issueId, int userId, String comment) {
        try (Connection con = getConnection()) {
            String query = "INSERT INTO comments(issue_id, user_id, comment) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, issueId);
            ps.setInt(2, userId);
            ps.setString(3, comment);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}