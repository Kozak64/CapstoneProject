package ui;

import models.*;
import services.GymSystemManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TrainerDashboard extends JFrame {
    private static final DateTimeFormatter DATE_TIME_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final Trainer currentTrainer;
    private final GymSystemManager manager = GymSystemManager.getInstance();

    private JPanel mainPanel;
    private JScrollPane dashboardScrollPane;
    private JPanel scrollContentPanel;
    private JPanel heroPanel;
    private JLabel lblLogo;
    private JLabel lblHeroHeadline;
    private JLabel lblHeroSubheadline;

    private JLabel DashboardTitle;
    private JLabel Subtitle = new JLabel();
    private JPanel welcomeCardPanel;
    private JLabel lblWelcomeBody;
    private JPanel cardTrainerId;
    private JPanel cardSpecialization;
    private JPanel cardSchedule;
    private JLabel lblTrainerIdValue;
    private JLabel lblSpecializationValue;
    private JLabel lblScheduleValue;

    private JTabbedPane tabbedPane1;
    private JPanel tabMySessions;
    private JScrollPane sessionsScrollPane;
    private JTable Sessiontabel;
    private JButton updateScheduleButton;
    private JButton cancelSessionButton;

    private JPanel tabMarkAttendance;
    private JComboBox<String> cmSessionFilter;
    private JScrollPane attendanceScrollPane;
    private JTable AttendanceTabel;
    private JButton markPresentButton;
    private JButton markAbsentButton;
    private JButton btnLogout;

    private DefaultTableModel sessionsModel;
    private DefaultTableModel attendanceModel;

    private List<Session> displayedSessions = new ArrayList<>();
    private List<Member> displayedAttendanceMembers = new ArrayList<>();
    private boolean updatingCombo = false;

    public TrainerDashboard(Trainer trainer) {
        this.currentTrainer = trainer;

        setContentPane(mainPanel);
        UITheme.configureAdaptiveFrame(this, "Hussle Fitness - Trainer Dashboard");

        applyTheme();
        populateHeader();
        setupTablesAndCombos();
        setupListeners();
        refreshSessions();
    }

    public TrainerDashboard() {
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
                "Ready to put in the work? Manage your daily schedules, coordinate training slots, and keep track of everyone on the gym floor. "
                        + "Check back here to view your active roster, update class availability, and ensure every athlete is accounted for.",
                "Huss. Huss. Huss.",
                UITheme.FONT_INTRO_BODY,
                UITheme.TEXT_LIGHT,
                UITheme.ACCENT_YELLOW,
                1.38f
        );
        cardTrainerId = new UITheme.DarkGlassCard();
        cardSpecialization = new UITheme.DarkGlassCard();
        cardSchedule = new UITheme.DarkGlassCard();

        tabMySessions = new UITheme.DarkGlassCard();
        tabMarkAttendance = new UITheme.DarkGlassCard();

        cancelSessionButton = new UITheme.OutlineAccentButton(
                "CANCEL SESSION",
                UITheme.STATUS_RED,
                UITheme.STATUS_RED,
                42,
                176,
                1.5f,
                20
        );
        updateScheduleButton = new UITheme.GradientButton(
                "UPDATE SCHEDULE \u2192",
                42,
                224
        );
        markAbsentButton = new UITheme.OutlineAccentButton(
                "MARK ABSENT",
                UITheme.STATUS_RED,
                UITheme.STATUS_RED,
                42,
                168,
                1.5f,
                20
        );
        markPresentButton = new UITheme.GradientButton(
                "MARK PRESENT \u2192",
                42,
                210
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

        DashboardTitle.setFont(UITheme.FONT_PORTAL_TITLE);
        DashboardTitle.setForeground(UITheme.TEXT_WHITE);

        UITheme.styleDarkTabbedPane(tabbedPane1);
        UITheme.styleDarkComboBox(cmSessionFilter);
    }

    private void populateHeader() {
        if (currentTrainer != null) {
            DashboardTitle.setText(currentTrainer.getDashBoardTitle());

            String trainerIdText = "TR-" + currentTrainer.getTrainerID();
            String specText = currentTrainer.getSpecialization() != null && !currentTrainer.getSpecialization().isBlank()
                    ? currentTrainer.getSpecialization()
                    : "General Fitness";
            String scheduleText = currentTrainer.getSchedule() != null && !currentTrainer.getSchedule().isBlank()
                    ? currentTrainer.getSchedule()
                    : "N/A";

            lblTrainerIdValue.setText(trainerIdText);
            lblSpecializationValue.setText(specText);
            lblScheduleValue.setText(scheduleText);
            Subtitle.setText("Trainer ID: " + currentTrainer.getTrainerID()
                    + "   |   Specialization: " + specText
                    + "   |   Schedule: " + scheduleText);
        } else {
            DashboardTitle.setText("Trainer Portal - All Trainers");
            lblTrainerIdValue.setText("All Trainers");
            lblSpecializationValue.setText("All Specialties");
            lblScheduleValue.setText("Preview Mode");
            Subtitle.setText("Preview Mode");
        }
    }

    private void setupTablesAndCombos() {
        sessionsModel = new DefaultTableModel(
                new Object[]{"Session ID", "Type", "Schedule Date", "Enrolled", "Status"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Sessiontabel.setModel(sessionsModel);
        UITheme.styleDarkTable(Sessiontabel, sessionsScrollPane, 4);

        attendanceModel = new DefaultTableModel(
                new Object[]{"User ID", "Member Name", "Plan", "Attendance Status"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        AttendanceTabel.setModel(attendanceModel);
        UITheme.styleDarkTable(AttendanceTabel, attendanceScrollPane, 3);
    }

    private void refreshSessions() {
        int prevFilterIdx = cmSessionFilter.getSelectedIndex();

        displayedSessions = manager.getSessionsForTrainer(currentTrainer);
        sessionsModel.setRowCount(0);

        updatingCombo = true;
        cmSessionFilter.removeAllItems();
        for (Session s : displayedSessions) {
            String dateStr = s.getSessionDate() != null ? s.getSessionDate().format(DATE_TIME_FMT) : "";
            sessionsModel.addRow(new Object[]{
                    "SES-" + s.getSessionID(),
                    s.getType(),
                    dateStr,
                    s.getEnrolledMembers().size() + " / " + s.getMaxCapacity(),
                    s.getStatus()
            });
            cmSessionFilter.addItem("SES-" + s.getSessionID() + " - " + s.getType());
        }
        if (prevFilterIdx >= 0 && prevFilterIdx < cmSessionFilter.getItemCount()) {
            cmSessionFilter.setSelectedIndex(prevFilterIdx);
        } else if (cmSessionFilter.getItemCount() > 0) {
            cmSessionFilter.setSelectedIndex(0);
        }
        updatingCombo = false;

        refreshAttendanceTable();
    }

    private void refreshAttendanceTable() {
        attendanceModel.setRowCount(0);
        displayedAttendanceMembers = new ArrayList<>();

        int idx = cmSessionFilter.getSelectedIndex();
        if (idx < 0 || idx >= displayedSessions.size()) {
            return;
        }
        Session selected = displayedSessions.get(idx);
        for (Member m : selected.getEnrolledMembers()) {
            displayedAttendanceMembers.add(m);
            String planName = m.getMembershipPlan() != null ? m.getMembershipPlan().getPlanName() : "None";
            Attendance record = manager.findAttendance(m, selected);
            String status = record == null ? "Pending" : (record.isPresent() ? "Present" : "Absent");
            attendanceModel.addRow(new Object[]{
                    m.getUserID(), m.getName(), planName, status
            });
        }
    }

    private void setupListeners() {
        cmSessionFilter.addActionListener(e -> {
            if (!updatingCombo) {
                refreshAttendanceTable();
            }
        });
        updateScheduleButton.addActionListener(e -> handleUpdateSchedule());
        cancelSessionButton.addActionListener(e -> handleCancelSession());
        markPresentButton.addActionListener(e -> handleMarkAttendance(true));
        markAbsentButton.addActionListener(e -> handleMarkAttendance(false));
        btnLogout.addActionListener(e -> handleLogout());
    }

    private void handleUpdateSchedule() {
        int row = Sessiontabel.getSelectedRow();
        if (row == -1 || row >= displayedSessions.size()) {
            UITheme.showMessageDialog(this, "Please select a session first.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Session session = displayedSessions.get(row);
        String current = session.getSessionDate() != null ? session.getSessionDate().format(DATE_TIME_FMT) : "";
        String updated = UITheme.showInputDialog(this, "Enter new schedule date/time (yyyy-MM-dd HH:mm):", current);
        if (updated == null || updated.trim().isEmpty()) {
            return;
        }
        try {
            LocalDateTime newDate = LocalDateTime.parse(updated.trim(), DATE_TIME_FMT);
            manager.updateSessionDate(session.getSessionID(), newDate);
            refreshSessions();
            UITheme.showMessageDialog(this, "Session schedule updated.", "Updated", JOptionPane.INFORMATION_MESSAGE);
        } catch (DateTimeParseException ex) {
            UITheme.showMessageDialog(this, "Invalid format. Use yyyy-MM-dd HH:mm (e.g. 2026-11-23 09:00).", "Invalid Date", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCancelSession() {
        int row = Sessiontabel.getSelectedRow();
        if (row == -1 || row >= displayedSessions.size()) {
            UITheme.showMessageDialog(this, "Please select a session to cancel.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Session session = displayedSessions.get(row);
        int confirm = UITheme.showConfirmDialog(this, "Cancel session SES-" + session.getSessionID() + "?", "Confirm Cancel", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            manager.cancelSession(session.getSessionID());
            refreshSessions();
        }
    }

    private void handleMarkAttendance(boolean present) {
        int sessionIdx = cmSessionFilter.getSelectedIndex();
        if (sessionIdx < 0 || sessionIdx >= displayedSessions.size()) {
            UITheme.showMessageDialog(this, "Please select a session first.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int row = AttendanceTabel.getSelectedRow();
        if (row == -1 || row >= displayedAttendanceMembers.size()) {
            UITheme.showMessageDialog(this, "Please select a member first.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Session session = displayedSessions.get(sessionIdx);
        Member member = displayedAttendanceMembers.get(row);
        manager.markAttendance(member, session, present);
        refreshAttendanceTable();
    }

    private void handleLogout() {
        int confirm = UITheme.showConfirmDialog(this, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            UITheme.switchFrame(this, new LoginForm());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TrainerDashboard().setVisible(true));
    }
}
