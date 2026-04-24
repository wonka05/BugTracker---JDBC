import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class dashboardUI {

    static userDAO dao = new userDAO();
    static JTable table;
    static DefaultTableModel model;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Bug Tracking System - JDBC Project");
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7,1,5,5));

        JButton b1 = new JButton("Register User");
        JButton b2 = new JButton("View Users");
        JButton b3 = new JButton("Create Issue");
        JButton b4 = new JButton("View Issues");
        JButton b5 = new JButton("Update Status");
        JButton b6 = new JButton("Add Comment");
        JButton b7 = new JButton("Exit");

        panel.add(b1); panel.add(b2); panel.add(b3);
        panel.add(b4); panel.add(b5); panel.add(b6); panel.add(b7);

        model = new DefaultTableModel();
        table = new JTable(model);

        frame.add(panel, BorderLayout.WEST);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        // Register User
        b1.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Name");
            if (name == null || name.isEmpty()) return;

            String email = JOptionPane.showInputDialog("Email");
            if (email == null || email.isEmpty()) return;

            String pass = JOptionPane.showInputDialog("Password");
            if (pass == null || pass.isEmpty()) return;

            String role = JOptionPane.showInputDialog("Role");
            if (role == null || role.isEmpty()) return;

            dao.registerUser(name, email, pass, role);
            JOptionPane.showMessageDialog(frame, "User Added");
        });

        // View Users
        b2.addActionListener(e -> {
            String[] cols = {"ID","Name","Email","Role"};
            model.setDataVector(dao.getUsers(), cols);
        });

        // Create Issue
        b3.addActionListener(e -> {
            try {
                String title = JOptionPane.showInputDialog("Title");
                if (title == null) return;

                String desc = JOptionPane.showInputDialog("Description");
                String pr = JOptionPane.showInputDialog("Priority");
                String st = JOptionPane.showInputDialog("Status");

                int c = Integer.parseInt(JOptionPane.showInputDialog("Created By ID"));
                int a = Integer.parseInt(JOptionPane.showInputDialog("Assigned To ID"));

                dao.createIssue(title, desc, pr, st, c, a);
                JOptionPane.showMessageDialog(frame, "Issue Created");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input");
            }
        });

        // View Issues
        b4.addActionListener(e -> {
            String[] cols = {"ID","Title","Priority","Status","Created By","Assigned To"};
            model.setDataVector(dao.getIssues(), cols);
        });

        // Update Status
        b5.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Issue ID"));
                String st = JOptionPane.showInputDialog("New Status");

                dao.updateStatus(id, st);
                JOptionPane.showMessageDialog(frame, "Updated");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input");
            }
        });

        // Add Comment
        b6.addActionListener(e -> {
            try {
                int iid = Integer.parseInt(JOptionPane.showInputDialog("Issue ID"));
                int uid = Integer.parseInt(JOptionPane.showInputDialog("User ID"));
                String c = JOptionPane.showInputDialog("Comment");

                dao.addComment(iid, uid, c);
                JOptionPane.showMessageDialog(frame, "Comment Added");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input");
            }
        });

        b7.addActionListener(e -> System.exit(0));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}