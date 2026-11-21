package ui;

import models.Admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminDashboard extends JFrame {
    private final Admin currentAdmin;

    private JLabel lblTitle;
    private JLabel lblSubtitle;

    private JTable tblMembers;
    private DefaultTableModel membersModel;

    private JTable tblTrainers;
    private DefaultTableModel trainersModel;

    private JTable tblPlans;
    private DefaultTableModel plansModel;

    private JTable tblPayments;
    private DefaultTableModel paymentsModel;
    private JLabel lblReportSummary;

    public AdminDashboard(Admin admin) {
        this.currentAdmin = admin;

        setTitle("Gym Membership System - Admin Dashboard");
        setSize(920, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        populateHeader();
        loadDummyData();
        updateSummary();
    }

    public AdminDashboard() {
        this(null);
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout(0, 16));
        root.setBackground(UITheme.BG_GRAY);
        root.setBorder(new EmptyBorder(16, 20, 16, 20));

        // --- Header Card ---
        JPanel headerCard = new JPanel(new BorderLayout(12, 4));
        UITheme.styleCard(headerCard, 16, 20);

        JPanel titleBlock = new JPanel(new GridLayout(2, 1, 0, 4));
        titleBlock.setOpaque(false);
        lblTitle = new JLabel("Admin Control Panel");
        lblTitle.setFont(UITheme.FONT_TITLE);
        lblTitle.setForeground(UITheme.TEXT_DARK);

        lblSubtitle = new JLabel("System Overview");
        lblSubtitle.setFont(UITheme.FONT_SUBTITLE);
        lblSubtitle.setForeground(UITheme.TEXT_MUTED);

        titleBlock.add(lblTitle);
        titleBlock.add(lblSubtitle);

        JButton btnLogout = new JButton("Logout");
        UITheme.styleSecondaryButton(btnLogout);
        btnLogout.addActionListener(e -> handleLogout());

        JPanel logoutWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        logoutWrap.setOpaque(false);
        logoutWrap.add(btnLogout);

        headerCard.add(titleBlock, BorderLayout.CENTER);
        headerCard.add(logoutWrap, BorderLayout.EAST);
        root.add(headerCard, BorderLayout.NORTH);

        // --- Main Tabbed Card ---
        JPanel bodyCard = new JPanel(new BorderLayout());
        UITheme.styleCard(bodyCard, 12, 16);

        JTabbedPane tabbedPane = new JTabbedPane();
        UITheme.styleTabbedPane(tabbedPane);
        tabbedPane.addTab("Manage Members", createMembersTab());
        tabbedPane.addTab("Manage Trainers", createTrainersTab());
        tabbedPane.addTab("Membership Plans", createPlansTab());
        tabbedPane.addTab("Payments & Reports", createPaymentsTab());

        bodyCard.add(tabbedPane, BorderLayout.CENTER);
        root.add(bodyCard, BorderLayout.CENTER);

        setContentPane(root);
    }

    private JPanel createMembersTab() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(12, 8, 8, 8));

        membersModel = new DefaultTableModel(
                new Object[]{"User ID", "Name", "Email", "Plan", "Expiry Date", "Status"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblMembers = new JTable(membersModel);
        UITheme.styleTable(tblMembers);

        JScrollPane scrollPane = new JScrollPane(tblMembers);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);

        JButton btnRemove = new JButton("Remove Member");
        UITheme.styleDangerButton(btnRemove);
        btnRemove.addActionListener(e -> removeSelectedRow(tblMembers, membersModel, "member"));

        JButton btnToggleStatus = new JButton("Toggle Active/Inactive");
        UITheme.styleSecondaryButton(btnToggleStatus);
        btnToggleStatus.addActionListener(e -> toggleMemberStatus());

        JButton btnAdd = new JButton("Add Member");
        UITheme.stylePrimaryButton(btnAdd);
        btnAdd.addActionListener(e -> handleAddMember());

        actions.add(btnRemove);
        actions.add(btnToggleStatus);
        actions.add(btnAdd);
        panel.add(actions, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createTrainersTab() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(12, 8, 8, 8));

        trainersModel = new DefaultTableModel(
                new Object[]{"User ID", "Trainer ID", "Name", "Specialization", "Schedule"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblTrainers = new JTable(trainersModel);
        UITheme.styleTable(tblTrainers);

        JScrollPane scrollPane = new JScrollPane(tblTrainers);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);

        JButton btnRemove = new JButton("Remove Trainer");
        UITheme.styleDangerButton(btnRemove);
        btnRemove.addActionListener(e -> removeSelectedRow(tblTrainers, trainersModel, "trainer"));

        JButton btnEditSchedule = new JButton("Edit Schedule");
        UITheme.styleSecondaryButton(btnEditSchedule);
        btnEditSchedule.addActionListener(e -> editTrainerSchedule());

        JButton btnAdd = new JButton("Add Trainer");
        UITheme.stylePrimaryButton(btnAdd);
        btnAdd.addActionListener(e -> handleAddTrainer());

        actions.add(btnRemove);
        actions.add(btnEditSchedule);
        actions.add(btnAdd);
        panel.add(actions, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createPlansTab() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(12, 8, 8, 8));

        plansModel = new DefaultTableModel(
                new Object[]{"Plan ID", "Plan Name", "Price (PHP)", "Duration (Months)", "Description"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblPlans = new JTable(plansModel);
        UITheme.styleTable(tblPlans);

        JScrollPane scrollPane = new JScrollPane(tblPlans);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);

        JButton btnDelete = new JButton("Delete Plan");
        UITheme.styleDangerButton(btnDelete);
        btnDelete.addActionListener(e -> removeSelectedRow(tblPlans, plansModel, "plan"));

        JButton btnEditPrice = new JButton("Edit Price");
        UITheme.styleSecondaryButton(btnEditPrice);
        btnEditPrice.addActionListener(e -> editPlanPrice());

        JButton btnAdd = new JButton("Add Plan");
        UITheme.stylePrimaryButton(btnAdd);
        btnAdd.addActionListener(e -> handleAddPlan());

        actions.add(btnDelete);
        actions.add(btnEditPrice);
        actions.add(btnAdd);
        panel.add(actions, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createPaymentsTab() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(12, 8, 8, 8));

        lblReportSummary = new JLabel("Total Revenue: PHP 0.00");
        lblReportSummary.setFont(UITheme.FONT_BOLD);
        lblReportSummary.setForeground(UITheme.PRIMARY_BLUE);
        panel.add(lblReportSummary, BorderLayout.NORTH);

        paymentsModel = new DefaultTableModel(
                new Object[]{"Payment ID", "Member", "Plan", "Amount (PHP)", "Method", "Date", "Status"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblPayments = new JTable(paymentsModel);
        UITheme.styleTable(tblPayments);

        JScrollPane scrollPane = new JScrollPane(tblPayments);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);

        JButton btnGenerateReport = new JButton("Generate Summary Report");
        UITheme.stylePrimaryButton(btnGenerateReport);
        btnGenerateReport.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                lblReportSummary.getText() + "\nMembers: " + membersModel.getRowCount()
                        + "\nTrainers: " + trainersModel.getRowCount(),
                "System Report",
                JOptionPane.INFORMATION_MESSAGE
        ));

        actions.add(btnGenerateReport);
        panel.add(actions, BorderLayout.SOUTH);

        return panel;
    }

    private void populateHeader() {
        if (currentAdmin != null) {
            lblTitle.setText(currentAdmin.getDashBoardTitle());
        } else {
            lblTitle.setText("Admin Control Panel - System Admin");
        }
    }

    private void loadDummyData() {
        membersModel.addRow(new Object[]{"25-1234-567", "Juan Dela Cruz", "member@gym.com", "Gold", "2026-12-21", "Active"});
        membersModel.addRow(new Object[]{"25-2045-812", "Maria Santos", "maria@gym.com", "Annual Elite", "2027-08-15", "Active"});
        membersModel.addRow(new Object[]{"25-3310-194", "Carlos Reyes", "carlos@gym.com", "Monthly Basic", "2026-10-01", "Expired"});

        trainersModel.addRow(new Object[]{"25-1231-123", 201, "Coach Alex", "Strength & Conditioning", "MWF 8AM-5PM"});
        trainersModel.addRow(new Object[]{"25-4412-009", 202, "Coach Mia", "HIIT & Cardio", "TThS 9AM-6PM"});

        plansModel.addRow(new Object[]{1, "Monthly Basic", 1200.00, 1, "Gym floor access"});
        plansModel.addRow(new Object[]{2, "Quarterly Pro", 3200.00, 3, "Gym + group classes"});
        plansModel.addRow(new Object[]{3, "Annual Elite", 11000.00, 12, "All access + PT sessions"});

        paymentsModel.addRow(new Object[]{501, "Juan Dela Cruz", "Monthly Basic", 1200.00, "GCash", "2026-11-20", "Completed"});
        paymentsModel.addRow(new Object[]{502, "Maria Santos", "Annual Elite", 11000.00, "Card", "2026-11-18", "Completed"});
    }

    private void updateSummary() {
        double total = 0;
        for (int i = 0; i < paymentsModel.getRowCount(); i++) {
            if ("Completed".equalsIgnoreCase(paymentsModel.getValueAt(i, 6).toString())) {
                total += Double.parseDouble(paymentsModel.getValueAt(i, 3).toString());
            }
        }
        String totalFmt = String.format("PHP %,.2f", total);
        lblSubtitle.setText("Members: " + membersModel.getRowCount()
                + "   |   Trainers: " + trainersModel.getRowCount()
                + "   |   Total Revenue: " + totalFmt);
        lblReportSummary.setText("Total Completed Revenue: " + totalFmt
                + "   (" + paymentsModel.getRowCount() + " transactions)");
    }

    private void removeSelectedRow(JTable table, DefaultTableModel model, String label) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a " + label + " first.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Remove selected " + label + "?", "Confirm Removal", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            model.removeRow(row);
            updateSummary();
        }
    }

    private void toggleMemberStatus() {
        int row = tblMembers.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a member first.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String current = membersModel.getValueAt(row, 5).toString();
        membersModel.setValueAt("Active".equalsIgnoreCase(current) ? "Inactive" : "Active", row, 5);
    }

    private void handleAddMember() {
        String name = JOptionPane.showInputDialog(this, "Member Full Name:");
        if (name == null || name.trim().isEmpty()) return;
        String email = JOptionPane.showInputDialog(this, "Member Email:");
        if (email == null || email.trim().isEmpty()) return;
        String id = "25-" + String.format("%04d", membersModel.getRowCount() + 10) + "-100";
        membersModel.addRow(new Object[]{id, name.trim(), email.trim(), "Monthly Basic", "2026-12-21", "Active"});
        updateSummary();
    }

    private void editTrainerSchedule() {
        int row = tblTrainers.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a trainer first.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String current = trainersModel.getValueAt(row, 4).toString();
        String updated = JOptionPane.showInputDialog(this, "New Schedule:", current);
        if (updated != null && !updated.trim().isEmpty()) {
            trainersModel.setValueAt(updated.trim(), row, 4);
        }
    }

    private void handleAddTrainer() {
        String name = JOptionPane.showInputDialog(this, "Trainer Full Name:");
        if (name == null || name.trim().isEmpty()) return;
        String spec = JOptionPane.showInputDialog(this, "Specialization:", "General Fitness");
        if (spec == null || spec.trim().isEmpty()) return;
        String id = "25-" + String.format("%04d", trainersModel.getRowCount() + 50) + "-200";
        int trainerId = 200 + trainersModel.getRowCount() + 1;
        trainersModel.addRow(new Object[]{id, trainerId, name.trim(), spec.trim(), "MWF 9AM-5PM"});
        updateSummary();
    }

    private void editPlanPrice() {
        int row = tblPlans.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a plan first.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String current = plansModel.getValueAt(row, 2).toString();
        String input = JOptionPane.showInputDialog(this, "New Price (PHP):", current);
        if (input != null && !input.trim().isEmpty()) {
            try {
                double price = Double.parseDouble(input.trim());
                if (price < 0) throw new NumberFormatException();
                plansModel.setValueAt(price, row, 2);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid non-negative number.", "Invalid Price", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleAddPlan() {
        String name = JOptionPane.showInputDialog(this, "Plan Name:");
        if (name == null || name.trim().isEmpty()) return;
        String priceStr = JOptionPane.showInputDialog(this, "Price (PHP):", "1500");
        if (priceStr == null) return;
        try {
            double price = Double.parseDouble(priceStr.trim());
            int nextId = plansModel.getRowCount() + 1;
            plansModel.addRow(new Object[]{nextId, name.trim(), price, 1, "Custom plan"});
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid price.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            new LoginForm().setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard().setVisible(true));
    }
}
