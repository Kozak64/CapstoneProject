package ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicPasswordFieldUI;
import javax.swing.table.JTableHeader;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.PasswordView;
import javax.swing.text.Position;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;

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

    private static final char PASSWORD_BULLET = '\u2022';
    private static final int PEEK_DURATION_MS = 900;
    private static final String PASSWORD_ENHANCED_KEY = "ui.passwordEnhanced";

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
        if (input instanceof JPasswordField) {
            stylePasswordField((JPasswordField) input);
            return;
        }
        input.setFont(FONT_BODY);
        input.setBackground(Color.WHITE);
        input.setForeground(TEXT_DARK);
        input.setBorder(new CompoundBorder(
                new LineBorder(CARD_BORDER, 1),
                new EmptyBorder(6, 10, 6, 10)
        ));
    }

    public static void stylePasswordField(JPasswordField field) {
        field.setFont(FONT_BODY);
        field.setBackground(Color.WHITE);
        field.setForeground(TEXT_DARK);
        field.setEchoChar(PASSWORD_BULLET);

        Border normalBorder = new CompoundBorder(
                new LineBorder(CARD_BORDER, 1),
                new EmptyBorder(6, 10, 6, 36)
        );
        Border focusedBorder = new CompoundBorder(
                new LineBorder(PRIMARY_BLUE, 1),
                new EmptyBorder(6, 10, 6, 36)
        );
        field.setBorder(normalBorder);

        if (Boolean.TRUE.equals(field.getClientProperty(PASSWORD_ENHANCED_KEY))) {
            return;
        }
        field.putClientProperty(PASSWORD_ENHANCED_KEY, Boolean.TRUE);

        PasswordPeekState state = new PasswordPeekState(field);
        field.setUI(new PeekPasswordFieldUI(state));
        field.setFont(FONT_BODY);
        field.setBackground(Color.WHITE);
        field.setForeground(TEXT_DARK);
        field.setBorder(normalBorder);
        field.setEchoChar(PASSWORD_BULLET);

        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!state.revealed && e.getLength() == 1) {
                    state.peekIndex = e.getOffset();
                    state.hideTimer.restart();
                } else {
                    state.clearPeek();
                }
                field.repaint();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                state.clearPeek();
                field.repaint();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(focusedBorder);
            }

            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(normalBorder);
                state.clearPeek();
                field.repaint();
            }
        });

        JButton eyeButton = createEyeToggleButton(field, state);
        field.setLayout(new TrailingButtonLayout(eyeButton));
        field.add(eyeButton);
    }

    private static JButton createEyeToggleButton(JPasswordField field, PasswordPeekState state) {
        JButton btn = new JButton(new EyeIcon(false, TEXT_MUTED));
        btn.setToolTipText("Show password");
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.setFocusable(false);
        btn.setMargin(new Insets(0, 0, 0, 0));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        final boolean[] hovered = {false};
        Runnable refreshIcon = () -> {
            Color color = hovered[0] ? PRIMARY_BLUE : TEXT_MUTED;
            btn.setIcon(new EyeIcon(state.revealed, color));
            btn.setToolTipText(state.revealed ? "Hide password" : "Show password");
        };

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovered[0] = true;
                refreshIcon.run();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovered[0] = false;
                refreshIcon.run();
            }
        });

        btn.addActionListener(e -> {
            state.revealed = !state.revealed;
            state.clearPeek();
            field.setEchoChar(state.revealed ? (char) 0 : PASSWORD_BULLET);
            refreshIcon.run();
            field.repaint();
        });

        return btn;
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

    private static final class PasswordPeekState {
        int peekIndex = -1;
        boolean revealed = false;
        final Timer hideTimer;

        PasswordPeekState(JPasswordField field) {
            this.hideTimer = new Timer(PEEK_DURATION_MS, e -> {
                peekIndex = -1;
                if (field.hasFocus() && field.getCaret() != null) {
                    field.getCaret().setVisible(false);
                    field.getCaret().setVisible(true);
                }
                field.repaint();
            });
            this.hideTimer.setRepeats(false);
        }

        void clearPeek() {
            peekIndex = -1;
            hideTimer.stop();
        }
    }

    private static final class PeekPasswordFieldUI extends BasicPasswordFieldUI {
        private final PasswordPeekState state;

        PeekPasswordFieldUI(PasswordPeekState state) {
            this.state = state;
        }

        @Override
        public View create(Element elem) {
            return new PeekPasswordView(elem, state);
        }
    }

    private static final class PeekPasswordView extends PasswordView {
        private final PasswordPeekState state;

        PeekPasswordView(Element elem, PasswordPeekState state) {
            super(elem);
            this.state = state;
        }

        private char charAtDoc(int pos, char fallback) {
            try {
                String s = getDocument().getText(pos, 1);
                return s.isEmpty() ? fallback : s.charAt(0);
            } catch (BadLocationException e) {
                return fallback;
            }
        }

        @Override
        protected float drawUnselectedText(Graphics2D g, float x, float y, int p0, int p1)
                throws BadLocationException {
            Container c = getContainer();
            if (!(c instanceof JPasswordField)) {
                return super.drawUnselectedText(g, x, y, p0, p1);
            }
            JPasswordField f = (JPasswordField) c;
            if (!f.echoCharIsSet() || state.peekIndex < 0) {
                return super.drawUnselectedText(g, x, y, p0, p1);
            }
            g.setColor(f.isEnabled() ? f.getForeground() : f.getDisabledTextColor());
            char echo = f.getEchoChar();
            for (int i = p0; i < p1; i++) {
                char toDraw = (i == state.peekIndex) ? charAtDoc(i, echo) : echo;
                x = drawEchoCharacter(g, x, y, toDraw);
            }
            return x;
        }

        @Override
        protected float drawSelectedText(Graphics2D g, float x, float y, int p0, int p1)
                throws BadLocationException {
            Container c = getContainer();
            if (!(c instanceof JPasswordField)) {
                return super.drawSelectedText(g, x, y, p0, p1);
            }
            JPasswordField f = (JPasswordField) c;
            if (!f.echoCharIsSet() || state.peekIndex < 0) {
                return super.drawSelectedText(g, x, y, p0, p1);
            }
            g.setColor(f.getSelectedTextColor());
            char echo = f.getEchoChar();
            for (int i = p0; i < p1; i++) {
                char toDraw = (i == state.peekIndex) ? charAtDoc(i, echo) : echo;
                x = drawEchoCharacter(g, x, y, toDraw);
            }
            return x;
        }

        @Override
        public Shape modelToView(int pos, Shape a, Position.Bias b) throws BadLocationException {
            Shape s = super.modelToView(pos, a, b);
            Container c = getContainer();
            if (c instanceof JPasswordField && state.peekIndex >= 0 && pos > state.peekIndex) {
                JPasswordField f = (JPasswordField) c;
                if (f.echoCharIsSet() && state.peekIndex < getDocument().getLength()) {
                    FontMetrics fm = f.getFontMetrics(f.getFont());
                    char echo = f.getEchoChar();
                    char actual = charAtDoc(state.peekIndex, echo);
                    int delta = fm.charWidth(actual) - fm.charWidth(echo);
                    if (delta != 0 && s instanceof Rectangle) {
                        Rectangle r = new Rectangle((Rectangle) s);
                        r.x += delta;
                        return r;
                    }
                }
            }
            return s;
        }
    }

    private static final class TrailingButtonLayout implements LayoutManager {
        private final Component button;

        TrailingButtonLayout(Component button) {
            this.button = button;
        }

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            return new Dimension(0, 0);
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return new Dimension(0, 0);
        }

        @Override
        public void layoutContainer(Container parent) {
            int btnWidth = 30;
            int h = Math.max(0, parent.getHeight() - 4);
            button.setBounds(parent.getWidth() - btnWidth - 2, 2, btnWidth, h);
        }
    }

    private static final class EyeIcon implements Icon {
        private final boolean slashed;
        private final Color color;

        EyeIcon(boolean slashed, Color color) {
            this.slashed = slashed;
            this.color = color;
        }

        @Override
        public int getIconWidth() {
            return 18;
        }

        @Override
        public int getIconHeight() {
            return 18;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            float cx = x + 9f;
            float cy = y + 9f;
            g2.draw(new QuadCurve2D.Float(x + 1.5f, cy, cx, y + 2.5f, x + 16.5f, cy));
            g2.draw(new QuadCurve2D.Float(x + 1.5f, cy, cx, y + 15.5f, x + 16.5f, cy));
            g2.draw(new Ellipse2D.Float(cx - 2.5f, cy - 2.5f, 5f, 5f));

            if (slashed) {
                g2.draw(new Line2D.Float(x + 2.5f, y + 15.5f, x + 15.5f, y + 2.5f));
            }
            g2.dispose();
        }
    }
}
