package ui;

import models.Member;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MemberDashboard extends JFrame {
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

    private Member currentMember;

    public MemberDashboard(Member member) {
        this.currentMember = member;

        setContentPane(mainPanel);
        setTitle("Gym Membership System - Member Dashboard");
        setSize(680, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        populateMemberHeader();
        setupTablesAndCombos();
        setupListeners();
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
            lblPlanInfo.setText("Current Plan: Monthly Basic   |   Expiry Date: 2026-12-20   |   Status: Active");
        }
    }

    private void setupTablesAndCombos() {
        DefaultTableModel sessionModel = new DefaultTableModel(
                new Object[]{"Session ID", "Type", "Trainer", "Schedule Date", "Status"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        sessionModel.addRow(new Object[]{"SES-101", "Strength & Conditioning", "Coach Alex", "2026-11-22 09:00 AM", "Available"});
        sessionModel.addRow(new Object[]{"SES-102", "HIIT Cardio", "Coach Mia", "2026-11-23 04:00 PM", "Booked"});
        tblSessions.setModel(sessionModel);

        DefaultTableModel attendanceModel = new DefaultTableModel(
                new Object[]{"Attendance ID", "Session Date", "Session Type", "Status"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        attendanceModel.addRow(new Object[]{"ATT-001", "2026-11-18", "Strength & Conditioning", "Present"});
        tblAttendance.setModel(attendanceModel);

        cmbPlans.addItem("Monthly Basic (30 Days)");
        cmbPlans.addItem("Quarterly Pro (90 Days)");
        cmbPlans.addItem("Annual Elite (365 Days)");

        cmbPaymentMethod.addItem("Cash");
        cmbPaymentMethod.addItem("GCash");
        cmbPaymentMethod.addItem("Credit/Debit Card");

        updatePriceDisplay();
    }

    private void updatePriceDisplay() {
        int index = cmbPlans.getSelectedIndex();
        if (index == 0) lblPlanPrice.setText("Amount Due: PHP 1,200.00");
        else if (index == 1) lblPlanPrice.setText("Amount Due: PHP 3,200.00");
        else lblPlanPrice.setText("Amount Due: PHP 11,000.00");
    }

    private void setupListeners() {
        cmbPlans.addActionListener(e -> updatePriceDisplay());

        btnBookSession.addActionListener(e -> {
            int row = tblSessions.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a session from the table first.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String sessionId = tblSessions.getValueAt(row, 0).toString();
            tblSessions.setValueAt("Booked", row, 4);
            JOptionPane.showMessageDialog(this, "Session " + sessionId + " booked successfully!", "Booking Confirmed", JOptionPane.INFORMATION_MESSAGE);
        });

        btnCancelSession.addActionListener(e -> {
            int row = tblSessions.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a booked session to cancel.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String sessionId = tblSessions.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to cancel session " + sessionId + "?",
                    "Confirm Cancel Session",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                tblSessions.setValueAt("Available", row, 4);
                JOptionPane.showMessageDialog(this, "Session " + sessionId + " has been cancelled.", "Session Cancelled", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnRenewPay.addActionListener(e -> {
            String selectedPlan = (String) cmbPlans.getSelectedItem();
            String method = (String) cmbPaymentMethod.getSelectedItem();
            String amountText = lblPlanPrice.getText();

            JOptionPane.showMessageDialog(
                    this,
                    "Payment Receipt\nPlan: " + selectedPlan + "\n" + amountText + "\nMethod: " + method + "\nStatus: Paid",
                    "Membership Renewed",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION
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
