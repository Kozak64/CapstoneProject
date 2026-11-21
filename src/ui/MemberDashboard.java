package ui;

import models.Member;
import models.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

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

    private final Member currentMember;

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

            if (currentMember.getMembershipPlan() == null) {
                JOptionPane.showMessageDialog(
                        this,"Please subscribe to a membership plan first.","Membership Required",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            if (!"Active".equalsIgnoreCase(currentMember.getStatus())) {
                JOptionPane.showMessageDialog(
                        this,"Your membership is not active.\n\n" + "Please renew or activate your membership before booking sessions.","Inactive Membership",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            if (currentMember.getExpiryDate() == null ||
                    currentMember.getExpiryDate().isBefore(java.time.LocalDate.now())) {

                JOptionPane.showMessageDialog(
                        this,"Your membership has expired.\n\n" + "Please renew your membership to book sessions.","Expired Membership",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int row = tblSessions.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(
                        this,"Please select a session from the table first.","Selection Required",JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            String status = tblSessions.getValueAt(row, 4).toString();

            if ("Booked".equalsIgnoreCase(status)) {
                JOptionPane.showMessageDialog(
                        this,"This session is already booked.","Already Booked",JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            String sessionId = tblSessions.getValueAt(row, 0).toString();

            tblSessions.setValueAt("Booked", row, 4);

            JOptionPane.showMessageDialog(
                    this,
                    "Session " + sessionId + " booked successfully!",
                    "Booking Confirmed",
                    JOptionPane.INFORMATION_MESSAGE
            );
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
                    "Are you sure you want to cancel session " + sessionId + "?","Confirm Cancel Session",JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                tblSessions.setValueAt("Available", row, 4);
                JOptionPane.showMessageDialog(this, "Session " + sessionId + " has been cancelled.", "Session Cancelled", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnRenewPay.addActionListener(e -> {
                if (currentMember == null) {
                    JOptionPane.showMessageDialog(
                            this, "No member account is currently loaded.", "Error",JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                int selectedIndex = cmbPlans.getSelectedIndex();
                String method = (String) cmbPaymentMethod.getSelectedItem();

                if (selectedIndex == -1 || method == null) {
                    JOptionPane.showMessageDialog(
                            this, "Please select a membership plan and payment method.", "Selection Required",JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                MembershipPlan selectedPlan;

                if (selectedIndex == 0) {
                    selectedPlan = new MembershipPlan(
                            1, "Monthly Basic", 1200.00, 1, "Basic gym membership"
                    );
                } else if (selectedIndex == 1) {
                    selectedPlan = new MembershipPlan(
                            2, "Quarterly Pro", 3200.00, 3, "Quarterly gym membership"
                    );
                } else {
                    selectedPlan = new MembershipPlan(
                            3, "Annual Elite", 11000.00, 12, "Annual gym membership"
                    );
                }

                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Confirm Membership Payment?\n\n" + "Plan: " + selectedPlan.getPlanName() + "\n" +"Price: PHP " + String.format("%.2f", selectedPlan.getPrice()) + "\n" +"Duration: " + selectedPlan.getDurationMonths() + " month(s)\n" +"Payment Method: " + method, "Confirm Payment",JOptionPane.YES_NO_OPTION
                );

                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }

                try {
                    Payment payment = new Payment(
                            1,
                            currentMember,
                            selectedPlan,
                            selectedPlan.getPrice(),
                            method
                    );

                    payment.processPayment();
                    populateMemberHeader();

                    JOptionPane.showMessageDialog(
                            this,
                            "PAYMENT RECEIPT\n\n" + "Member: " + currentMember.getName() + "\n" + "Plan: " + selectedPlan.getPlanName() + "\n" + "Amount: PHP " + String.format("%.2f", payment.getAmount()) + "\n" + "Duration: " + selectedPlan.getDurationMonths() + " month(s)\n" + "Payment Method: " + payment.getMethod() + "\n" + "Payment Date: " + payment.getPaymentDate() + "\n" + "Expiry Date: " + currentMember.getExpiryDate() + "\n" + "Status: " + currentMember.getStatus(), "Payment Successful", JOptionPane.INFORMATION_MESSAGE
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
