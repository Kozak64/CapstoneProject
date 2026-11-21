package ui;

import models.*;
import services.GymSystemManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MemberDashboard extends JFrame {
    private static final DateTimeFormatter DATE_TIME_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private JPanel mainPanel;
    private JLabel lblDashboardTitle;
    private JLabel lblPlanInfo;
    private JTabbedPane tabbedPane;

    private JPanel tabBookSession;
    private JTable tblSessions;
    private JButton btnBookSession;
    private JButton btnCancelSession;

    private JPanel tabRenewPlan;
    private JComboBox<String> cmbPlans;
    private JComboBox<String> cmbPaymentMethod;
    private JLabel lblPlanPrice;
    private JButton btnRenewPay;

    private JPanel tabAttendance;
    private JTable tblAttendance;

    private JButton btnLogout;

    private final Member currentMember;
    private final GymSystemManager manager = GymSystemManager.getInstance();

    private DefaultTableModel sessionModel;
    private DefaultTableModel attendanceModel;
    private List<Session> displayedSessions = new ArrayList<>();
    private List<MembershipPlan> availablePlans = new ArrayList<>();

    public MemberDashboard(Member member) {
        this.currentMember = member;

        setContentPane(mainPanel);
        setTitle("Gym Membership System - Member Dashboard");
        setSize(860, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        applyTheme();
        populateMemberHeader();
        setupTablesAndCombos();
        setupListeners();
        refreshAll();
    }

    private void applyTheme() {
        mainPanel.setBackground(UITheme.BG_GRAY);
        mainPanel.setBorder(new javax.swing.border.EmptyBorder(16, 20, 16, 20));

        lblDashboardTitle.setFont(UITheme.FONT_TITLE);
        lblDashboardTitle.setForeground(UITheme.TEXT_DARK);

        lblPlanInfo.setFont(UITheme.FONT_SUBTITLE);
        lblPlanInfo.setForeground(UITheme.TEXT_MUTED);

        lblPlanPrice.setFont(UITheme.FONT_BOLD);
        lblPlanPrice.setForeground(UITheme.PRIMARY_BLUE);

        UITheme.styleTabbedPane(tabbedPane);
        tabBookSession.setBackground(Color.WHITE);
        tabRenewPlan.setBackground(Color.WHITE);
        tabAttendance.setBackground(Color.WHITE);

        UITheme.styleTable(tblSessions);
        UITheme.styleTable(tblAttendance);

        cmbPlans.setFont(UITheme.FONT_BODY);
        cmbPlans.setBackground(Color.WHITE);
        cmbPaymentMethod.setFont(UITheme.FONT_BODY);
        cmbPaymentMethod.setBackground(Color.WHITE);

        UITheme.stylePrimaryButton(btnBookSession);
        UITheme.styleDangerButton(btnCancelSession);
        UITheme.stylePrimaryButton(btnRenewPay);
        UITheme.styleSecondaryButton(btnLogout);
    }

    public MemberDashboard() {
        this(null);
    }

    private void populateMemberHeader() {
        if (currentMember != null) {
            lblDashboardTitle.setText(currentMember.getDashBoardTitle());

            String planName = (currentMember.getMembershipPlan() != null)
                    ? currentMember.getMembershipPlan().getPlanName()
                    : "No Active Plan";
            String expiryText = (currentMember.getExpiryDate() != null)
                    ? currentMember.getExpiryDate().toString()
                    : "N/A";

            lblPlanInfo.setText("Current Plan: " + planName + "   |   Expiry Date: " + expiryText + "   |   Status: " + currentMember.getStatus());
        } else {
            lblDashboardTitle.setText("Member Portal - Guest Member");
            lblPlanInfo.setText("Preview Mode");
        }
    }

    private void setupTablesAndCombos() {
        sessionModel = new DefaultTableModel(
                new Object[]{"Session ID", "Type", "Trainer", "Schedule Date", "Status"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblSessions.setModel(sessionModel);

        attendanceModel = new DefaultTableModel(
                new Object[]{"Attendance ID", "Session Date", "Session Type", "Status"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblAttendance.setModel(attendanceModel);

        availablePlans = manager.getPlans();
        cmbPlans.removeAllItems();
        for (MembershipPlan plan : availablePlans) {
            cmbPlans.addItem(plan.getPlanName() + " (" + plan.getDurationMonths() + " mo)");
        }

        cmbPaymentMethod.removeAllItems();
        cmbPaymentMethod.addItem("Cash");
        cmbPaymentMethod.addItem("GCash");
        cmbPaymentMethod.addItem("Credit/Debit Card");

        updatePriceDisplay();
    }

    private void refreshAll() {
        populateMemberHeader();
        refreshSessionsTable();
        refreshAttendanceTable();
    }

    private void refreshSessionsTable() {
        sessionModel.setRowCount(0);
        displayedSessions = manager.getSessions();
        for (Session s : displayedSessions) {
            String trainerName = s.getTrainer() != null ? s.getTrainer().getName() : "TBA";
            String dateStr = s.getSessionDate() != null ? s.getSessionDate().format(DATE_TIME_FMT) : "";
            String displayStatus;
            if ("Cancelled".equalsIgnoreCase(s.getStatus())) {
                displayStatus = "Cancelled";
            } else if (currentMember != null && s.getEnrolledMembers().contains(currentMember)) {
                displayStatus = "Booked";
            } else if (s.getEnrolledMembers().size() >= s.getMaxCapacity()) {
                displayStatus = "Full";
            } else {
                displayStatus = "Available (" + s.getEnrolledMembers().size() + "/" + s.getMaxCapacity() + ")";
            }
            sessionModel.addRow(new Object[]{
                    "SES-" + s.getSessionID(), s.getType(), trainerName, dateStr, displayStatus
            });
        }
    }

    private void refreshAttendanceTable() {
        attendanceModel.setRowCount(0);
        if (currentMember == null) {
            return;
        }
        for (Attendance a : manager.getAttendanceForMember(currentMember)) {
            String dateStr = a.getCheckInTime() != null ? a.getCheckInTime().format(DATE_TIME_FMT) : "";
            String sessionType = a.getSession() != null ? a.getSession().getType() : "General Gym Check-In";
            attendanceModel.addRow(new Object[]{
                    "ATT-" + a.getAttendanceID(), dateStr, sessionType, a.isPresent() ? "Present" : "Absent"
            });
        }
    }

    private void updatePriceDisplay() {
        int index = cmbPlans.getSelectedIndex();
        if (index >= 0 && index < availablePlans.size()) {
            MembershipPlan plan = availablePlans.get(index);
            lblPlanPrice.setText(String.format("Amount Due: PHP %,.2f", plan.getPrice()));
        } else {
            lblPlanPrice.setText("Amount Due: PHP 0.00");
        }
    }

    private void setupListeners() {
        cmbPlans.addActionListener(e -> updatePriceDisplay());

        btnBookSession.addActionListener(e -> {
            if (currentMember == null) {
                JOptionPane.showMessageDialog(this, "No member account is currently loaded.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int row = tblSessions.getSelectedRow();
            if (row == -1 || row >= displayedSessions.size()) {
                JOptionPane.showMessageDialog(
                        this, "Please select a session from the table first.", "Selection Required", JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            Session session = displayedSessions.get(row);
            try {
                manager.bookSession(currentMember, session.getSessionID());
                refreshSessionsTable();
                JOptionPane.showMessageDialog(
                        this,
                        "Session SES-" + session.getSessionID() + " booked successfully!",
                        "Booking Confirmed",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (IllegalStateException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Cannot Book Session", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnCancelSession.addActionListener(e -> {
            if (currentMember == null) {
                JOptionPane.showMessageDialog(this, "No member account is currently loaded.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int row = tblSessions.getSelectedRow();
            if (row == -1 || row >= displayedSessions.size()) {
                JOptionPane.showMessageDialog(this, "Please select a booked session to cancel.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Session session = displayedSessions.get(row);
            if (!session.getEnrolledMembers().contains(currentMember)) {
                JOptionPane.showMessageDialog(this, "You are not booked in this session.", "Not Booked", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to cancel session SES-" + session.getSessionID() + "?",
                    "Confirm Cancel Session",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                manager.cancelBooking(currentMember, session.getSessionID());
                refreshSessionsTable();
                JOptionPane.showMessageDialog(this, "Session SES-" + session.getSessionID() + " has been cancelled.", "Session Cancelled", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnRenewPay.addActionListener(e -> {
            if (currentMember == null) {
                JOptionPane.showMessageDialog(
                        this, "No member account is currently loaded.", "Error", JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            int selectedIndex = cmbPlans.getSelectedIndex();
            String method = (String) cmbPaymentMethod.getSelectedItem();

            if (selectedIndex < 0 || selectedIndex >= availablePlans.size() || method == null) {
                JOptionPane.showMessageDialog(
                        this, "Please select a membership plan and payment method.", "Selection Required", JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            MembershipPlan selectedPlan = availablePlans.get(selectedIndex);

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Confirm Membership Payment?\n\n"
                            + "Plan: " + selectedPlan.getPlanName() + "\n"
                            + "Price: PHP " + String.format("%.2f", selectedPlan.getPrice()) + "\n"
                            + "Duration: " + selectedPlan.getDurationMonths() + " month(s)\n"
                            + "Payment Method: " + method,
                    "Confirm Payment",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            try {
                Payment payment = manager.processPayment(currentMember, selectedPlan, method);
                refreshAll();

                JOptionPane.showMessageDialog(
                        this,
                        "PAYMENT RECEIPT\n\n"
                                + "Payment ID: #" + payment.getPaymentID() + "\n"
                                + "Member: " + currentMember.getName() + "\n"
                                + "Plan: " + selectedPlan.getPlanName() + "\n"
                                + "Amount: PHP " + String.format("%.2f", payment.getAmount()) + "\n"
                                + "Duration: " + selectedPlan.getDurationMonths() + " month(s)\n"
                                + "Payment Method: " + payment.getMethod() + "\n"
                                + "Payment Date: " + payment.getPaymentDate() + "\n"
                                + "Expiry Date: " + currentMember.getExpiryDate() + "\n"
                                + "Status: " + currentMember.getStatus(),
                        "Payment Successful",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this, "Payment failed: " + ex.getMessage(), "Payment Error", JOptionPane.ERROR_MESSAGE
                );
            }
        });

        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginForm().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MemberDashboard().setVisible(true));
    }
}
