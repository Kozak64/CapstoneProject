package ui;

import exceptions.DuplicateUserException;
import models.*;
import services.GymSystemManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class AdminDashboard extends JFrame {
    private static final DateTimeFormatter DATE_TIME_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final Admin currentAdmin;
    private final GymSystemManager manager = GymSystemManager.getInstance();

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
        refreshAllTables();
    }

    public AdminDashboard() {
        this(null);
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout(0, 16));
        root.setBackground(UITheme.BG_GRAY);
        root.setBorder(new EmptyBorder(16, 20, 16, 20));

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
        btnRemove.addActionListener(e -> handleRemoveMember());

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
        btnRemove.addActionListener(e -> handleRemoveTrainer());

        JButton btnEditSchedule = new JButton("Edit Schedule");
        UITheme.styleSecondaryButton(btnEditSchedule);
        btnEditSchedule.addActionListener(e -> editTrainerSchedule());

        JButton btnCreateSession = new JButton("Create Session");
        UITheme.styleSecondaryButton(btnCreateSession);
        btnCreateSession.addActionListener(e -> handleCreateSession());

        JButton btnAdd = new JButton("Add Trainer");
        UITheme.stylePrimaryButton(btnAdd);
        btnAdd.addActionListener(e -> handleAddTrainer());

        actions.add(btnRemove);
        actions.add(btnEditSchedule);
        actions.add(btnCreateSession);
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
        btnDelete.addActionListener(e -> handleDeletePlan());

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
                lblReportSummary.getText()
                        + "\nMembers: " + manager.getMembers().size()
                        + "\nTrainers: " + manager.getTrainers().size()
                        + "\nPlans: " + manager.getPlans().size()
                        + "\nSessions: " + manager.getSessions().size(),
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

    private void refreshAllTables() {
        refreshMembersTable();
        refreshTrainersTable();
        refreshPlansTable();
        refreshPaymentsTable();
        updateSummary();
    }

    private void refreshMembersTable() {
        membersModel.setRowCount(0);
        for (Member m : manager.getMembers()) {
            String planName = m.getMembershipPlan() != null ? m.getMembershipPlan().getPlanName() : "None";
            String expiry = m.getExpiryDate() != null ? m.getExpiryDate().toString() : "N/A";
            membersModel.addRow(new Object[]{
                    m.getUserID(), m.getName(), m.getEmail(), planName, expiry, m.getStatus()
            });
        }
    }

    private void refreshTrainersTable() {
        trainersModel.setRowCount(0);
        for (Trainer t : manager.getTrainers()) {
            trainersModel.addRow(new Object[]{
                    t.getUserID(), t.getTrainerID(), t.getName(), t.getSpecialization(), t.getSchedule()
            });
        }
    }

    private void refreshPlansTable() {
        plansModel.setRowCount(0);
        for (MembershipPlan p : manager.getPlans()) {
            plansModel.addRow(new Object[]{
                    p.getPlanID(), p.getPlanName(), String.format("%.2f", p.getPrice()),
                    p.getDurationMonths(), p.getDescription()
            });
        }
    }

    private void refreshPaymentsTable() {
        paymentsModel.setRowCount(0);
        for (Payment p : manager.getPayments()) {
            String memberName = p.getMember() != null ? p.getMember().getName() : "Unknown";
            String planName = p.getPlan() != null ? p.getPlan().getPlanName() : "N/A";
            String date = p.getPaymentDate() != null ? p.getPaymentDate().toString() : "";
            paymentsModel.addRow(new Object[]{
                    p.getPaymentID(), memberName, planName,
                    String.format("%.2f", p.getAmount()), p.getMethod(), date, p.getStatus()
            });
        }
    }

    private void updateSummary() {
        String totalFmt = String.format("PHP %,.2f", manager.getTotalCompletedRevenue());
        lblSubtitle.setText("Members: " + manager.getMembers().size()
                + "   |   Trainers: " + manager.getTrainers().size()
                + "   |   Total Revenue: " + totalFmt);
        lblReportSummary.setText("Total Completed Revenue: " + totalFmt
                + "   (" + manager.getPayments().size() + " transactions)");
    }

    private String suggestUserID(int suffix) {
        int mid = 1001;
        String candidate;
        do {
            candidate = String.format("25-%04d-%03d", mid++, suffix);
        } while (manager.userIDExists(candidate));
        return candidate;
    }

    private void handleRemoveMember() {
        int row = tblMembers.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a member first.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String userID = membersModel.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Remove member " + userID + "?", "Confirm Removal", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            manager.removeMember(userID);
            refreshAllTables();
        }
    }

    private void toggleMemberStatus() {
        int row = tblMembers.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a member first.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String userID = membersModel.getValueAt(row, 0).toString();
        manager.toggleMemberStatus(userID);
        refreshMembersTable();
    }

    private void handleAddMember() {
        JTextField tfId = new JTextField(suggestUserID(100));
        JTextField tfName = new JTextField();
        JTextField tfEmail = new JTextField();
        JPasswordField tfPass = new JPasswordField("member123"); UITheme.stylePasswordField(tfPass);

        List<MembershipPlan> plans = manager.getPlans();
        JComboBox<String> cmbPlan = new JComboBox<>();
        cmbPlan.addItem("None (Inactive)");
        for (MembershipPlan p : plans) {
            cmbPlan.addItem(p.getPlanName() + " (PHP " + String.format("%.2f", p.getPrice()) + ")");
        }

        JPanel form = new JPanel(new GridLayout(0, 2, 8, 6));
        form.add(new JLabel("User ID (XX-XXXX-XXX):")); form.add(tfId);
        form.add(new JLabel("Full Name:")); form.add(tfName);
        form.add(new JLabel("Email:")); form.add(tfEmail);
        form.add(new JLabel("Password:")); form.add(tfPass);
        form.add(new JLabel("Membership Plan:")); form.add(cmbPlan);

        int result = JOptionPane.showConfirmDialog(this, form, "Add Member", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        try {
            int idx = cmbPlan.getSelectedIndex();
            MembershipPlan selectedPlan = idx > 0 ? plans.get(idx - 1) : null;
            String status = selectedPlan != null ? "Active" : "Inactive";
            manager.addMember(
                    tfId.getText().trim(),
                    tfName.getText().trim(),
                    tfEmail.getText().trim(),
                    new String(tfPass.getPassword()),
                    selectedPlan,
                    status
            );
            refreshAllTables();
        } catch (DuplicateUserException | IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Could Not Add Member", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRemoveTrainer() {
        int row = tblTrainers.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a trainer first.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String userID = trainersModel.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Remove trainer " + userID + "?", "Confirm Removal", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            manager.removeTrainer(userID);
            refreshAllTables();
        }
    }

    private void editTrainerSchedule() {
        int row = tblTrainers.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a trainer first.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String userID = trainersModel.getValueAt(row, 0).toString();
        String current = trainersModel.getValueAt(row, 4).toString();
        String updated = JOptionPane.showInputDialog(this, "New Schedule:", current);
        if (updated != null && !updated.trim().isEmpty()) {
            manager.updateTrainerSchedule(userID, updated.trim());
            refreshTrainersTable();
        }
    }

    private void handleAddTrainer() {
        JTextField tfId = new JTextField(suggestUserID(200));
        JTextField tfName = new JTextField();
        JTextField tfEmail = new JTextField();
        JPasswordField tfPass = new JPasswordField("trainer123"); UITheme.stylePasswordField(tfPass);
        JTextField tfSpec = new JTextField("General Fitness");
        JTextField tfSched = new JTextField("MWF 9AM-5PM");

        JPanel form = new JPanel(new GridLayout(0, 2, 8, 6));
        form.add(new JLabel("User ID (XX-XXXX-XXX):")); form.add(tfId);
        form.add(new JLabel("Full Name:")); form.add(tfName);
        form.add(new JLabel("Email:")); form.add(tfEmail);
        form.add(new JLabel("Password:")); form.add(tfPass);
        form.add(new JLabel("Specialization:")); form.add(tfSpec);
        form.add(new JLabel("Schedule:")); form.add(tfSched);

        int result = JOptionPane.showConfirmDialog(this, form, "Add Trainer", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        try {
            manager.addTrainer(
                    tfId.getText().trim(),
                    tfName.getText().trim(),
                    tfEmail.getText().trim(),
                    new String(tfPass.getPassword()),
                    tfSpec.getText().trim(),
                    tfSched.getText().trim()
            );
            refreshAllTables();
        } catch (DuplicateUserException | IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Could Not Add Trainer", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCreateSession() {
        int row = tblTrainers.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a trainer first.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String trainerUserID = trainersModel.getValueAt(row, 0).toString();
        User u = manager.findUserById(trainerUserID);
        if (!(u instanceof Trainer)) return;
        Trainer trainer = (Trainer) u;

        JTextField tfType = new JTextField(trainer.getSpecialization());
        JTextField tfDate = new JTextField(LocalDate.now().plusDays(1).atTime(9, 0).format(DATE_TIME_FMT));
        JTextField tfCap = new JTextField("15");

        JPanel form = new JPanel(new GridLayout(0, 2, 8, 6));
        form.add(new JLabel("Trainer:")); form.add(new JLabel(trainer.getName()));
        form.add(new JLabel("Session Type:")); form.add(tfType);
        form.add(new JLabel("Date & Time (yyyy-MM-dd HH:mm):")); form.add(tfDate);
        form.add(new JLabel("Max Capacity:")); form.add(tfCap);

        int result = JOptionPane.showConfirmDialog(this, form, "Create Training Session", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        try {
            LocalDateTime dateTime = LocalDateTime.parse(tfDate.getText().trim(), DATE_TIME_FMT);
            int capacity = Integer.parseInt(tfCap.getText().trim());
            String type = tfType.getText().trim();
            if (type.isEmpty()) throw new IllegalArgumentException("Session type cannot be empty.");

            Session created = manager.createSession(trainer, dateTime, type, capacity);
            JOptionPane.showMessageDialog(this, "Created session SES-" + created.getSessionID() + " for " + trainer.getName() + ".", "Session Created", JOptionPane.INFORMATION_MESSAGE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Use date format yyyy-MM-dd HH:mm (e.g. 2026-11-23 09:00).", "Invalid Date", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDeletePlan() {
        int row = tblPlans.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a plan first.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int planID = Integer.parseInt(plansModel.getValueAt(row, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(this, "Delete plan #" + planID + "?", "Confirm Removal", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            manager.deletePlan(planID);
            refreshPlansTable();
        }
    }

    private void editPlanPrice() {
        int row = tblPlans.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a plan first.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int planID = Integer.parseInt(plansModel.getValueAt(row, 0).toString());
        String current = plansModel.getValueAt(row, 2).toString();
        String input = JOptionPane.showInputDialog(this, "New Price (PHP):", current);
        if (input != null && !input.trim().isEmpty()) {
            try {
                double price = Double.parseDouble(input.trim());
                manager.updatePlanPrice(planID, price);
                refreshPlansTable();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid non-negative number.", "Invalid Price", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleAddPlan() {
        JTextField tfName = new JTextField();
        JTextField tfPrice = new JTextField("1500");
        JTextField tfDuration = new JTextField("1");
        JTextField tfDesc = new JTextField("Custom plan");

        JPanel form = new JPanel(new GridLayout(0, 2, 8, 6));
        form.add(new JLabel("Plan Name:")); form.add(tfName);
        form.add(new JLabel("Price (PHP):")); form.add(tfPrice);
        form.add(new JLabel("Duration (Months):")); form.add(tfDuration);
        form.add(new JLabel("Description:")); form.add(tfDesc);

        int result = JOptionPane.showConfirmDialog(this, form, "Add Membership Plan", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        try {
            String name = tfName.getText().trim();
            if (name.isEmpty()) throw new IllegalArgumentException("Plan name cannot be empty.");
            double price = Double.parseDouble(tfPrice.getText().trim());
            int duration = Integer.parseInt(tfDuration.getText().trim());
            manager.addPlan(name, price, duration, tfDesc.getText().trim());
            refreshPlansTable();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Invalid plan details: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
