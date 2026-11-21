package ui;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;

//Sharde UI theme for the forms
public final class UITheme {
    public static final Color BG_GRAY = new Color(241, 245, 249);
    public static final Color CARD_BORDER = new Color(203, 213, 225);
    public static final Color GRID_LINE = new Color(226, 232, 240);
    public static final Color TEXT_DARK = new Color(15, 23, 42);
    public static final Color TEXT_MUTED = new Color(100, 116, 139);
    public static final Color PRIMARY_BLUE = new Color(37, 99, 235);
    public static final Color DANGER_RED = new Color(220, 38, 38);

    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 13);

    private UITheme() {}

    public static void styleCard(JPanel panel, int padV, int padH) {
        panel.setBackground(Color.WHITE);
        panel.setBorder(new CompoundBorder(
                new LineBorder(CARD_BORDER, 1, true),
                new EmptyBorder(padV, padH, padV, padH)
        ));
    }

    public static void stylePrimaryButton(JButton btn) {
        styleButton(btn, PRIMARY_BLUE, Color.WHITE, false);
    }

    public static void styleSecondaryButton(JButton btn) {
        styleButton(btn, Color.WHITE, TEXT_DARK, true);
        btn.setBorder(new CompoundBorder(
                new LineBorder(CARD_BORDER, 1),
                new EmptyBorder(6, 14, 6, 14)
        ));
    }

    public static void styleDangerButton(JButton btn) {
        styleButton(btn, DANGER_RED, Color.WHITE, false);
    }

    public static void styleButton(JButton btn, Color bg, Color fg, boolean bordered) {
        btn.setFont(FONT_BOLD);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorderPainted(bordered);
        if (!bordered) {
            btn.setBorder(new EmptyBorder(8, 16, 8, 16));
        }
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void styleInput(JComponent input) {
        input.setFont(FONT_BODY);
        input.setBackground(Color.WHITE);
        input.setForeground(TEXT_DARK);
        input.setBorder(new CompoundBorder(
                new LineBorder(CARD_BORDER, 1),
                new EmptyBorder(6, 10, 6, 10)
        ));
    }

    public static void styleTable(JTable table) {
        table.setFont(FONT_BODY);
        table.setForeground(TEXT_DARK);
        table.setBackground(Color.WHITE);
        table.setRowHeight(28);
        table.setGridColor(GRID_LINE);
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(TEXT_DARK);
        table.setShowVerticalLines(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_BOLD);
        header.setBackground(BG_GRAY);
        header.setForeground(TEXT_DARK);
        header.setReorderingAllowed(false);
    }

    public static void styleTabbedPane(JTabbedPane tabs) {
        tabs.setFont(FONT_BOLD);
        tabs.setBackground(Color.WHITE);
        tabs.setForeground(TEXT_DARK);
    }
}
