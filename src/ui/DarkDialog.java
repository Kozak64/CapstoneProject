package ui;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;

/**
 * Resizable dark-athletic modal dialogs styled to match the Hussle Fitness UI.
 * Drop-in replacement for JOptionPane message, confirm, and input dialogs.
 */
public final class DarkDialog {
    private static final Color ERROR_RED = new Color(0xEF4444);
    private static final Color SURFACE_BG = new Color(0x0E1525);
    private static final Font FONT_DIALOG_TITLE = new Font("Segoe UI", Font.BOLD, 19);
    private static final Font FONT_DIALOG_BODY = new Font("Segoe UI", Font.PLAIN, 16);
    private static final Font FONT_DIALOG_INPUT = new Font("Segoe UI", Font.PLAIN, 15);
    private static final Font FONT_DIALOG_BUTTON = new Font("Segoe UI", Font.BOLD, 14);

    private DarkDialog() {}

    public static void showMessage(Component parent, Object message, String title, int messageType) {
        JDialog dialog = createBaseDialog(parent, title != null ? title : "Notice");
        JPanel card = createCardShell(accentFor(messageType));

        card.add(createHeader(title != null ? title : "Notice", messageType), BorderLayout.NORTH);
        card.add(createBody(message), BorderLayout.CENTER);

        JButton okBtn = createPrimaryDialogButton("OK", messageType == JOptionPane.ERROR_MESSAGE);
        okBtn.addActionListener(e -> dialog.dispose());

        card.add(createButtonRow(okBtn), BorderLayout.SOUTH);

        installEscapeClose(dialog, dialog::dispose);
        dialog.getRootPane().setDefaultButton(okBtn);

        finalizeAndShow(dialog, card, parent, message, false);
    }

    public static int showConfirm(Component parent, Object message, String title, int optionType, int messageType) {
        JDialog dialog = createBaseDialog(parent, title != null ? title : "Confirm");
        JPanel card = createCardShell(accentFor(messageType));

        card.add(createHeader(title != null ? title : "Confirm", messageType), BorderLayout.NORTH);
        card.add(createBody(message), BorderLayout.CENTER);

        final int[] result = {JOptionPane.CLOSED_OPTION};

        JButton primaryBtn;
        JPanel btnRow;

        if (optionType == JOptionPane.OK_CANCEL_OPTION) {
            JButton cancelBtn = createSecondaryDialogButton("CANCEL");
            primaryBtn = createPrimaryDialogButton("OK", false);
            cancelBtn.addActionListener(e -> {
                result[0] = JOptionPane.CANCEL_OPTION;
                dialog.dispose();
            });
            primaryBtn.addActionListener(e -> {
                result[0] = JOptionPane.OK_OPTION;
                dialog.dispose();
            });
            btnRow = createButtonRow(cancelBtn, primaryBtn);
        } else if (optionType == JOptionPane.YES_NO_CANCEL_OPTION) {
            JButton cancelBtn = createSecondaryDialogButton("CANCEL");
            JButton noBtn = createSecondaryDialogButton("NO");
            primaryBtn = createPrimaryDialogButton("YES", false);
            cancelBtn.addActionListener(e -> {
                result[0] = JOptionPane.CANCEL_OPTION;
                dialog.dispose();
            });
            noBtn.addActionListener(e -> {
                result[0] = JOptionPane.NO_OPTION;
                dialog.dispose();
            });
            primaryBtn.addActionListener(e -> {
                result[0] = JOptionPane.YES_OPTION;
                dialog.dispose();
            });
            btnRow = createButtonRow(cancelBtn, noBtn, primaryBtn);
        } else {
            JButton noBtn = createSecondaryDialogButton("NO");
            primaryBtn = createPrimaryDialogButton("YES", false);
            noBtn.addActionListener(e -> {
                result[0] = JOptionPane.NO_OPTION;
                dialog.dispose();
            });
            primaryBtn.addActionListener(e -> {
                result[0] = JOptionPane.YES_OPTION;
                dialog.dispose();
            });
            btnRow = createButtonRow(noBtn, primaryBtn);
        }

        card.add(btnRow, BorderLayout.SOUTH);

        installEscapeClose(dialog, () -> {
            result[0] = (optionType == JOptionPane.YES_NO_OPTION)
                    ? JOptionPane.NO_OPTION
                    : JOptionPane.CANCEL_OPTION;
            dialog.dispose();
        });
        dialog.getRootPane().setDefaultButton(primaryBtn);

        finalizeAndShow(dialog, card, parent, message, false);
        return result[0];
    }

    public static String showInput(Component parent, Object message, Object initialValue) {
        String title = "Input Required";
        JDialog dialog = createBaseDialog(parent, title);
        JPanel card = createCardShell(UITheme.ELECTRIC_BLUE);

        card.add(createHeader(title, JOptionPane.QUESTION_MESSAGE), BorderLayout.NORTH);

        JPanel inputBody = new JPanel(new BorderLayout(0, 14));
        inputBody.setOpaque(false);
        inputBody.add(createMessageTextArea(message == null ? "" : String.valueOf(message)), BorderLayout.NORTH);

        UITheme.DarkTextField inputField = new UITheme.DarkTextField("", null, null, 44);
        inputField.setFont(FONT_DIALOG_INPUT);
        if (initialValue != null) {
            inputField.setText(String.valueOf(initialValue));
        }
        inputBody.add(inputField, BorderLayout.CENTER);

        card.add(inputBody, BorderLayout.CENTER);

        final String[] result = {null};

        JButton cancelBtn = createSecondaryDialogButton("CANCEL");
        JButton okBtn = createPrimaryDialogButton("OK", false);

        Runnable submit = () -> {
            result[0] = inputField.getText();
            dialog.dispose();
        };

        cancelBtn.addActionListener(e -> {
            result[0] = null;
            dialog.dispose();
        });
        okBtn.addActionListener(e -> submit.run());
        inputField.addActionListener(e -> submit.run());

        card.add(createButtonRow(cancelBtn, okBtn), BorderLayout.SOUTH);

        installEscapeClose(dialog, () -> {
            result[0] = null;
            dialog.dispose();
        });
        dialog.getRootPane().setDefaultButton(okBtn);

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                inputField.requestFocusInWindow();
                inputField.selectAll();
            }
        });

        finalizeAndShow(dialog, card, parent, message, true);
        return result[0];
    }

    // -------------------------------------------------------------------------
    // Dialog Construction Helpers
    // -------------------------------------------------------------------------

    private static JDialog createBaseDialog(Component parent, String title) {
        Window owner = UITheme.resolveActiveWindow(parent);
        JDialog dialog = new JDialog(owner, title, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setResizable(true);
        dialog.setMinimumSize(new Dimension(440, 260));
        return dialog;
    }

    private static JPanel createCardShell(Color accent) {
        JPanel card = new JPanel(new BorderLayout(0, 16)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();
                RoundRectangle2D shape = new RoundRectangle2D.Double(1, 1, w - 2, h - 2, 22, 22);

                g2.setColor(SURFACE_BG);
                g2.fill(shape);

                // Subtle frosted top sheen
                g2.setPaint(new GradientPaint(0, 0, new Color(255, 255, 255, 14), 0, 90, new Color(255, 255, 255, 0)));
                g2.fill(shape);

                // Accent border
                g2.setStroke(new BasicStroke(1.5f));
                g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 135));
                g2.draw(shape);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(22, 26, 20, 26));
        return card;
    }

    private static JPanel createHeader(String title, int messageType) {
        JPanel wrapper = new JPanel(new BorderLayout(0, 14));
        wrapper.setOpaque(false);

        JPanel row = new JPanel(new BorderLayout(14, 0));
        row.setOpaque(false);

        JLabel iconLabel = new JLabel(new DialogBadgeIcon(messageType));
        iconLabel.setOpaque(false);
        row.add(iconLabel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(FONT_DIALOG_TITLE);
        titleLabel.setForeground(UITheme.TEXT_WHITE);
        titleLabel.setOpaque(false);
        row.add(titleLabel, BorderLayout.CENTER);

        JPanel divider = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(UITheme.DIVIDER_COLOR);
                g.fillRect(0, 0, getWidth(), 1);
            }
        };
        divider.setOpaque(false);
        divider.setPreferredSize(new Dimension(10, 1));

        wrapper.add(row, BorderLayout.CENTER);
        wrapper.add(divider, BorderLayout.SOUTH);
        return wrapper;
    }

    private static JComponent createBody(Object message) {
        if (message instanceof Component comp) {
            styleEmbeddedComponent(comp);
            JPanel holder = new JPanel(new BorderLayout());
            holder.setOpaque(false);
            holder.setBorder(new EmptyBorder(4, 2, 4, 2));
            holder.add(comp, BorderLayout.CENTER);
            return holder;
        }

        String text = (message == null) ? "" : String.valueOf(message);
        JTextArea area = createMessageTextArea(text);

        JScrollPane scroll = new JScrollPane(area);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        styleDarkScrollBar(scroll.getVerticalScrollBar());
        return scroll;
    }

    private static JTextArea createMessageTextArea(String text) {
        JTextArea area = new JTextArea(text);
        area.setFont(FONT_DIALOG_BODY);
        area.setForeground(UITheme.TEXT_LIGHT);
        area.setSelectionColor(new Color(10, 132, 255, 110));
        area.setSelectedTextColor(UITheme.TEXT_WHITE);
        area.setOpaque(false);
        area.setEditable(false);
        area.setFocusable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(new EmptyBorder(4, 2, 4, 2));
        area.setCaretPosition(0);
        return area;
    }

    private static void styleDarkScrollBar(JScrollBar bar) {
        bar.setOpaque(false);
        bar.setPreferredSize(new Dimension(8, 8));
        bar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbColor = new Color(0x334155);
                trackColor = new Color(0x0E1525);
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton b = new JButton();
                b.setPreferredSize(new Dimension(0, 0));
                b.setMinimumSize(new Dimension(0, 0));
                b.setMaximumSize(new Dimension(0, 0));
                return b;
            }

            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
                g.setColor(trackColor);
                g.fillRect(r.x, r.y, r.width, r.height);
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
                if (r.isEmpty() || !bar.isEnabled()) return;
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(thumbColor);
                g2.fill(new RoundRectangle2D.Double(r.x + 1, r.y + 2, r.width - 2, r.height - 4, 6, 6));
                g2.dispose();
            }
        });
    }

    private static void styleEmbeddedComponent(Component comp) {
        if (comp instanceof JPanel panel) {
            panel.setOpaque(false);
            for (Component child : panel.getComponents()) {
                styleEmbeddedComponent(child);
            }
        } else if (comp instanceof JLabel label) {
            label.setFont(new Font("Segoe UI", Font.BOLD, 14));
            label.setForeground(UITheme.TEXT_LIGHT);
            label.setOpaque(false);
        } else if (comp instanceof JTextField field) {
            field.setFont(FONT_DIALOG_INPUT);
            field.setBackground(UITheme.INPUT_BG);
            field.setForeground(UITheme.TEXT_WHITE);
            field.setCaretColor(UITheme.ELECTRIC_BLUE);
            field.setBorder(new CompoundBorder(
                    new LineBorder(UITheme.INPUT_BORDER, 1, true),
                    new EmptyBorder(7, 10, 7, 10)
            ));
        } else if (comp instanceof JComboBox<?> combo) {
            combo.setFont(FONT_DIALOG_INPUT);
            combo.setBackground(UITheme.INPUT_BG);
            combo.setForeground(UITheme.TEXT_WHITE);
        }
    }

    private static JButton createPrimaryDialogButton(String text, boolean dangerStyle) {
        Color start = dangerStyle ? new Color(0xEF4444) : UITheme.ELECTRIC_BLUE;
        Color end = dangerStyle ? new Color(0xB91C1C) : UITheme.ELECTRIC_BLUE_DARK;
        UITheme.GradientButton btn = new UITheme.GradientButton(text, start, end) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(126, 42);
            }

            @Override
            public Dimension getMinimumSize() {
                return new Dimension(112, 42);
            }
        };
        btn.setFont(FONT_DIALOG_BUTTON);
        return btn;
    }

    private static JButton createSecondaryDialogButton(String text) {
        JButton btn = new JButton(text) {
            private boolean hovered = false;
            private boolean pressed = false;

            {
                setFont(FONT_DIALOG_BUTTON);
                setForeground(UITheme.TEXT_LIGHT);
                setOpaque(false);
                setContentAreaFilled(false);
                setBorderPainted(false);
                setFocusPainted(false);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        hovered = true;
                        repaint();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        hovered = false;
                        pressed = false;
                        repaint();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        pressed = true;
                        repaint();
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        pressed = false;
                        repaint();
                    }
                });
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(126, 42);
            }

            @Override
            public Dimension getMinimumSize() {
                return new Dimension(112, 42);
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();
                RoundRectangle2D shape = new RoundRectangle2D.Double(1, 1, w - 2, h - 2, 18, 18);

                g2.setColor(pressed ? new Color(0x1E293B) : (hovered ? new Color(0x1B263B) : UITheme.INPUT_BG));
                g2.fill(shape);

                g2.setStroke(new BasicStroke(1.35f));
                g2.setColor(hovered ? UITheme.ELECTRIC_BLUE_LIGHT : UITheme.INPUT_BORDER);
                g2.draw(shape);

                g2.setFont(getFont());
                g2.setColor(hovered ? UITheme.TEXT_WHITE : getForeground());
                FontMetrics fm = g2.getFontMetrics();
                int tx = (w - fm.stringWidth(getText())) / 2;
                int ty = (h - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(getText(), tx, ty);
                g2.dispose();
            }
        };
        return btn;
    }

    private static JPanel createButtonRow(JButton... buttons) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        row.setOpaque(false);
        for (JButton b : buttons) {
            row.add(b);
        }
        return row;
    }

    private static void installEscapeClose(JDialog dialog, Runnable onEscape) {
        dialog.getRootPane().registerKeyboardAction(
                e -> onEscape.run(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );
    }

    private static void finalizeAndShow(JDialog dialog, JPanel card, Component parent, Object message, boolean isInput) {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BG_NAVY);
        root.setBorder(new EmptyBorder(14, 14, 14, 14));
        root.add(card, BorderLayout.CENTER);
        dialog.setContentPane(root);

        // Compute content dimensions cleanly, then pack to include OS title bar & window insets
        if (message instanceof Component) {
            dialog.pack();
            int w = Math.max(520, dialog.getWidth() + 48);
            int h = Math.max(290, dialog.getHeight() + 36);
            dialog.setSize(w, h);
        } else {
            String text = (message == null) ? "" : String.valueOf(message);
            String[] lines = text.split("\n", -1);
            int maxLineChars = 0;
            for (String line : lines) {
                maxLineChars = Math.max(maxLineChars, line.length());
            }
            int contentW = (maxLineChars > 44) ? 540 : 490;
            int wrapChars = Math.max(36, (contentW - 90) / 8);
            int estimatedLines = 0;
            for (String line : lines) {
                estimatedLines += Math.max(1, (int) Math.ceil((line.length() + 1.0) / wrapChars));
            }
            int bodyH = Math.max(48, estimatedLines * 26 + 16) + (isInput ? 60 : 0);
            int contentH = Math.max(230, Math.min(540, 178 + bodyH));

            root.setPreferredSize(new Dimension(contentW, contentH));
            dialog.pack();
        }

        Window anchor = UITheme.resolveActiveWindow(parent);
        dialog.setLocationRelativeTo(anchor != null ? anchor : parent);
        dialog.setVisible(true);
    }

    private static Color accentFor(int messageType) {
        return switch (messageType) {
            case JOptionPane.WARNING_MESSAGE -> UITheme.ACCENT_YELLOW;
            case JOptionPane.ERROR_MESSAGE -> ERROR_RED;
            case JOptionPane.QUESTION_MESSAGE -> UITheme.ELECTRIC_BLUE_LIGHT;
            default -> UITheme.ELECTRIC_BLUE;
        };
    }

    private static final class DialogBadgeIcon implements Icon {
        private final int messageType;

        DialogBadgeIcon(int messageType) {
            this.messageType = messageType;
        }

        @Override
        public int getIconWidth() {
            return 38;
        }

        @Override
        public int getIconHeight() {
            return 38;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            Color accent = accentFor(messageType);
            Ellipse2D circle = new Ellipse2D.Double(x + 1.5, y + 1.5, 35, 35);

            g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 34));
            g2.fill(circle);

            g2.setStroke(new BasicStroke(1.8f));
            g2.setColor(accent);
            g2.draw(circle);

            if (messageType == JOptionPane.ERROR_MESSAGE) {
                g2.setStroke(new BasicStroke(2.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.draw(new Line2D.Double(x + 13, y + 13, x + 25, y + 25));
                g2.draw(new Line2D.Double(x + 25, y + 13, x + 13, y + 25));
            } else {
                String glyph = switch (messageType) {
                    case JOptionPane.WARNING_MESSAGE -> "!";
                    case JOptionPane.QUESTION_MESSAGE -> "?";
                    default -> "i";
                };
                g2.setFont(new Font("Segoe UI", Font.BOLD, 20));
                FontMetrics fm = g2.getFontMetrics();
                int gx = x + (38 - fm.stringWidth(glyph)) / 2;
                int gy = y + (38 - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(glyph, gx, gy);
            }

            g2.dispose();
        }
    }
}
