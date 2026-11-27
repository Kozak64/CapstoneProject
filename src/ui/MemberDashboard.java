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
    private JScrollPane dashboardScrollPane;
    private JPanel scrollContentPanel;
    private JPanel heroPanel;
    private JLabel lblLogo;
    private JLabel lblHeroHeadline;
    private JLabel lblHeroSubheadline;

    private JLabel lblDashboardTitle;
    private JLabel lblPlanInfo = new JLabel();
    private JPanel welcomeCardPanel;
    private JLabel lblWelcomeBody;
    private JPanel cardCurrentPlan;
    private JPanel cardExpiryDate;
    private JPanel cardStatus;
    private JLabel lblPlanValue;
    private JLabel lblExpiryValue;
    private JLabel lblStatusValue;

    private JTabbedPane tabbedPane;

    private JPanel tabBookSession;
    private JScrollPane sessionsScrollPane;
    private JTable tblSessions;
    private JButton btnBookSession;
    private JButton btnCancelSession;

    private JPanel tabRenewPlan;
    private JComboBox<String> cmbPlans;
    private JComboBox<String> cmbPaymentMethod;
    private JPanel priceCardPanel;
    private JLabel lblPlanPrice;
    private JButton btnRenewPay;

    private JPanel tabAttendance;
    private JScrollPane attendanceScrollPane;
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
        UITheme.configureAdaptiveFrame(this, "Hussle Fitness - Member Dashboard");

        applyTheme();
        populateMemberHeader();
        setupTablesAndCombos();
        setupListeners();
        refreshAll();
    }

    public MemberDashboard() {
        this(null);
    }

    private void createUIComponents() {
        scrollContentPanel = new UITheme.ScrollableDarkPanel();
        heroPanel = new UITheme.HeroHeaderPanel(
                UITheme.loadImage(getClass(), UITheme.GYM_HERO_BG_ASSET),
                UITheme.HeroHeaderPanel.DEFAULT_HERO_HEIGHT
        );
        lblLogo = UITheme.createHeaderLogoLabel(
                getClass(),
                UITheme.LOGO_STACKED_ASSET,
                UITheme.LOGO_HORIZONTAL_ASSET,
                46
        );
        lblHeroHeadline = new UITheme.HeroHeadlineLabel("HUSS.", " HUSS. HUSS.");
        lblHeroSubheadline = new UITheme.HeroSubheadlineLabel(
                "In this town, everybody goes out partying except us. Ready to join the few?",
                520
        );
        btnLogout = new UITheme.OutlineAccentButton(
                "LOGOUT",
                UITheme.LOGOUT_BORDER,
                UITheme.TEXT_LIGHT,
                36,
                104,
                1.0f,
                16
        );

        welcomeCardPanel = new UITheme.DarkGlassCard();
        lblWelcomeBody = new UITheme.WrappingParagraphLabel(
                "Built for the ones who show up when it counts. "
                        + "Lock in your next class, hit the floor with our coaches, and track your attendance as you outwork yesterday and level up rep by rep.",
                "Huss. Huss. Huss.",
                UITheme.FONT_INTRO_BODY,
                UITheme.TEXT_LIGHT,
                UITheme.ACCENT_YELLOW,
                1.38f
        );
        cardCurrentPlan = new UITheme.DarkGlassCard();
        cardExpiryDate = new UITheme.DarkGlassCard();
        cardStatus = new UITheme.DarkGlassCard();

        tabBookSession = new UITheme.DarkGlassCard();
        tabRenewPlan = new UITheme.DarkGlassCard();
        tabAttendance = new UITheme.DarkGlassCard();
        priceCardPanel = new UITheme.DarkInsetPanel();

        btnCancelSession = new UITheme.OutlineAccentButton(
                "CANCEL SESSION",
                UITheme.STATUS_RED,
                UITheme.STATUS_RED,
                42,
                172,
                1.5f,
                20
        );
        btnBookSession = new UITheme.GradientButton(
                "BOOK SELECTED SESSION \u2192",
                42,
                244
        );
        btnRenewPay = new UITheme.GradientButton(
                "PROCESS PAYMENT / RENEW",
                44,
                260
        );
    }

    private void applyTheme() {
        mainPanel.setBackground(UITheme.BG_NAVY);
        UITheme.styleDarkScrollPane(dashboardScrollPane, false);
        dashboardScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        UITheme.installSmoothScrolling(dashboardScrollPane, sessionsScrollPane, attendanceScrollPane);
        if (heroPanel instanceof UITheme.HeroHeaderPanel heroHeader) {
            heroHeader.attachScrollFade(dashboardScrollPane);
        }

        lblDashboardTitle.setFont(UITheme.FONT_PORTAL_TITLE);
        lblDashboardTitle.setForeground(UITheme.TEXT_WHITE);

        lblPlanPrice.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblPlanPrice.setForeground(UITheme.ACCENT_YELLOW);

        UITheme.styleDarkTabbedPane(tabbedPane);
        UITheme.styleDarkComboBox(cmbPlans);
        UITheme.styleDarkComboBox(cmbPaymentMethod);
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
            String statusText = currentMember.getStatus() != null ? currentMember.getStatus() : "Inactive";

            lblPlanValue.setText(planName);
            lblExpiryValue.setText(expiryText);
            lblStatusValue.setText(statusText);
            lblStatusValue.setIcon(new UITheme.StatusDotIcon(resolveStatusColor(statusText), 14));

            lblPlanInfo.setText("Current Plan: " + planName + "   |   Expiry Date: " + expiryText + "   |   Status: " + statusText);
        } else {
            lblDashboardTitle.setText("Member Portal - Guest Member");
            lblPlanValue.setText("No Active Plan");
            lblExpiryValue.setText("N/A");
            lblStatusValue.setText("Preview Mode");
            lblStatusValue.setIcon(new UITheme.StatusDotIcon(UITheme.STATUS_AMBER, 14));
            lblPlanInfo.setText("Preview Mode");
        }
    }

    private Color resolveStatusColor(String statusText) {
        if (statusText == null) {
            return UITheme.TEXT_SLATE;
        }
        String lower = statusText.trim().toLowerCase();
        if ("active".equals(lower)) {
            return UITheme.STATUS_GREEN;
        }
        if ("expired".equals(lower) || "pending".equals(lower)) {
            return UITheme.STATUS_AMBER;
        }
        return UITheme.STATUS_RED;
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
        UITheme.styleDarkTable(tblSessions, sessionsScrollPane, 4);
        tblSessions.getColumnModel().getColumn(0).setPreferredWidth(110);
        tblSessions.getColumnModel().getColumn(1).setPreferredWidth(180);
        tblSessions.getColumnModel().getColumn(2).setPreferredWidth(180);
        tblSessions.getColumnModel().getColumn(3).setPreferredWidth(160);
        tblSessions.getColumnModel().getColumn(4).setPreferredWidth(170);

        attendanceModel = new DefaultTableModel(
                new Object[]{"Attendance ID", "Session Date", "Session Type", "Status"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblAttendance.setModel(attendanceModel);
        UITheme.styleDarkTable(tblAttendance, attendanceScrollPane, 3);
        tblAttendance.getColumnModel().getColumn(0).setPreferredWidth(140);
        tblAttendance.getColumnModel().getColumn(1).setPreferredWidth(190);
        tblAttendance.getColumnModel().getColumn(2).setPreferredWidth(260);
        tblAttendance.getColumnModel().getColumn(3).setPreferredWidth(150);

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
                UITheme.showMessageDialog(this, "No member account is currently loaded.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int row = tblSessions.getSelectedRow();
            if (row == -1 || row >= displayedSessions.size()) {
                UITheme.showMessageDialog(
                        this, "Please select a session from the table first.", "Selection Required", JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            Session session = displayedSessions.get(row);
            try {
                manager.bookSession(currentMember, session.getSessionID());
                refreshSessionsTable();
                UITheme.showMessageDialog(
                        this,
                        "Session SES-" + session.getSessionID() + " booked successfully!",
                        "Booking Confirmed",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (IllegalStateException | IllegalArgumentException ex) {
                UITheme.showMessageDialog(this, ex.getMessage(), "Cannot Book Session", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnCancelSession.addActionListener(e -> {
            if (currentMember == null) {
                UITheme.showMessageDialog(this, "No member account is currently loaded.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int row = tblSessions.getSelectedRow();
            if (row == -1 || row >= displayedSessions.size()) {
                UITheme.showMessageDialog(this, "Please select a booked session to cancel.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Session session = displayedSessions.get(row);
            if (!session.getEnrolledMembers().contains(currentMember)) {
                UITheme.showMessageDialog(this, "You are not booked in this session.", "Not Booked", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirm = UITheme.showConfirmDialog(
                    this,
                    "Are you sure you want to cancel session SES-" + session.getSessionID() + "?",
                    "Confirm Cancel Session",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                manager.cancelBooking(currentMember, session.getSessionID());
                refreshSessionsTable();
                UITheme.showMessageDialog(this, "Session SES-" + session.getSessionID() + " has been cancelled.", "Session Cancelled", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnRenewPay.addActionListener(e -> {
            if (currentMember == null) {
                UITheme.showMessageDialog(
                        this, "No member account is currently loaded.", "Error", JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            int selectedIndex = cmbPlans.getSelectedIndex();
            String method = (String) cmbPaymentMethod.getSelectedItem();

            if (selectedIndex < 0 || selectedIndex >= availablePlans.size() || method == null) {
                UITheme.showMessageDialog(
                        this, "Please select a membership plan and payment method.", "Selection Required", JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            MembershipPlan selectedPlan = availablePlans.get(selectedIndex);

            JTextField tfAmount = new JTextField(String.format("%.2f", selectedPlan.getPrice()));
            UITheme.styleInput(tfAmount);

            JPanel confirmPanel = new JPanel(new GridLayout(0, 2, 8, 6));
            confirmPanel.add(new JLabel("Plan:"));
            confirmPanel.add(new JLabel(selectedPlan.getPlanName()));
            confirmPanel.add(new JLabel("Required Price:"));
            confirmPanel.add(new JLabel(String.format("PHP %.2f", selectedPlan.getPrice())));
            confirmPanel.add(new JLabel("Duration:"));
            confirmPanel.add(new JLabel(selectedPlan.getDurationMonths() + " month(s)"));
            confirmPanel.add(new JLabel("Payment Method:"));
            confirmPanel.add(new JLabel(method));
            confirmPanel.add(new JLabel("Amount Paid (PHP):"));
            confirmPanel.add(tfAmount);

            int confirm = UITheme.showConfirmDialog(
                    this,
                    confirmPanel,
                    "Confirm Payment",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (confirm != JOptionPane.OK_OPTION) {
                return;
            }

            try {
                double amountPaid = Double.parseDouble(tfAmount.getText().trim());
                Payment payment = manager.processPayment(currentMember, selectedPlan, method, amountPaid);
                refreshAll();

                UITheme.showMessageDialog(
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
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this, "Please enter a valid numeric payment amount.", "Payment Failed", JOptionPane.ERROR_MESSAGE
                );
            } catch (Exception ex) {
                UITheme.showMessageDialog(
                        this, "Payment failed: " + ex.getMessage(), "Payment Error", JOptionPane.ERROR_MESSAGE
                );
            }
        });

        btnLogout.addActionListener(e -> {
            int confirm = UITheme.showConfirmDialog(
                    this, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                UITheme.switchFrame(this, new LoginForm());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MemberDashboard().setVisible(true));
    }
}
