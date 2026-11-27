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

        UITheme.configureWindow(this, "Hussle Fitness - Admin Dashboard");

        initUI();
        populateHeader();
        reloadAll();
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

    private DefaultTableModel readOnlyModel(Object[] columns) {
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private JPanel createMembersTab() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(12, 8, 8, 8));

        membersModel = readOnlyModel(new Object[]{"User ID", "Name", "Email", "Plan", "Expiry Date", "Status"});
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

        trainersModel = readOnlyModel(new Object[]{"User ID", "Trainer ID", "Name", "Specialization", "Schedule"});
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

        plansModel = readOnlyModel(new Object[]{"Plan ID", "Plan Name", "Price (PHP)", "Duration (Months)", "Description"});
        tblPlans = new JTable(plansModel);
        UITheme.styleTable(tblPlans);

        JScrollPane scrollPane = new JScrollPane(tblPlans);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);

        JButton btnDelete = new JButton("Delete Plan");
        UITheme.styleDangerButton(btnDelete);
        btnDelete.addActionListener(e -> handleRemovePlan());

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

        paymentsModel = readOnlyModel(new Object[]{"Payment ID", "Member", "Plan", "Amount (PHP)", "Method", "Date", "Status"});
        tblPayments = new JTable(paymentsModel);
        UITheme.styleTable(tblPayments);

        JScrollPane scrollPane = new JScrollPane(tblPayments);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);

        JButton btnGenerateReport = new JButton("Generate Summary Report");
        UITheme.stylePrimaryButton(btnGenerateReport);
        btnGenerateReport.addActionListener(e -> UITheme.showMessageDialog(
                this,
                lblReportSummary.getText()
                        + "\nMembers: " + manager.getMembers().size()
                        + "\nTrainers: " + manager.getTrainers().size()
                        + "\nPlans: " + manager.getPlans().size(),
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

    private void reloadAll() {
        loadMembers();
        loadTrainers();
        loadPlans();
        loadPayments();
        updateSummary();
    }

    private void loadMembers() {
        membersModel.setRowCount(0);
        for (Member m : manager.getMembers()) {
            String planName = m.getMembershipPlan() != null ? m.getMembershipPlan().getPlanName() : "None";
            membersModel.addRow(new Object[]{
                    m.getUserID(), m.getName(), m.getEmail(),
                    planName, m.getExpiryDate(), m.getStatus()
            });
        }
    }

    private void loadTrainers() {
        trainersModel.setRowCount(0);
        for (Trainer t : manager.getTrainers()) {
            trainersModel.addRow(new Object[]{
                    t.getUserID(), t.getTrainerID(), t.getName(),
                    t.getSpecialization(), t.getSchedule()
            });
        }
    }

    private void loadPlans() {
        plansModel.setRowCount(0);
        for (MembershipPlan p : manager.getPlans()) {
            plansModel.addRow(new Object[]{
                    p.getPlanID(), p.getPlanName(), p.getPrice(),
                    p.getDurationMonths(), p.getDescription()
            });
        }
    }

    private void loadPayments() {
        paymentsModel.setRowCount(0);
        for (Payment p : manager.getPayments()) {
            String memberName = p.getMember() != null ? p.getMember().getName() : "Unknown";
            String planName = p.getPlan() != null ? p.getPlan().getPlanName() : "Unknown";
            paymentsModel.addRow(new Object[]{
                    p.getPaymentID(), memberName, planName,
                    p.getAmount(), p.getMethod(), p.getPaymentDate(), p.getStatus()
            });
        }
    }

    private void updateSummary() {
        double total = manager.getTotalCompletedRevenue();
        String totalFmt = String.format("PHP %,.2f", total);

        lblSubtitle.setText("Members: " + manager.getMembers().size()
                + "   |   Trainers: " + manager.getTrainers().size()
                + "   |   Total Revenue: " + totalFmt);
        lblReportSummary.setText("Total Completed Revenue: " + totalFmt
                + "   (" + manager.getPayments().size() + " transactions)");
    }

    private boolean requireSelection(JTable table, String label) {
        if (table.getSelectedRow() == -1) {
            UITheme.showMessageDialog(this, "Please select a " + label + " first.",
                    "Selection Required", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean confirmRemoval(String label) {
        return UITheme.showConfirmDialog(this, "Remove selected " + label + "?",
                "Confirm Removal", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    private void handleAddMember() {
        List<MembershipPlan> plans = manager.getPlans();
        if (plans.isEmpty()) {
            UITheme.showMessageDialog(this, "Create a membership plan first.",
                    "No Plans", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String name = UITheme.showInputDialog(this, "Member Full Name:");
        if (name == null || name.trim().isEmpty()) return;
        String email = UITheme.showInputDialog(this, "Member Email:");
        if (email == null || email.trim().isEmpty()) return;

        JComboBox<MembershipPlan> planBox = new JComboBox<>(plans.toArray(new MembershipPlan[0]));
        planBox.setRenderer((list, value, index, sel, focus) ->
                new JLabel(value == null ? "" : value.getPlanName()));
        int choice = UITheme.showConfirmDialog(this, planBox, "Select Plan",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice != JOptionPane.OK_OPTION) return;
        MembershipPlan plan = (MembershipPlan) planBox.getSelectedItem();
        if (plan == null) return;

        try {
            String id = manager.generateUserID();
            manager.addMember(id, name.trim(), email.trim(), "pass123", plan, "Active");
            reloadAll();
            UITheme.showMessageDialog(this,
                    "Member registered successfully!\n\nUser ID: " + id + "\nDefault Password: " + "pass123",
                    "Member Added", JOptionPane.INFORMATION_MESSAGE);
        } catch (DuplicateUserException ex) {
            UITheme.showMessageDialog(this, ex.getMessage(), "Duplicate Email Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRemoveMember() {
        if (!requireSelection(tblMembers, "member")) return;
        if (!confirmRemoval("member")) return;
        String userId = membersModel.getValueAt(tblMembers.getSelectedRow(), 0).toString();
        manager.removeMember(userId);
        reloadAll();
    }

    private void toggleMemberStatus() {
        if (!requireSelection(tblMembers, "member")) return;
        int row = tblMembers.getSelectedRow();
        String userId = membersModel.getValueAt(row, 0).toString();
        manager.toggleMemberStatus(userId);
        reloadAll();
        tblMembers.setRowSelectionInterval(row, row);
    }

    private void handleAddTrainer() {
        String name = UITheme.showInputDialog(this, "Trainer Full Name:");
        if (name == null || name.trim().isEmpty()) return;
        String defaultEmail = name.trim().toLowerCase().replaceAll("\\s+", ".") + "@gym.com";
        String email = UITheme.showInputDialog(this, "Trainer Email:", defaultEmail);
        if (email == null || email.trim().isEmpty()) return;
        String spec = UITheme.showInputDialog(this, "Specialization:", "General Fitness");
        if (spec == null || spec.trim().isEmpty()) return;

        try {
            String id = manager.generateUserID();
            manager.addTrainer(id, name.trim(), email.trim(), "train123", spec.trim(), "MWF 9AM-5PM");
            reloadAll();
            UITheme.showMessageDialog(this,
                    "Trainer added successfully!\n\nUser ID: " + id + "\nEmail: " + email.trim() + "\nDefault Password: " + "train123",
                    "Trainer Added", JOptionPane.INFORMATION_MESSAGE);
        } catch (DuplicateUserException | IllegalArgumentException ex) {
            UITheme.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCreateSession() {
        if (!requireSelection(tblTrainers, "trainer")) return;
        String trainerUserID = trainersModel.getValueAt(tblTrainers.getSelectedRow(), 0).toString();
        User u = manager.findUserById(trainerUserID);
        if (!(u instanceof Trainer trainer)) return;

        JTextField tfType = new JTextField(trainer.getSpecialization());
        JTextField tfDate = new JTextField(LocalDate.now().plusDays(1).atTime(9, 0).format(DATE_TIME_FMT));
        JTextField tfCap = new JTextField("15");

        JPanel form = new JPanel(new GridLayout(0, 2, 8, 6));
        form.add(new JLabel("Trainer:")); form.add(new JLabel(trainer.getName()));
        form.add(new JLabel("Session Type:")); form.add(tfType);
        form.add(new JLabel("Date & Time (yyyy-MM-dd HH:mm):")); form.add(tfDate);
        form.add(new JLabel("Max Capacity:")); form.add(tfCap);

        int result = UITheme.showConfirmDialog(this, form, "Create Training Session",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        try {
            LocalDateTime dateTime = LocalDateTime.parse(tfDate.getText().trim(), DATE_TIME_FMT);
            int capacity = Integer.parseInt(tfCap.getText().trim());
            String type = tfType.getText().trim();
            if (type.isEmpty()) throw new IllegalArgumentException("Session type cannot be empty.");

            Session created = manager.createSession(trainer, dateTime, type, capacity);
            UITheme.showMessageDialog(this,
                    "Created session SES-" + created.getSessionID() + " for " + trainer.getName() + ".",
                    "Session Created", JOptionPane.INFORMATION_MESSAGE);
        } catch (DateTimeParseException ex) {
            UITheme.showMessageDialog(this, "Use date format yyyy-MM-dd HH:mm (e.g. 2026-11-23 09:00).",
                    "Invalid Date", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            UITheme.showMessageDialog(this, ex.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRemoveTrainer() {
        if (!requireSelection(tblTrainers, "trainer")) return;
        if (!confirmRemoval("trainer")) return;
        String userId = trainersModel.getValueAt(tblTrainers.getSelectedRow(), 0).toString();
        manager.removeTrainer(userId);
        reloadAll();
    }

    private void editTrainerSchedule() {
        if (!requireSelection(tblTrainers, "trainer")) return;
        int row = tblTrainers.getSelectedRow();
        String userId = trainersModel.getValueAt(row, 0).toString();
        Trainer t = (Trainer) manager.findUserById(userId);

        String updated = UITheme.showInputDialog(this, "New Schedule:", t.getSchedule());
        if (updated != null && !updated.trim().isEmpty()) {
            manager.updateTrainerSchedule(userId, updated.trim());
            reloadAll();
            tblTrainers.setRowSelectionInterval(row, row);
        }
    }

    private void handleAddPlan() {
        String name = UITheme.showInputDialog(this, "Plan Name:");
        if (name == null || name.trim().isEmpty()) return;
        String priceStr = UITheme.showInputDialog(this, "Price (PHP):", "1500");
        if (priceStr == null) return;
        String monthsStr = UITheme.showInputDialog(this, "Duration (Months):", "1");
        if (monthsStr == null) return;
        String desc = UITheme.showInputDialog(this, "Description:", "Custom plan");
        if (desc == null) return;

        try {
            double price = Double.parseDouble(priceStr.trim());
            int months = Integer.parseInt(monthsStr.trim());
            if (price < 0 || months <= 0) throw new NumberFormatException();

            manager.addPlan(name.trim(), price, months, desc.trim());
            reloadAll();
        } catch (NumberFormatException ex) {
            UITheme.showMessageDialog(this,
                    "Price must be a non-negative number and duration a positive whole number.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRemovePlan() {
        if (!requireSelection(tblPlans, "plan")) return;
        if (!confirmRemoval("plan")) return;
        int planId = Integer.parseInt(plansModel.getValueAt(tblPlans.getSelectedRow(), 0).toString());
        manager.deletePlan(planId);
        reloadAll();
    }

    private void editPlanPrice() {
        if (!requireSelection(tblPlans, "plan")) return;
        int row = tblPlans.getSelectedRow();

        // Grabs the plan directly from the list by index instead of using findPlanById
        MembershipPlan plan = manager.getPlans().get(row);

        String input = UITheme.showInputDialog(this, "New Price (PHP):", plan.getPrice());

        if (input != null && !input.trim().isEmpty()) {
            try {
                double price = Double.parseDouble(input.trim());
                if (price < 0) throw new NumberFormatException();

                manager.updatePlanPrice(plan.getPlanID(), price);
                reloadAll();
                tblPlans.setRowSelectionInterval(row, row);
            } catch (NumberFormatException ex) {
                UITheme.showMessageDialog(this, "Please enter a valid non-negative number.",
                        "Invalid Price", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleLogout() {
        int confirm = UITheme.showConfirmDialog(this, "Are you sure you want to logout?",
                "Confirm Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            UITheme.switchFrame(this, new LoginForm());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard().setVisible(true));
    }
}
