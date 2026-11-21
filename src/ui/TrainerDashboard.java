package ui;

import models.Trainer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TrainerDashboard extends JFrame {
    private final Trainer currentTrainer;

    private JPanel mainPanel;
    private JLabel DashboardTitle;
    private JLabel Subtitle;
    private JTabbedPane tabbedPane1;
    private JTable Sessiontabel;
    private JButton updateScheduleButton;
    private JButton cancelSessionButton;
    private JComboBox<String> cmSessionFilter;
    private JTable AttendanceTabel;
    private JButton markPresentButton;
    private JButton markAbsentButton;
    private JButton btnLogout;

    private DefaultTableModel sessionsModel;
    private DefaultTableModel attendanceModel;

    public TrainerDashboard(Trainer trainer) {
        this.currentTrainer = trainer;

        setContentPane(mainPanel);
        setTitle("Gym Membership System - Trainer Dashboard");
        setSize(860, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        applyTheme();
        populateHeader();
        setupTablesAndCombos();
        setupListeners();
    }

    public TrainerDashboard() {
        this(null);
    }

    private void applyTheme() {
        mainPanel.setBackground(UITheme.BG_GRAY);
        mainPanel.setBorder(new EmptyBorder(16, 20, 16, 20));

        DashboardTitle.setFont(UITheme.FONT_TITLE);
        DashboardTitle.setForeground(UITheme.TEXT_DARK);

        Subtitle.setFont(UITheme.FONT_SUBTITLE);
        Subtitle.setForeground(UITheme.TEXT_MUTED);

        UITheme.styleTabbedPane(tabbedPane1);
        UITheme.styleTable(Sessiontabel);
        UITheme.styleTable(AttendanceTabel);

        cmSessionFilter.setFont(UITheme.FONT_BODY);
        cmSessionFilter.setBackground(Color.WHITE);

        UITheme.stylePrimaryButton(updateScheduleButton);
        UITheme.styleDangerButton(cancelSessionButton);
        UITheme.stylePrimaryButton(markPresentButton);
        UITheme.styleSecondaryButton(markAbsentButton);
        UITheme.styleSecondaryButton(btnLogout);
    }

    private void populateHeader() {
        if (currentTrainer != null) {
            DashboardTitle.setText(currentTrainer.getDashBoardTitle());
            Subtitle.setText("Trainer ID: " + currentTrainer.getTrainerID()
                    + "   |   Specialization: " + currentTrainer.getSpecialization()
                    + "   |   Schedule: " + currentTrainer.getSchedule());
        } else {
            DashboardTitle.setText("Trainer Portal - Coach Alex (Strength)");
            Subtitle.setText("Trainer ID: 201   |   Specialization: Strength   |   Schedule: MWF 8AM-5PM");
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
        sessionsModel.addRow(new Object[]{"SES-101", "Strength & Conditioning", "2026-11-22 09:00 AM", "8 / 15", "Scheduled"});
        sessionsModel.addRow(new Object[]{"SES-102", "HIIT Cardio", "2026-11-23 04:00 PM", "12 / 15", "Scheduled"});
        sessionsModel.addRow(new Object[]{"SES-103", "Core & Mobility", "2026-11-25 10:00 AM", "5 / 12", "Scheduled"});
        Sessiontabel.setModel(sessionsModel);

        cmSessionFilter.addItem("SES-101 - Strength & Conditioning");
        cmSessionFilter.addItem("SES-102 - HIIT Cardio");
        cmSessionFilter.addItem("SES-103 - Core & Mobility");

        attendanceModel = new DefaultTableModel(
                new Object[]{"User ID", "Member Name", "Plan", "Attendance Status"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        attendanceModel.addRow(new Object[]{"25-1234-567", "Juan Dela Cruz", "Gold", "Pending"});
        attendanceModel.addRow(new Object[]{"25-2045-812", "Maria Santos", "Annual Elite", "Pending"});
        attendanceModel.addRow(new Object[]{"25-3310-194", "Carlos Reyes", "Monthly Basic", "Present"});
        AttendanceTabel.setModel(attendanceModel);
    }

    private void setupListeners() {
        updateScheduleButton.addActionListener(e -> handleUpdateSchedule());
        cancelSessionButton.addActionListener(e -> handleCancelSession());
        markPresentButton.addActionListener(e -> handleMarkAttendance("Present"));
        markAbsentButton.addActionListener(e -> handleMarkAttendance("Absent"));
        btnLogout.addActionListener(e -> handleLogout());
    }

    private void handleUpdateSchedule() {
        int row = Sessiontabel.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a session first.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String current = sessionsModel.getValueAt(row, 2).toString();
        String updated = JOptionPane.showInputDialog(this, "Enter new schedule date/time:", current);
        if (updated != null && !updated.trim().isEmpty()) {
            sessionsModel.setValueAt(updated.trim(), row, 2);
            JOptionPane.showMessageDialog(this, "Session schedule updated.", "Updated", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleCancelSession() {
        int row = Sessiontabel.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a session to cancel.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String id = sessionsModel.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Cancel session " + id + "?", "Confirm Cancel", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            sessionsModel.setValueAt("Cancelled", row, 4);
        }
    }

    private void handleMarkAttendance(String status) {
        int row = AttendanceTabel.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a member first.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        attendanceModel.setValueAt(status, row, 3);
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            new LoginForm().setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TrainerDashboard().setVisible(true));
    }
}
