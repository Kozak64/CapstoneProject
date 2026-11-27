package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.BasicPasswordFieldUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.PasswordView;
import javax.swing.text.Position;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Shared UI theme and reusable components for the Hussle Fitness Gym Membership System
public final class UITheme {
    // Legacy / Dashboard Light Palette (preserved for existing forms)
    public static final Color BG_GRAY = new Color(241, 245, 249);
    public static final Color CARD_BORDER = new Color(203, 213, 225);
    public static final Color GRID_LINE = new Color(226, 232, 240);
    public static final Color TEXT_DARK = new Color(15, 23, 42);
    public static final Color TEXT_MUTED = new Color(100, 116, 139);
    public static final Color PRIMARY_BLUE = new Color(37, 99, 235);
    public static final Color DANGER_RED = new Color(220, 38, 38);

    // Hussle Fitness Dark Athletic Palette
    public static final Color BG_NAVY = new Color(0x0A0E17);
    public static final Color SURFACE_DARK = new Color(0x0E1525);
    public static final Color SURFACE_ALT = new Color(0x131C2E);
    public static final Color SURFACE_HEADER = new Color(0x111A2E);
    public static final Color ELECTRIC_BLUE = new Color(0x0A84FF);
    public static final Color ELECTRIC_BLUE_DARK = new Color(0x0066CC);
    public static final Color ELECTRIC_BLUE_LIGHT = new Color(0x38BDF8);
    public static final Color ACCENT_YELLOW = new Color(0xFFD21F);
    public static final Color INPUT_BG = new Color(0x131C2E);
    public static final Color INPUT_BORDER = new Color(0x2A3954);
    public static final Color BORDER_SUBTLE = new Color(0x1E293B);
    public static final Color BORDER_BLUE_SOFT = new Color(10, 132, 255, 90);
    public static final Color DIVIDER_COLOR = new Color(0x26334D);
    public static final Color LOGOUT_BORDER = new Color(0x475569);
    public static final Color GLASS_CARD_FILL = new Color(14, 21, 37, 215);
    public static final Color DASHBOARD_CARD_FILL = new Color(14, 21, 37, 238);
    public static final Color GLASS_CARD_BORDER = new Color(10, 132, 255, 115);
    public static final Color TEXT_WHITE = Color.WHITE;
    public static final Color TEXT_LIGHT = new Color(0xE2E8F0);
    public static final Color TEXT_HERO_SUB = new Color(0xF1F5F9);
    public static final Color TEXT_BODY_SOFT = new Color(0xCBD5E1);
    public static final Color TEXT_SLATE = new Color(0x94A3B8);
    public static final Color TEXT_PLACEHOLDER = new Color(0x64748B);
    public static final Color STATUS_GREEN = new Color(0x22C55E);
    public static final Color STATUS_GREEN_TEXT = new Color(0x4ADE80);
    public static final Color STATUS_BLUE_TEXT = new Color(0x60A5FA);
    public static final Color STATUS_RED = new Color(0xEF4444);
    public static final Color STATUS_RED_TEXT = new Color(0xF87171);
    public static final Color STATUS_AMBER = new Color(0xF59E0B);
    public static final Color TABLE_SELECTION_BG = new Color(0x122C4D);

    // Shared Typography
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_PORTAL_TITLE = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font FONT_HERO_SUBHEADLINE = new Font("Segoe UI Semibold", Font.PLAIN, 20);
    public static final Font FONT_INTRO_HEADING = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_INTRO_BODY = new Font("Segoe UI", Font.PLAIN, 18);
    public static final Font FONT_SECTION_TITLE = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_METRIC_VALUE = new Font("Segoe UI", Font.BOLD, 19);
    public static final Font FONT_METRIC_LABEL = new Font("Segoe UI", Font.BOLD, 11);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_BODY_LG = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_TAB = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_BRAND = new Font("Segoe UI", Font.BOLD | Font.ITALIC, 26);
    public static final Font FONT_SYSTEM_SUBTITLE = new Font("Segoe UI", Font.BOLD, 12);
    public static final Font FONT_HELPER = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font FONT_FIELD_LABEL = new Font("Segoe UI", Font.BOLD, 11);
    public static final Font FONT_BUTTON_ACTION = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_FOOTER_TAGLINE = new Font("Segoe UI", Font.ITALIC, 10);

    // Unified Window Dimensions
    public static final int DEFAULT_WINDOW_WIDTH = 1280;
    public static final int DEFAULT_WINDOW_HEIGHT = 720;
    public static final int MIN_WINDOW_WIDTH = 1024;
    public static final int MIN_WINDOW_HEIGHT = 640;

    public static final String LOGIN_BG_ASSET = "/assets/login_bg.png";
    public static final String GYM_HERO_BG_ASSET = "/assets/gym_hero_bg.png";
    public static final String LOGO_HORIZONTAL_ASSET = "/assets/hussle_fitness_logo_horizontal.png";
    public static final String LOGO_STACKED_ASSET = "/assets/hussle_fitness_logo_stacked.png";

    private static final char PASSWORD_BULLET = '\u2022';
    private static final int PEEK_DURATION_MS = 900;
    private static final String PASSWORD_ENHANCED_KEY = "ui.passwordEnhanced";
    private static final int FRAME_TRANSITION_DURATION_MS = 180;
    private static JFrame activeHostFrame;
    private static long activeTransitionToken = 0L;
    private static final Map<String, BufferedImage> IMAGE_CACHE = new ConcurrentHashMap<>();

    private UITheme() {}

    // -------------------------------------------------------------------------
    // Unified Adaptive Window & Frame Transition Helpers
    // -------------------------------------------------------------------------

    /**
     * Alias for configureWindow so dashboards and auth forms share one unified
     * adaptive full-screen window helper.
     */
    public static void configureAdaptiveFrame(JFrame frame, String title) {
        configureWindow(frame, title);
    }

    /**
     * Configures any application JFrame with unified resizable, full-screen adaptive
     * launch settings and a clean 1280x720 restored fallback size.
     */
    public static void configureWindow(JFrame frame, String title) {
        if (title != null && !title.isEmpty()) {
            frame.setTitle(title);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setMinimumSize(new Dimension(MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT));
        frame.setBackground(BG_NAVY);
        if (frame.getRootPane() != null) {
            frame.getRootPane().setBackground(BG_NAVY);
        }

        Rectangle maxBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int restoredW = Math.min(DEFAULT_WINDOW_WIDTH, maxBounds.width);
        int restoredH = Math.min(DEFAULT_WINDOW_HEIGHT, maxBounds.height);
        frame.setSize(restoredW, restoredH);
        frame.setLocationRelativeTo(null);
        frame.setMaximizedBounds(maxBounds);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    /**
     * Resolves the currently visible top-level Window for dialogs or navigation even when
     * a form's content pane is hosted inside the shared steady application JFrame.
     */
    public static Window resolveActiveWindow(Component component) {
        if (component instanceof Window window && window.isShowing()) {
            return window;
        }
        if (component != null) {
            Window ancestor = SwingUtilities.getWindowAncestor(component);
            if (ancestor != null && ancestor.isShowing()) {
                return ancestor;
            }
        }
        if (activeHostFrame != null && activeHostFrame.isShowing()) {
            return activeHostFrame;
        }
        for (Frame frame : Frame.getFrames()) {
            if (frame.isShowing()) {
                return frame;
            }
        }
        if (component instanceof Window window) {
            return window;
        }
        return component != null ? SwingUtilities.getWindowAncestor(component) : null;
    }

    private static JFrame resolveHostFrame(JFrame currentFrame) {
        if (currentFrame != null && currentFrame.isShowing()) {
            return currentFrame;
        }
        if (activeHostFrame != null && activeHostFrame.isShowing()) {
            return activeHostFrame;
        }
        for (Frame frame : Frame.getFrames()) {
            if (frame instanceof JFrame jf && jf.isShowing()) {
                return jf;
            }
        }
        return null;
    }

    /**
     * Transitions seamlessly from one form to the next inside the exact same OS window
     * (zero taskbar flicker, zero window pop/resize jump, zero white flash) with a subtle
     * HiDPI-sharp hardware-blitted crossfade while the new view settles underneath.
     */
    public static void switchFrame(JFrame currentFrame, JFrame nextFrame) {
        if (nextFrame == null) {
            return;
        }
        JFrame host = resolveHostFrame(currentFrame);
        if (host == null || host == nextFrame) {
            if (currentFrame != null && currentFrame != nextFrame) {
                int state = currentFrame.getExtendedState();
                Rectangle bounds = currentFrame.getBounds();
                nextFrame.setExtendedState(JFrame.NORMAL);
                nextFrame.setBounds(bounds);
                if ((state & Frame.MAXIMIZED_BOTH) != 0) {
                    nextFrame.setExtendedState(state);
                }
            }
            activeHostFrame = nextFrame;
            nextFrame.setVisible(true);
            if (currentFrame != null && currentFrame != nextFrame) {
                currentFrame.dispose();
            }
            return;
        }

        activeHostFrame = host;
        final long token = ++activeTransitionToken;

        CrossfadeGlassPane glassPane;
        if (host.getGlassPane() instanceof CrossfadeGlassPane existing) {
            glassPane = existing;
        } else {
            glassPane = new CrossfadeGlassPane();
            host.setGlassPane(glassPane);
        }

        Container oldContent = host.getContentPane();
        int contentW = oldContent != null ? oldContent.getWidth() : 0;
        int contentH = oldContent != null ? oldContent.getHeight() : 0;

        BufferedImage oldSnapshot = null;
        if (contentW > 0 && contentH > 0) {
            Component captureSource = glassPane.isTransitionActive() ? glassPane : oldContent;
            glassPane.stopTimerOnly();
            oldSnapshot = captureHiDpiSnapshot(captureSource, contentW, contentH);
            Point offset = (oldContent != null)
                    ? SwingUtilities.convertPoint(oldContent, 0, 0, host.getRootPane())
                    : new Point(0, 0);
            glassPane.beginStaticHold(oldSnapshot, offset.x, offset.y, contentW, contentH);
        }

        Container nextContent = nextFrame.getContentPane();
        String nextTitle = nextFrame.getTitle();
        JButton nextDefaultButton = (nextFrame.getRootPane() != null)
                ? nextFrame.getRootPane().getDefaultButton()
                : null;

        // Detach content from the undisplayed form shell and release the unused shell
        nextFrame.setContentPane(new JPanel());
        nextFrame.dispose();

        // Swap view silently inside the same steady OS window while oldSnapshot holds the screen
        host.setContentPane(nextContent);
        if (nextTitle != null && !nextTitle.isEmpty()) {
            host.setTitle(nextTitle);
        }
        if (host.getRootPane() != null) {
            host.getRootPane().setDefaultButton(nextDefaultButton);
            host.getRootPane().revalidate();
            host.getRootPane().validate();
        }

        if (oldSnapshot == null) {
            glassPane.finish();
            host.repaint();
            focusInitialComponent(nextContent);
            return;
        }

        final BufferedImage capturedOld = oldSnapshot;
        // Let one EDT pass run so deferred responsive wrappers (e.g. WrappingParagraphLabel) settle
        // completely before capturing the target snapshot and starting the smooth crossfade.
        SwingUtilities.invokeLater(() -> {
            if (token != activeTransitionToken || !host.isShowing()) {
                return;
            }
            if (host.getRootPane() != null) {
                host.getRootPane().validate();
            }
            Container liveContent = host.getContentPane();
            int w = liveContent != null ? liveContent.getWidth() : 0;
            int h = liveContent != null ? liveContent.getHeight() : 0;
            if (w <= 0 || h <= 0) {
                glassPane.finish();
                focusInitialComponent(liveContent);
                return;
            }
            BufferedImage newSnapshot = captureHiDpiSnapshot(liveContent, w, h);
            Point offset = SwingUtilities.convertPoint(liveContent, 0, 0, host.getRootPane());
            glassPane.startCrossfade(
                    capturedOld,
                    newSnapshot,
                    offset.x,
                    offset.y,
                    w,
                    h,
                    FRAME_TRANSITION_DURATION_MS,
                    () -> {
                        if (token == activeTransitionToken) {
                            focusInitialComponent(liveContent);
                        }
                    }
            );
        });
    }

    private static BufferedImage captureHiDpiSnapshot(Component comp, int logicalW, int logicalH) {
        GraphicsConfiguration gc = comp.getGraphicsConfiguration();
        double sx = 1.0;
        double sy = 1.0;
        if (gc != null) {
            AffineTransform tx = gc.getDefaultTransform();
            sx = Math.max(1.0, tx.getScaleX());
            sy = Math.max(1.0, tx.getScaleY());
        }
        int pw = Math.max(1, (int) Math.round(logicalW * sx));
        int ph = Math.max(1, (int) Math.round(logicalH * sy));

        BufferedImage img = (gc != null)
                ? gc.createCompatibleImage(pw, ph, Transparency.OPAQUE)
                : new BufferedImage(pw, ph, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = img.createGraphics();
        try {
            g2.scale(sx, sy);
            g2.setColor(BG_NAVY);
            g2.fillRect(0, 0, logicalW, logicalH);
            comp.paint(g2);
        } finally {
            g2.dispose();
        }
        return img;
    }

    private static void focusInitialComponent(Container root) {
        if (root == null) {
            return;
        }
        JComponent firstInput = findFirstFocusableInput(root);
        if (firstInput != null) {
            firstInput.requestFocusInWindow();
        } else {
            root.requestFocusInWindow();
        }
    }

    private static JComponent findFirstFocusableInput(Container container) {
        for (Component child : container.getComponents()) {
            if (!child.isVisible() || !child.isEnabled()) {
                continue;
            }
            if (child instanceof JTextField tf && tf.isEditable()) {
                return tf;
            }
            if (child instanceof Container nested) {
                JComponent found = findFirstFocusableInput(nested);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    /**
     * Hardware-compatible opaque glass pane that holds the outgoing view steady while
     * the incoming form lays out underneath, then dissolves smoothly into the new view.
     */
    private static final class CrossfadeGlassPane extends JComponent {
        private BufferedImage oldSnapshot;
        private BufferedImage newSnapshot;
        private int offsetX;
        private int offsetY;
        private int logicalW;
        private int logicalH;
        private float progress;
        private long startNanos;
        private long durationNanos;
        private Runnable onComplete;
        private final Timer animTimer;

        CrossfadeGlassPane() {
            setOpaque(true);
            setVisible(false);
            // Swallow stray input during the brief crossfade so clicks don't misfire mid-transition
            addMouseListener(new MouseAdapter() {});
            addMouseMotionListener(new MouseMotionAdapter() {});
            addMouseWheelListener(MouseWheelEvent::consume);
            addKeyListener(new KeyAdapter() {});

            animTimer = new Timer(10, e -> step());
            animTimer.setCoalesce(true);
        }

        boolean isTransitionActive() {
            return isVisible() && (oldSnapshot != null || newSnapshot != null);
        }

        void stopTimerOnly() {
            if (animTimer.isRunning()) {
                animTimer.stop();
            }
        }

        void beginStaticHold(BufferedImage snapshot, int x, int y, int w, int h) {
            stopTimerOnly();
            this.oldSnapshot = snapshot;
            this.newSnapshot = null;
            this.offsetX = x;
            this.offsetY = y;
            this.logicalW = w;
            this.logicalH = h;
            this.progress = 0f;
            this.onComplete = null;
            setVisible(true);
            repaint();
        }

        void startCrossfade(
                BufferedImage fromSnapshot,
                BufferedImage toSnapshot,
                int x,
                int y,
                int w,
                int h,
                int durationMs,
                Runnable onFinish
        ) {
            stopTimerOnly();
            this.oldSnapshot = fromSnapshot;
            this.newSnapshot = toSnapshot;
            this.offsetX = x;
            this.offsetY = y;
            this.logicalW = w;
            this.logicalH = h;
            this.progress = 0f;
            this.startNanos = System.nanoTime();
            this.durationNanos = Math.max(1L, durationMs * 1_000_000L);
            this.onComplete = onFinish;
            setVisible(true);
            repaint();
            animTimer.start();
        }

        private void step() {
            float raw = (float) ((System.nanoTime() - startNanos) / (double) durationNanos);
            if (raw >= 1f) {
                finish();
                return;
            }
            // Smooth cubic ease-out
            float inv = 1f - Math.max(0f, raw);
            this.progress = 1f - (inv * inv * inv);
            repaint();
        }

        void finish() {
            stopTimerOnly();
            Runnable callback = this.onComplete;
            this.oldSnapshot = null;
            this.newSnapshot = null;
            this.onComplete = null;
            this.progress = 1f;
            setVisible(false);
            if (callback != null) {
                callback.run();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (oldSnapshot == null && newSnapshot == null) {
                return;
            }
            int curW = getWidth();
            int curH = getHeight();
            boolean resizedMidFade = newSnapshot != null && (curW != logicalW || curH != logicalH);
            int drawW = resizedMidFade ? curW : logicalW;
            int drawH = resizedMidFade ? curH : logicalH;
            int drawX = resizedMidFade ? 0 : offsetX;
            int drawY = resizedMidFade ? 0 : offsetY;

            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                if (newSnapshot != null) {
                    g2.drawImage(newSnapshot, drawX, drawY, drawW, drawH, null);
                    float oldAlpha = Math.max(0f, Math.min(1f, 1f - progress));
                    if (oldSnapshot != null && oldAlpha > 0.005f) {
                        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, oldAlpha));
                        g2.drawImage(oldSnapshot, drawX, drawY, drawW, drawH, null);
                    }
                } else if (oldSnapshot != null) {
                    g2.drawImage(oldSnapshot, drawX, drawY, drawW, drawH, null);
                }
            } finally {
                g2.dispose();
            }

            if (resizedMidFade) {
                SwingUtilities.invokeLater(this::finish);
            }
        }
    }

    // -------------------------------------------------------------------------
    // Safe Image Loading & High-Quality Scaling Helpers
    // -------------------------------------------------------------------------

    public static BufferedImage loadImage(Class<?> anchor, String resourcePath) {
        if (resourcePath == null || resourcePath.isEmpty()) {
            return null;
        }
        BufferedImage cached = IMAGE_CACHE.get(resourcePath);
        if (cached != null) {
            return cached;
        }
        BufferedImage loaded = null;
        try {
            URL url = anchor.getResource(resourcePath);
            if (url != null) {
                loaded = ImageIO.read(url);
            }
        } catch (IOException ignored) {
            // Fall through to filesystem fallback
        }

        if (loaded == null) {
            String normalized = resourcePath.startsWith("/") ? resourcePath.substring(1) : resourcePath;
            File[] candidates = {
                    new File("src", normalized),
                    new File(normalized)
            };
            for (File file : candidates) {
                if (file.isFile()) {
                    try {
                        loaded = ImageIO.read(file);
                        if (loaded != null) {
                            break;
                        }
                    } catch (IOException ignored) {
                        // Try next candidate
                    }
                }
            }
        }
        if (loaded != null) {
            IMAGE_CACHE.putIfAbsent(resourcePath, loaded);
        }
        return loaded;
    }

    public static BufferedImage scaleImageToHeight(BufferedImage src, int targetHeight) {
        if (src == null || targetHeight <= 0) {
            return null;
        }
        int targetWidth = Math.max(1, (int) Math.round((double) src.getWidth() * targetHeight / src.getHeight()));
        BufferedImage current = src;

        // Progressive halving avoids aliasing when downscaling large PNG logos
        while (current.getWidth() / 2 >= targetWidth && current.getHeight() / 2 >= targetHeight) {
            int nextW = Math.max(targetWidth, current.getWidth() / 2);
            int nextH = Math.max(targetHeight, current.getHeight() / 2);
            BufferedImage step = new BufferedImage(nextW, nextH, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = step.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.drawImage(current, 0, 0, nextW, nextH, null);
            g2.dispose();
            current = step;
        }

        BufferedImage result = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = result.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(current, 0, 0, targetWidth, targetHeight, null);
        g2.dispose();
        return result;
    }

    /**
     * Creates a transparent top-left logo JLabel using the requested PNG asset,
     * falling back gracefully to styled text if the PNG is missing.
     */
    public static JLabel createHeaderLogoLabel(Class<?> anchor, String resourcePath, int height) {
        return createHeaderLogoLabel(anchor, resourcePath, null, height);
    }

    /**
     * Creates a transparent top-left logo JLabel trying primaryResourcePath first,
     * then fallbackResourcePath, then styled brand text.
     */
    public static JLabel createHeaderLogoLabel(Class<?> anchor, String primaryResourcePath, String fallbackResourcePath, int height) {
        BufferedImage logoRaw = loadImage(anchor, primaryResourcePath);
        if (logoRaw == null && fallbackResourcePath != null) {
            logoRaw = loadImage(anchor, fallbackResourcePath);
        }
        JLabel label;
        if (logoRaw != null) {
            label = new JLabel(new ImageIcon(scaleImageToHeight(logoRaw, height)));
        } else {
            label = new JLabel("<html><font color='#FFFFFF'>HUSSLE</font> <font color='#FFD21F'>FITNESS</font></html>");
            label.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 20));
        }
        label.setOpaque(false);
        return label;
    }

    // -------------------------------------------------------------------------
    // Resizable Dark-Athletic Modal Popup Dialog Helpers
    // -------------------------------------------------------------------------

    public static void showMessageDialog(Component parent, Object message, String title, int messageType) {
        DarkDialog.showMessage(parent, message, title, messageType);
    }

    public static int showConfirmDialog(Component parent, Object message, String title, int optionType) {
        return DarkDialog.showConfirm(parent, message, title, optionType, JOptionPane.QUESTION_MESSAGE);
    }

    public static int showConfirmDialog(Component parent, Object message, String title, int optionType, int messageType) {
        return DarkDialog.showConfirm(parent, message, title, optionType, messageType);
    }

    public static String showInputDialog(Component parent, Object message) {
        return DarkDialog.showInput(parent, message, null);
    }

    public static String showInputDialog(Component parent, Object message, Object initialValue) {
        return DarkDialog.showInput(parent, message, initialValue);
    }

    // -------------------------------------------------------------------------
    // Legacy Light-Theme Styling Methods (Preserved for Existing Forms)
    // -------------------------------------------------------------------------

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

        PasswordPeekState state = installPasswordPeek(field, TEXT_MUTED, PRIMARY_BLUE, 2);
        field.setFont(FONT_BODY);
        field.setBackground(Color.WHITE);
        field.setForeground(TEXT_DARK);
        field.setBorder(normalBorder);
        field.setEchoChar(PASSWORD_BULLET);

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
    }

    private static PasswordPeekState installPasswordPeek(
            JPasswordField field,
            Color iconNormal,
            Color iconHover,
            int rightInset) {
        PasswordPeekState state = new PasswordPeekState(field);
        field.setUI(new PeekPasswordFieldUI(state));
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

        JButton eyeButton = createEyeToggleButton(field, state, iconNormal, iconHover);
        field.setLayout(new TrailingButtonLayout(eyeButton, rightInset));
        field.add(eyeButton);
        return state;
    }

    private static JButton createEyeToggleButton(
            JPasswordField field,
            PasswordPeekState state,
            Color normalColor,
            Color hoverColor) {
        JButton btn = new JButton(new EyeIcon(false, normalColor));
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
            Color color = hovered[0] ? hoverColor : normalColor;
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

    // -------------------------------------------------------------------------
    // Reusable Hussle Fitness Dark Athletic Dashboard Styling Helpers
    // -------------------------------------------------------------------------

    /**
     * Styles a JScrollPane for dark athletic screens with sleek custom scrollbars
     * and smooth wheel scrolling.
     */
    public static void styleDarkScrollPane(JScrollPane scrollPane, boolean bordered) {
        Color bg = bordered ? SURFACE_DARK : BG_NAVY;
        scrollPane.setBackground(bg);
        scrollPane.getViewport().setBackground(bg);
        scrollPane.setOpaque(true);
        scrollPane.getViewport().setOpaque(true);
        scrollPane.setBorder(bordered ? new LineBorder(BORDER_SUBTLE, 1) : BorderFactory.createEmptyBorder());
        if (scrollPane.getVerticalScrollBar() != null) {
            scrollPane.getVerticalScrollBar().setUnitIncrement(18);
            scrollPane.getVerticalScrollBar().setUI(new DarkScrollBarUI());
            scrollPane.getVerticalScrollBar().setBackground(bg);
            scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        }
        if (scrollPane.getHorizontalScrollBar() != null) {
            scrollPane.getHorizontalScrollBar().setUnitIncrement(18);
            scrollPane.getHorizontalScrollBar().setUI(new DarkScrollBarUI());
            scrollPane.getHorizontalScrollBar().setBackground(bg);
            scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        }

        JPanel corner = new JPanel();
        corner.setBackground(SURFACE_HEADER);
        scrollPane.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, corner);
    }

    /**
     * Installs 60+ fps eased smooth mouse-wheel/trackpad scrolling on a main dashboard JScrollPane
     * and bubbles unused wheel events from nested table scroll panes so scrolling glides effortlessly
     * anywhere over the window without stuttering or tearing.
     */
    public static void installSmoothScrolling(JScrollPane mainScrollPane, JScrollPane... nestedScrollPanes) {
        if (mainScrollPane == null || mainScrollPane.getVerticalScrollBar() == null) {
            return;
        }
        JScrollBar vsb = mainScrollPane.getVerticalScrollBar();
        vsb.setUnitIncrement(18);
        mainScrollPane.setWheelScrollingEnabled(false);
        mainScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);

        final double[] currentY = {vsb.getValue()};
        final double[] targetY = {vsb.getValue()};
        final boolean[] programmatic = {false};

        Timer animTimer = new Timer(12, null);
        animTimer.addActionListener(e -> {
            int maxScroll = Math.max(0, vsb.getMaximum() - vsb.getModel().getExtent());
            targetY[0] = Math.max(0.0, Math.min(maxScroll, targetY[0]));
            double diff = targetY[0] - currentY[0];
            if (Math.abs(diff) < 0.75) {
                currentY[0] = targetY[0];
                animTimer.stop();
            } else {
                currentY[0] += diff * 0.24;
            }
            int nextVal = (int) Math.round(currentY[0]);
            if (vsb.getValue() != nextVal) {
                programmatic[0] = true;
                vsb.setValue(nextVal);
                programmatic[0] = false;
            }
        });
        mainScrollPane.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.DISPLAYABILITY_CHANGED) != 0
                    && !mainScrollPane.isDisplayable()
                    && animTimer.isRunning()) {
                animTimer.stop();
            }
        });

        vsb.addAdjustmentListener(e -> {
            if (!programmatic[0]) {
                currentY[0] = e.getValue();
                targetY[0] = e.getValue();
            }
        });

        MouseWheelListener smoothWheel = e -> {
            double rotation = e.getPreciseWheelRotation();
            if (rotation == 0.0) {
                return;
            }
            int maxScroll = Math.max(0, vsb.getMaximum() - vsb.getModel().getExtent());
            if (!animTimer.isRunning()) {
                currentY[0] = vsb.getValue();
                targetY[0] = vsb.getValue();
            }
            targetY[0] = Math.max(0.0, Math.min(maxScroll, targetY[0] + rotation * 84.0));
            if (!animTimer.isRunning()) {
                animTimer.start();
            }
            e.consume();
        };
        mainScrollPane.addMouseWheelListener(smoothWheel);

        if (nestedScrollPanes != null) {
            for (JScrollPane child : nestedScrollPanes) {
                if (child == null) {
                    continue;
                }
                child.addMouseWheelListener(e -> {
                    JScrollBar childBar = child.getVerticalScrollBar();
                    boolean childCanScroll = childBar != null && childBar.isVisible()
                            && childBar.getMaximum() > childBar.getModel().getExtent();
                    if (!childCanScroll) {
                        smoothWheel.mouseWheelMoved(e);
                        return;
                    }
                    double rot = e.getPreciseWheelRotation();
                    boolean atTop = childBar.getValue() <= childBar.getMinimum();
                    boolean atBottom = childBar.getValue() + childBar.getModel().getExtent() >= childBar.getMaximum();
                    if ((rot < 0 && atTop) || (rot > 0 && atBottom)) {
                        smoothWheel.mouseWheelMoved(e);
                    }
                });
            }
        }
    }

    /**
     * Styles a JTabbedPane into a modern dark navy tab bar with electric blue / yellow
     * active underline indicator, usable across Member, Trainer, and Admin dashboards.
     */
    public static void styleDarkTabbedPane(JTabbedPane tabs) {
        tabs.setOpaque(false);
        tabs.setBackground(BG_NAVY);
        tabs.setForeground(TEXT_SLATE);
        tabs.setFont(FONT_TAB);
        tabs.setFocusable(false);
        tabs.setBorder(BorderFactory.createEmptyBorder());
        tabs.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tabs.setUI(new DarkTabbedPaneUI());
    }

    /**
     * Styles a JTable and its enclosing JScrollPane in the Hussle Fitness dark athletic
     * theme with alternating dark rows, soft electric blue selection highlight, and
     * pill-style status badges on the specified statusColumnIndex (pass -1 if none).
     */
    public static void styleDarkTable(JTable table, JScrollPane scrollPane, int statusColumnIndex) {
        table.setFont(FONT_BODY_LG);
        table.setForeground(TEXT_LIGHT);
        table.setBackground(SURFACE_DARK);
        table.setRowHeight(44);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(BORDER_SUBTLE);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setSelectionBackground(TABLE_SELECTION_BG);
        table.setSelectionForeground(TEXT_WHITE);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        table.setFocusable(true);

        DarkTableCellRenderer defaultRenderer = new DarkTableCellRenderer();
        table.setDefaultRenderer(Object.class, defaultRenderer);

        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setResizingAllowed(true);
        header.setBackground(SURFACE_HEADER);
        header.setForeground(TEXT_SLATE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 42));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, DIVIDER_COLOR));
        header.setDefaultRenderer(new DarkTableHeaderRenderer());

        if (statusColumnIndex >= 0 && statusColumnIndex < table.getColumnModel().getColumnCount()) {
            table.getColumnModel().getColumn(statusColumnIndex).setCellRenderer(new StatusBadgeCellRenderer());
        }

        if (scrollPane != null) {
            styleDarkScrollPane(scrollPane, true);
        }
    }

    /**
     * Styles a JComboBox in the dark athletic input theme (#131C2E fill, #2A3954 border,
     * #0A84FF focus/hover highlight, custom vector chevron, and dark popup list).
     */
    public static <T> void styleDarkComboBox(JComboBox<T> combo) {
        combo.setFont(FONT_BODY_LG);
        combo.setForeground(TEXT_WHITE);
        combo.setBackground(INPUT_BG);
        combo.setOpaque(false);
        combo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        combo.setPreferredSize(new Dimension(Math.max(combo.getPreferredSize().width, 280), 44));
        combo.setMinimumSize(new Dimension(200, 44));
        combo.setUI(new DarkComboBoxUI());
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                lbl.setFont(FONT_BODY_LG);
                if (index == -1) {
                    lbl.setOpaque(false);
                    lbl.setForeground(TEXT_WHITE);
                    lbl.setBorder(new EmptyBorder(0, 14, 0, 8));
                } else {
                    lbl.setOpaque(true);
                    lbl.setBackground(isSelected ? TABLE_SELECTION_BG : SURFACE_ALT);
                    lbl.setForeground(isSelected ? TEXT_WHITE : TEXT_LIGHT);
                    lbl.setBorder(new EmptyBorder(10, 14, 10, 14));
                }
                return lbl;
            }
        });
    }

    // -------------------------------------------------------------------------
    // Reusable Hussle Fitness Dark Athletic Swing Components
    // -------------------------------------------------------------------------

    /**
     * Scrollable dark navy container that always tracks viewport width (no horizontal
     * overflow) and fills viewport height when content is shorter than the window.
     */
    public static class ScrollableDarkPanel extends JPanel implements Scrollable {
        public ScrollableDarkPanel() {
            setBackground(BG_NAVY);
            setOpaque(true);
        }

        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return getPreferredSize();
        }

        @Override
        public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 24;
        }

        @Override
        public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
            return Math.max(120, visibleRect.height - 60);
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return true;
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            Container parent = getParent();
            if (parent instanceof JViewport vp) {
                return vp.getHeight() > getPreferredSize().height;
            }
            return false;
        }
    }

    /**
     * Reusable full-width top hero header for Hussle Fitness dashboards
     * (MemberDashboard, TrainerDashboard, AdminDashboard).
     * Draws /assets/gym_hero_bg.png with high-quality bicubic cover-fit scaling anchored
     * to keep the full mascot framed, a tall multi-stop #0A0E17 gradient over the lower 50%,
     * and a smooth scroll-driven dark navy fade + 0.35x parallax shift via attachScrollFade(...).
     */
    public static class HeroHeaderPanel extends JPanel {
        public static final int DEFAULT_HERO_HEIGHT = 460;

        private final BufferedImage heroImage;
        private final BufferedImage blurredHeroImage;
        private final int bannerHeight;
        private int scrollOffsetY = 0;
        private float scrollProgress = 0f;
        private BufferedImage cachedScaledHero;
        private int cachedScaledW = -1;
        private int cachedScaledH = -1;
        private BufferedImage cachedBlurredScaled;
        private BufferedImage cachedStaticOverlays;
        private int cachedOverlayW = -1;
        private int cachedOverlayH = -1;

        public HeroHeaderPanel(BufferedImage heroImage) {
            this(heroImage, DEFAULT_HERO_HEIGHT);
        }

        public HeroHeaderPanel(BufferedImage heroImage, int bannerHeight) {
            this.heroImage = heroImage;
            this.blurredHeroImage = createSoftBlurredCopy(heroImage);
            this.bannerHeight = bannerHeight;
            setBackground(BG_NAVY);
            setOpaque(true);
        }

        /**
         * Connects this hero header to the dashboard's main JScrollPane so scrolling down
         * smoothly deepens the dark navy overlay (0% -> 85%) and applies subtle 0.35x parallax.
         */
        public void attachScrollFade(JScrollPane scrollPane) {
            if (scrollPane == null || scrollPane.getVerticalScrollBar() == null) {
                return;
            }
            JScrollBar vsb = scrollPane.getVerticalScrollBar();
            vsb.setUnitIncrement(18);
            vsb.addAdjustmentListener(e -> setScrollOffset(e.getValue()));
            setScrollOffset(vsb.getValue());
        }

        public void setScrollOffset(int scrollY) {
            int clampedY = Math.max(0, scrollY);
            float nextProgress = Math.max(0f, Math.min(1f, clampedY / 320f));
            if (clampedY != this.scrollOffsetY || Math.abs(nextProgress - this.scrollProgress) > 0.001f) {
                this.scrollOffsetY = clampedY;
                this.scrollProgress = nextProgress;
                repaint();
            }
        }

        public float getScrollProgress() {
            return scrollProgress;
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension d = super.getPreferredSize();
            return new Dimension(d.width, Math.max(bannerHeight, d.height));
        }

        @Override
        public Dimension getMinimumSize() {
            return new Dimension(400, Math.min(bannerHeight, 440));
        }

        private BufferedImage getScaledHero(int targetW, int targetH) {
            if (heroImage == null || targetW <= 0 || targetH <= 0) {
                return null;
            }
            if (cachedScaledHero != null && cachedScaledW == targetW && cachedScaledH == targetH) {
                return cachedScaledHero;
            }
            BufferedImage current = heroImage;
            while (current.getWidth() / 2 >= targetW && current.getHeight() / 2 >= targetH) {
                int nextW = Math.max(targetW, current.getWidth() / 2);
                int nextH = Math.max(targetH, current.getHeight() / 2);
                BufferedImage step = new BufferedImage(nextW, nextH, BufferedImage.TYPE_INT_ARGB);
                Graphics2D sg = step.createGraphics();
                sg.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                sg.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                sg.drawImage(current, 0, 0, nextW, nextH, null);
                sg.dispose();
                current = step;
            }
            GraphicsConfiguration gc = getGraphicsConfiguration();
            BufferedImage scaled = (gc != null)
                    ? gc.createCompatibleImage(targetW, targetH, Transparency.OPAQUE)
                    : new BufferedImage(targetW, targetH, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = scaled.createGraphics();
            g2.setColor(BG_NAVY);
            g2.fillRect(0, 0, targetW, targetH);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawImage(current, 0, 0, targetW, targetH, null);
            g2.dispose();
            cachedScaledHero = scaled;
            cachedScaledW = targetW;
            cachedScaledH = targetH;

            if (blurredHeroImage != null) {
                BufferedImage blurScaled = (gc != null)
                        ? gc.createCompatibleImage(targetW, targetH, Transparency.OPAQUE)
                        : new BufferedImage(targetW, targetH, BufferedImage.TYPE_INT_RGB);
                Graphics2D bg = blurScaled.createGraphics();
                bg.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                bg.drawImage(blurredHeroImage, 0, 0, targetW, targetH, null);
                bg.dispose();
                cachedBlurredScaled = blurScaled;
            }
            return scaled;
        }

        private BufferedImage getStaticOverlays(int w, int h) {
            if (cachedStaticOverlays != null && cachedOverlayW == w && cachedOverlayH == h) {
                return cachedStaticOverlays;
            }
            GraphicsConfiguration gc = getGraphicsConfiguration();
            BufferedImage overlay = (gc != null)
                    ? gc.createCompatibleImage(w, h, Transparency.TRANSLUCENT)
                    : new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = overlay.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Soft top dark shade behind logo & LOGOUT button
            GradientPaint topScrim = new GradientPaint(
                    0f, 0f, new Color(10, 14, 23, 195),
                    0f, Math.min(110f, h * 0.28f), new Color(10, 14, 23, 0)
            );
            g2.setPaint(topScrim);
            g2.fillRect(0, 0, w, Math.min(115, h));

            // Left-to-right dark navy scrim so the hero headline pops while mascot stays vivid
            LinearGradientPaint leftScrim = new LinearGradientPaint(
                    0f, 0f, w * 0.58f, 0f,
                    new float[]{0.0f, 0.50f, 1.0f},
                    new Color[]{
                            new Color(10, 14, 23, 205),
                            new Color(10, 14, 23, 115),
                            new Color(10, 14, 23, 0)
                    }
            );
            g2.setPaint(leftScrim);
            g2.fillRect(0, 0, w, h);

            // Tall multi-stop bottom gradient over the lower 50% into solid #0A0E17
            int fadeTop = (int) (h * 0.50);
            LinearGradientPaint bottomFade = new LinearGradientPaint(
                    0f, fadeTop, 0f, h,
                    new float[]{0.00f, 0.45f, 0.78f, 1.00f},
                    new Color[]{
                            new Color(10, 14, 23, 0),
                            new Color(10, 14, 23, 95),
                            new Color(10, 14, 23, 215),
                            new Color(10, 14, 23, 255)
                    }
            );
            g2.setPaint(bottomFade);
            g2.fillRect(0, fadeTop, w, h - fadeTop);

            g2.dispose();
            cachedStaticOverlays = overlay;
            cachedOverlayW = w;
            cachedOverlayH = h;
            return overlay;
        }

        private static BufferedImage createSoftBlurredCopy(BufferedImage src) {
            if (src == null) {
                return null;
            }
            try {
                // Downscale -> apply box blur kernel twice -> yields silky fast Gaussian-like blur
                int smallW = Math.max(64, src.getWidth() / 6);
                int smallH = Math.max(36, src.getHeight() / 6);
                BufferedImage down = new BufferedImage(smallW, smallH, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = down.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g.drawImage(src, 0, 0, smallW, smallH, null);
                g.dispose();

                float[] matrix = new float[25];
                float weight = 1.0f / 25.0f;
                for (int i = 0; i < matrix.length; i++) {
                    matrix[i] = weight;
                }
                ConvolveOp op = new ConvolveOp(new Kernel(5, 5, matrix), ConvolveOp.EDGE_NO_OP, null);
                BufferedImage pass1 = op.filter(down, null);
                return op.filter(pass1, null);
            } catch (Exception ignored) {
                return null;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            int w = getWidth();
            int h = getHeight();

            g2.setColor(BG_NAVY);
            g2.fillRect(0, 0, w, h);

            if (heroImage != null && w > 0 && h > 0) {
                int imgW = heroImage.getWidth();
                int imgH = heroImage.getHeight();
                // Cover-fit across the taller hero banner with top-center bias so the full mascot stays framed
                double scale = Math.max((double) w / imgW, (double) h / imgH);
                int drawW = (int) Math.ceil(imgW * scale);
                int drawH = (int) Math.ceil(imgH * scale);

                // Anchor right edge so the mascot on the right stays unobstructed on resize
                int drawX = w - drawW;
                // Top-center crop bias + gentle 0.35x scroll parallax
                int baseDrawY = (int) Math.round(h * 0.12 - drawH * 0.30);
                int parallaxShift = (int) Math.round(scrollOffsetY * 0.35);
                int drawY = Math.min(0, Math.max(h - drawH, baseDrawY + parallaxShift));

                BufferedImage sharp = getScaledHero(drawW, drawH);
                // Ultra-fast opaque 1:1 sub-rect blit (only visible pixels, zero resampling)
                if (sharp != null) {
                    int sx1 = Math.max(0, -drawX);
                    int sy1 = Math.max(0, -drawY);
                    int dx1 = Math.max(0, drawX);
                    int dy1 = Math.max(0, drawY);
                    int copyW = Math.min(w - dx1, drawW - sx1);
                    int copyH = Math.min(h - dy1, drawH - sy1);
                    if (copyW > 0 && copyH > 0) {
                        g2.drawImage(sharp, dx1, dy1, dx1 + copyW, dy1 + copyH,
                                sx1, sy1, sx1 + copyW, sy1 + copyH, null);
                    }
                } else {
                    g2.drawImage(heroImage, drawX, drawY, drawW, drawH, null);
                }

                // Lightweight 3-band 1:1 blur feather in the bottom fade strip
                if (cachedBlurredScaled != null) {
                    int blurStartY = (int) (h * 0.68);
                    int bands = 3;
                    int bandH = Math.max(1, (h - blurStartY + bands - 1) / bands);
                    Composite originalComp = g2.getComposite();
                    for (int i = 0; i < bands; i++) {
                        int y0 = blurStartY + i * bandH;
                        if (y0 >= h) {
                            break;
                        }
                        int sliceH = Math.min(bandH, h - y0);
                        float t = (float) (i + 1) / bands;
                        float alpha = Math.min(1.0f, t * t * 0.72f);
                        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                        int sx1 = Math.max(0, -drawX);
                        int sy1 = Math.max(0, y0 - drawY);
                        int dx1 = Math.max(0, drawX);
                        int copyW = Math.min(w - dx1, drawW - sx1);
                        int copyH = Math.min(sliceH, drawH - sy1);
                        if (copyW > 0 && copyH > 0) {
                            g2.drawImage(cachedBlurredScaled, dx1, y0, dx1 + copyW, y0 + copyH,
                                    sx1, sy1, sx1 + copyW, sy1 + copyH, null);
                        }
                    }
                    g2.setComposite(originalComp);
                }
            }

            if (w > 0 && h > 0) {
                // Fast 1:1 blit of pre-rendered top, left, and 50% bottom gradients
                g2.drawImage(getStaticOverlays(w, h), 0, 0, null);

                // Scroll-driven dark navy overlay (0% at top -> 85% as hero scrolls out)
                if (scrollProgress > 0.001f) {
                    int overlayAlpha = Math.round(scrollProgress * 217f);
                    g2.setColor(new Color(10, 14, 23, overlayAlpha));
                    g2.fillRect(0, 0, w, h);
                }
            }

            g2.dispose();
        }
    }

    /** Backward-compatible alias for HeroHeaderPanel. */
    public static class HeroBannerPanel extends HeroHeaderPanel {
        public HeroBannerPanel(BufferedImage heroImage) {
            super(heroImage);
        }

        public HeroBannerPanel(BufferedImage heroImage, int bannerHeight) {
            super(heroImage, bannerHeight);
        }
    }

    /**
     * Bold condensed uppercase hero headline ("HUSS." in #FFFFFF + " HUSS. HUSS." in #FFD21F)
     * with crisp anti-aliasing and subtle drop shadow over the hero image.
     */
    public static class HeroHeadlineLabel extends JLabel {
        private final String primaryPart;
        private final String accentPart;
        private final Font headlineFont;

        public HeroHeadlineLabel(String primaryPart, String accentPart) {
            super("");
            this.primaryPart = primaryPart != null ? primaryPart : "";
            this.accentPart = accentPart != null ? accentPart : "";
            Font base = new Font("Impact", Font.PLAIN, 50);
            if (!"Impact".equalsIgnoreCase(base.getFamily())) {
                base = new Font("Segoe UI", Font.BOLD, 48)
                        .deriveFont(AffineTransform.getScaleInstance(0.90, 1.0));
            }
            this.headlineFont = base;
            setOpaque(false);
        }

        @Override
        public Dimension getPreferredSize() {
            FontMetrics fm = getFontMetrics(headlineFont);
            int w = fm.stringWidth(primaryPart + accentPart) + 12;
            int h = fm.getHeight() + 4;
            return new Dimension(w, h);
        }

        @Override
        public Dimension getMinimumSize() {
            return getPreferredSize();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setFont(headlineFont);

            FontMetrics fm = g2.getFontMetrics();
            int y = fm.getAscent() + 2;
            int x = 0;

            // Subtle dark drop shadow for crisp readability over gym wallpaper
            g2.setColor(new Color(0, 4, 12, 165));
            g2.drawString(primaryPart + accentPart, x + 2, y + 3);

            g2.setColor(TEXT_WHITE);
            g2.drawString(primaryPart, x, y);

            int primaryW = fm.stringWidth(primaryPart);
            g2.setColor(ACCENT_YELLOW);
            g2.drawString(accentPart, x + primaryW, y);

            g2.dispose();
        }
    }

    /**
     * Large crisp hero subheadline (20pt Semibold #F1F5F9) with subtle dark drop shadow
     * and a constrained max width (~520px) so it wraps neatly in two balanced lines on the
     * left without ever overlapping the mascot.
     */
    public static class HeroSubheadlineLabel extends JLabel {
        private final String textContent;
        private final int maxLineWidth;

        public HeroSubheadlineLabel(String text) {
            this(text, 520);
        }

        public HeroSubheadlineLabel(String text, int maxLineWidth) {
            super("");
            this.textContent = text != null ? text : "";
            this.maxLineWidth = maxLineWidth;
            setFont(FONT_HERO_SUBHEADLINE);
            setForeground(TEXT_HERO_SUB);
            setOpaque(false);
        }

        private java.util.List<String> wrap(FontMetrics fm, int limitW) {
            java.util.List<String> lines = new java.util.ArrayList<>();
            String[] words = textContent.split("\\s+");
            StringBuilder cur = new StringBuilder();
            for (String word : words) {
                String cand = cur.length() == 0 ? word : cur + " " + word;
                if (cur.length() > 0 && fm.stringWidth(cand) > limitW) {
                    lines.add(cur.toString());
                    cur.setLength(0);
                    cur.append(word);
                } else {
                    cur.setLength(0);
                    cur.append(cand);
                }
            }
            if (cur.length() > 0) {
                lines.add(cur.toString());
            }
            return lines;
        }

        @Override
        public Dimension getPreferredSize() {
            FontMetrics fm = getFontMetrics(getFont());
            java.util.List<String> lines = wrap(fm, maxLineWidth);
            int maxW = 0;
            for (String line : lines) {
                maxW = Math.max(maxW, fm.stringWidth(line));
            }
            int lineH = (int) Math.round(fm.getHeight() * 1.32);
            return new Dimension(Math.min(maxLineWidth, maxW + 6), Math.max(lineH, lines.size() * lineH + 4));
        }

        @Override
        public Dimension getMinimumSize() {
            return getPreferredSize();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setFont(getFont());
            FontMetrics fm = g2.getFontMetrics();
            int limitW = getWidth() > 0 ? Math.min(maxLineWidth, getWidth()) : maxLineWidth;
            java.util.List<String> lines = wrap(fm, Math.max(220, limitW));
            int lineH = (int) Math.round(fm.getHeight() * 1.32);
            int y = fm.getAscent() + 1;
            for (String line : lines) {
                g2.setColor(new Color(0, 0, 0, 160));
                g2.drawString(line, 1, y + 2);
                g2.setColor(getForeground());
                g2.drawString(line, 0, y);
                y += lineH;
            }
            g2.dispose();
        }
    }

    /**
     * Responsive word-wrapping paragraph label that never causes horizontal overflow
     * or vertical explosion inside GridLayoutManager, with optional bold accent suffix.
     */
    public static class WrappingParagraphLabel extends JLabel {
        private final String bodyText;
        private final String accentSuffix;
        private final Color accentColor;
        private final float lineHeightMultiplier;
        private int lastLayoutWidth = -1;

        public WrappingParagraphLabel(String bodyText, String accentSuffix, Font font, Color bodyColor, Color accentColor) {
            this(bodyText, accentSuffix, font, bodyColor, accentColor, 1.48f);
        }

        public WrappingParagraphLabel(String bodyText, String accentSuffix, Font font, Color bodyColor, Color accentColor, float lineHeightMultiplier) {
            super("");
            this.bodyText = bodyText != null ? bodyText : "";
            this.accentSuffix = accentSuffix != null ? accentSuffix : "";
            this.accentColor = accentColor != null ? accentColor : ACCENT_YELLOW;
            this.lineHeightMultiplier = lineHeightMultiplier;
            setFont(font);
            setForeground(bodyColor);
            setOpaque(false);
        }

        private java.util.List<String> wrapLines(FontMetrics fmRegular, FontMetrics fmBold, int maxWidth) {
            java.util.List<String> lines = new java.util.ArrayList<>();
            String[] words = bodyText.trim().isEmpty() ? new String[0] : bodyText.trim().split("\\s+");
            StringBuilder current = new StringBuilder();
            for (String word : words) {
                String candidate = current.length() == 0 ? word : current + " " + word;
                if (current.length() > 0 && fmRegular.stringWidth(candidate) > maxWidth) {
                    lines.add(current.toString());
                    current.setLength(0);
                    current.append(word);
                } else {
                    current.setLength(0);
                    current.append(candidate);
                }
            }
            if (!accentSuffix.isEmpty()) {
                int suffixW = fmBold.stringWidth(accentSuffix);
                if (current.length() == 0) {
                    lines.add(accentSuffix);
                } else {
                    String prefixWithSpace = current + " ";
                    if (fmRegular.stringWidth(prefixWithSpace) + suffixW <= maxWidth) {
                        lines.add(prefixWithSpace + accentSuffix);
                    } else {
                        lines.add(current.toString());
                        lines.add(accentSuffix);
                    }
                }
            } else if (current.length() > 0) {
                lines.add(current.toString());
            }
            return lines;
        }

        private int computeHeightForWidth(int width) {
            Font regular = getFont();
            FontMetrics fm = getFontMetrics(regular);
            FontMetrics fmBold = getFontMetrics(regular.deriveFont(Font.BOLD));
            int effectiveW = Math.max(240, width);
            java.util.List<String> lines = wrapLines(fm, fmBold, effectiveW);
            if (lines.isEmpty()) {
                return fm.getHeight();
            }
            int stepH = (int) Math.round(fm.getHeight() * lineHeightMultiplier);
            // Exact height with zero trailing blank space below the last line
            return (lines.size() - 1) * stepH + fm.getHeight() + 2;
        }

        @Override
        public Dimension getMinimumSize() {
            int w = getWidth() > 0 ? getWidth() : 1100;
            return new Dimension(200, computeHeightForWidth(w));
        }

        @Override
        public Dimension getPreferredSize() {
            int w = getWidth() > 0 ? getWidth() : 1100;
            return new Dimension(400, computeHeightForWidth(w));
        }

        @Override
        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, width, height);
            if (width > 0 && width != lastLayoutWidth) {
                lastLayoutWidth = width;
                // Defer revalidation past current layout pass so parent card shrinks tightly to fit text
                SwingUtilities.invokeLater(() -> {
                    revalidate();
                    Container ancestor = SwingUtilities.getAncestorOfClass(ScrollableDarkPanel.class, this);
                    if (ancestor != null) {
                        ancestor.revalidate();
                        ancestor.repaint();
                    }
                });
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            int w = getWidth();
            if (w <= 0) {
                return;
            }
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            Font regular = getFont();
            Font bold = regular.deriveFont(Font.BOLD);
            g2.setFont(regular);
            FontMetrics fm = g2.getFontMetrics();
            FontMetrics fmBold = g2.getFontMetrics(bold);
            java.util.List<String> lines = wrapLines(fm, fmBold, Math.max(240, w));

            int stepH = (int) Math.round(fm.getHeight() * lineHeightMultiplier);
            int y = fm.getAscent();
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (!accentSuffix.isEmpty() && i == lines.size() - 1 && line.endsWith(accentSuffix)) {
                    String prefix = line.substring(0, line.length() - accentSuffix.length());
                    g2.setFont(regular);
                    g2.setColor(getForeground());
                    g2.drawString(prefix, 0, y);
                    int px = fm.stringWidth(prefix);
                    g2.setFont(bold);
                    g2.setColor(accentColor);
                    g2.drawString(accentSuffix, px, y);
                } else {
                    g2.setFont(regular);
                    g2.setColor(getForeground());
                    g2.drawString(line, 0, y);
                }
                y += stepH;
            }
            g2.dispose();
        }
    }

    /**
     * Frosted dark navy dashboard glass card (#0E1525 translucent fill, 16px rounded corners,
     * 1px soft electric blue border rgba(10, 132, 255, 0.35)) suitable for welcome cards,
     * status metric cards, and tabbed content panels across all dashboards.
     */
    public static class DarkGlassCard extends JPanel {
        private static final int ARC = 32;

        public DarkGlassCard() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            int w = getWidth();
            int h = getHeight();
            if (w > 2 && h > 2) {
                RoundRectangle2D card = new RoundRectangle2D.Double(1, 1, w - 2, h - 2, ARC, ARC);

                // Translucent dark navy fill
                g2.setColor(DASHBOARD_CARD_FILL);
                g2.fill(card);

                // Subtle top frosted sheen
                g2.setPaint(new GradientPaint(
                        0, 1, new Color(255, 255, 255, 14),
                        0, Math.min(90f, h * 0.45f), new Color(255, 255, 255, 0)
                ));
                g2.fill(card);

                // 1px soft electric blue border (rgba(10, 132, 255, 0.35))
                g2.setStroke(new BasicStroke(1.1f));
                g2.setColor(BORDER_BLUE_SOFT);
                g2.draw(new RoundRectangle2D.Double(0.75, 0.75, w - 1.5, h - 1.5, ARC, ARC));
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /**
     * Subtle dark rounded inset panel (#131C2E, 12px corners, 1px #26334D border)
     * for highlighted summary blocks such as "Amount Due".
     */
    public static class DarkInsetPanel extends JPanel {
        private static final int ARC = 24;

        public DarkInsetPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            if (w > 2 && h > 2) {
                RoundRectangle2D shape = new RoundRectangle2D.Double(1, 1, w - 2, h - 2, ARC, ARC);
                g2.setColor(SURFACE_ALT);
                g2.fill(shape);

                g2.setStroke(new BasicStroke(1.0f));
                g2.setColor(DIVIDER_COLOR);
                g2.draw(new RoundRectangle2D.Double(0.75, 0.75, w - 1.5, h - 1.5, ARC, ARC));
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /**
     * Glowing circular status dot icon (e.g. #22C55E green for Active status card).
     */
    public static final class StatusDotIcon implements Icon {
        private final Color color;
        private final int size;

        public StatusDotIcon(Color color) {
            this(color, 14);
        }

        public StatusDotIcon(Color color, int size) {
            this.color = color;
            this.size = size;
        }

        @Override
        public int getIconWidth() {
            return size;
        }

        @Override
        public int getIconHeight() {
            return size;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Soft outer glow halo
            g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 68));
            g2.fill(new Ellipse2D.Double(x, y, size, size));

            // Crisp inner solid dot
            double pad = size * 0.22;
            g2.setColor(color);
            g2.fill(new Ellipse2D.Double(x + pad, y + pad, size - pad * 2, size - pad * 2));
            g2.dispose();
        }
    }

    /**
     * Slim dark rounded scrollbar UI matching the Hussle Fitness dark theme.
     */
    public static class DarkScrollBarUI extends BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            thumbColor = new Color(0x2A3954);
            thumbHighlightColor = ELECTRIC_BLUE;
            trackColor = BG_NAVY;
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
            JButton btn = new JButton();
            btn.setPreferredSize(new Dimension(0, 0));
            btn.setMinimumSize(new Dimension(0, 0));
            btn.setMaximumSize(new Dimension(0, 0));
            return btn;
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            g.setColor(c.getBackground());
            g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            if (!c.isEnabled() || thumbBounds.width <= 0 || thumbBounds.height <= 0) {
                return;
            }
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(isThumbRollover() ? ELECTRIC_BLUE : new Color(0x334155));
            g2.fillRoundRect(thumbBounds.x + 2, thumbBounds.y + 2,
                    Math.max(4, thumbBounds.width - 4), Math.max(4, thumbBounds.height - 4), 8, 8);
            g2.dispose();
        }
    }

    /**
     * Modern dark athletic TabbedPaneUI with clean horizontal tabs, hover tint,
     * and an electric blue/yellow active bottom indicator bar.
     */
    public static class DarkTabbedPaneUI extends BasicTabbedPaneUI {
        private int hoveredTab = -1;

        @Override
        protected void installDefaults() {
            super.installDefaults();
            tabAreaInsets = new Insets(0, 0, 10, 0);
            contentBorderInsets = new Insets(0, 0, 0, 0);
            tabInsets = new Insets(10, 22, 12, 22);
            selectedTabPadInsets = new Insets(0, 0, 0, 0);
        }

        @Override
        protected void installListeners() {
            super.installListeners();
            tabPane.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    int idx = tabForCoordinate(tabPane, e.getX(), e.getY());
                    if (idx != hoveredTab) {
                        hoveredTab = idx;
                        tabPane.repaint();
                    }
                }
            });
            tabPane.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseExited(MouseEvent e) {
                    if (hoveredTab != -1) {
                        hoveredTab = -1;
                        tabPane.repaint();
                    }
                }
            });
        }

        @Override
        protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
            return Math.max(42, fontHeight + 22);
        }

        @Override
        protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
            return super.calculateTabWidth(tabPlacement, tabIndex, metrics) + 12;
        }

        @Override
        protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
            Graphics2D g2 = (Graphics2D) g.create();
            // Subtle baseline separator beneath the tab strip
            int lineY = calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight) - 10;
            g2.setColor(BORDER_SUBTLE);
            g2.fillRect(0, lineY, tabPane.getWidth(), 1);
            g2.dispose();
            super.paintTabArea(g, tabPlacement, selectedIndex);
        }

        @Override
        protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex,
                                          int x, int y, int w, int h, boolean isSelected) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (isSelected) {
                g2.setColor(new Color(19, 28, 46, 210));
                g2.fill(new RoundRectangle2D.Double(x + 2, y + 2, w - 4, h - 4, 14, 14));

                // Electric blue-to-yellow active indicator bar
                GradientPaint indicator = new GradientPaint(
                        x + 12, y + h - 3, ELECTRIC_BLUE,
                        x + w - 12, y + h - 3, ACCENT_YELLOW
                );
                g2.setPaint(indicator);
                g2.fill(new RoundRectangle2D.Double(x + 10, y + h - 4, w - 20, 3.2, 4, 4));
            } else if (tabIndex == hoveredTab) {
                g2.setColor(new Color(255, 255, 255, 12));
                g2.fill(new RoundRectangle2D.Double(x + 2, y + 2, w - 4, h - 4, 14, 14));
            }
            g2.dispose();
        }

        @Override
        protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
                                      int x, int y, int w, int h, boolean isSelected) {
            // Borderless sleek tabs
        }

        @Override
        protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
            // Content tabs themselves render inside DarkGlassCard
        }

        @Override
        protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects,
                                           int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
            // Suppress dotted Metal focus rectangle
        }

        @Override
        protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics,
                                 int tabIndex, String title, Rectangle textRect, boolean isSelected) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setFont(font);
            g2.setColor(isSelected ? TEXT_WHITE : (tabIndex == hoveredTab ? TEXT_LIGHT : TEXT_SLATE));
            int tx = textRect.x;
            int ty = textRect.y + metrics.getAscent();
            g2.drawString(title, tx, ty);
            g2.dispose();
        }
    }

    /**
     * Dark athletic JTable header renderer with crisp uppercase/bold typography and bottom divider.
     */
    public static class DarkTableHeaderRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            lbl.setOpaque(true);
            lbl.setBackground(SURFACE_HEADER);
            lbl.setForeground(TEXT_SLATE);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lbl.setHorizontalAlignment(SwingConstants.LEFT);
            lbl.setBorder(new EmptyBorder(0, 16, 0, 12));
            return lbl;
        }
    }

    /**
     * Dark alternating row renderer with soft electric blue selection highlight and left accent bar.
     */
    public static class DarkTableCellRenderer extends DefaultTableCellRenderer {
        private boolean selectedRow = false;
        private boolean firstColumn = false;

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, false, row, column);
            selectedRow = isSelected;
            firstColumn = (column == 0);
            lbl.setOpaque(true);
            lbl.setFont(column == 0 ? FONT_BOLD : FONT_BODY_LG);
            lbl.setBorder(new EmptyBorder(0, 16, 0, 12));

            if (isSelected) {
                lbl.setBackground(TABLE_SELECTION_BG);
                lbl.setForeground(TEXT_WHITE);
            } else {
                lbl.setBackground((row % 2 == 0) ? SURFACE_DARK : SURFACE_ALT);
                lbl.setForeground(column == 0 ? ELECTRIC_BLUE_LIGHT : TEXT_LIGHT);
            }
            return lbl;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (selectedRow && firstColumn) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(ELECTRIC_BLUE);
                g2.fillRect(0, 0, 3, getHeight());
                g2.dispose();
            }
        }
    }

    /**
     * Pill-style status badge renderer for JTable status columns:
     * - Blue pill for "Booked"
     * - Green pill for "Available (...)", "Present", "Active"
     * - Amber pill for "Full", "Pending", "Expired"
     * - Red pill for "Cancelled", "Absent", "Inactive"
     */
    public static class StatusBadgeCellRenderer extends JPanel implements TableCellRenderer {
        private String text = "";
        private Color pillBg = new Color(34, 197, 94, 42);
        private Color pillBorder = STATUS_GREEN;
        private Color pillText = STATUS_GREEN_TEXT;

        public StatusBadgeCellRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            text = value != null ? value.toString().trim() : "";
            setBackground(isSelected ? TABLE_SELECTION_BG : ((row % 2 == 0) ? SURFACE_DARK : SURFACE_ALT));

            String lower = text.toLowerCase();
            if (lower.startsWith("booked") || lower.equals("scheduled")) {
                pillBg = new Color(10, 132, 255, 52);
                pillBorder = ELECTRIC_BLUE;
                pillText = STATUS_BLUE_TEXT;
            } else if (lower.startsWith("available") || lower.equals("present") || lower.equals("active") || lower.equals("completed")) {
                pillBg = new Color(34, 197, 94, 42);
                pillBorder = STATUS_GREEN;
                pillText = STATUS_GREEN_TEXT;
            } else if (lower.equals("full") || lower.equals("pending") || lower.equals("expired")) {
                pillBg = new Color(255, 210, 31, 36);
                pillBorder = ACCENT_YELLOW;
                pillText = ACCENT_YELLOW;
            } else if (lower.startsWith("cancel") || lower.equals("absent") || lower.equals("inactive")) {
                pillBg = new Color(239, 68, 68, 42);
                pillBorder = STATUS_RED;
                pillText = STATUS_RED_TEXT;
            } else {
                pillBg = new Color(148, 163, 184, 34);
                pillBorder = TEXT_SLATE;
                pillText = TEXT_LIGHT;
            }
            return this;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (text.isEmpty()) {
                return;
            }
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            Font badgeFont = new Font("Segoe UI", Font.BOLD, 12);
            g2.setFont(badgeFont);
            FontMetrics fm = g2.getFontMetrics();

            int padH = 12;
            int pillH = 26;
            int pillW = Math.min(getWidth() - 24, fm.stringWidth(text) + padH * 2);
            int px = 14;
            int py = (getHeight() - pillH) / 2;

            RoundRectangle2D pill = new RoundRectangle2D.Double(px, py, pillW, pillH, pillH, pillH);
            g2.setColor(pillBg);
            g2.fill(pill);

            g2.setStroke(new BasicStroke(1.1f));
            g2.setColor(pillBorder);
            g2.draw(pill);

            g2.setColor(pillText);
            int tx = px + (pillW - fm.stringWidth(text)) / 2;
            int ty = py + (pillH - fm.getHeight()) / 2 + fm.getAscent();
            g2.drawString(text, tx, ty);

            g2.dispose();
        }
    }

    /**
     * Custom dark rounded JComboBox UI matching DarkTextField (#131C2E, #2A3954 / #0A84FF border).
     */
    public static class DarkComboBoxUI extends BasicComboBoxUI {
        private boolean hovered = false;

        @Override
        protected void installListeners() {
            super.installListeners();
            comboBox.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    hovered = true;
                    comboBox.repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hovered = false;
                    comboBox.repaint();
                }
            });
            comboBox.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    comboBox.repaint();
                }

                @Override
                public void focusLost(FocusEvent e) {
                    comboBox.repaint();
                }
            });
        }

        @Override
        protected JButton createArrowButton() {
            JButton btn = new JButton() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(hovered || comboBox.hasFocus() ? ELECTRIC_BLUE_LIGHT : TEXT_SLATE);
                    g2.setStroke(new BasicStroke(1.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    int cx = getWidth() / 2;
                    int cy = getHeight() / 2;
                    Path2D chevron = new Path2D.Double();
                    chevron.moveTo(cx - 5, cy - 2);
                    chevron.lineTo(cx, cy + 3);
                    chevron.lineTo(cx + 5, cy - 2);
                    g2.draw(chevron);
                    g2.dispose();
                }
            };
            btn.setOpaque(false);
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            return btn;
        }

        @Override
        protected ComboPopup createPopup() {
            BasicComboPopup popup = new BasicComboPopup(comboBox) {
                @Override
                protected JScrollPane createScroller() {
                    JScrollPane scroller = super.createScroller();
                    styleDarkScrollPane(scroller, false);
                    scroller.getViewport().setBackground(SURFACE_ALT);
                    return scroller;
                }
            };
            popup.setBorder(new LineBorder(INPUT_BORDER, 1));
            popup.getList().setBackground(SURFACE_ALT);
            popup.getList().setForeground(TEXT_LIGHT);
            popup.getList().setSelectionBackground(TABLE_SELECTION_BG);
            popup.getList().setSelectionForeground(TEXT_WHITE);
            return popup;
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = c.getWidth();
            int h = c.getHeight();
            int arc = 20;

            g2.setColor(INPUT_BG);
            g2.fill(new RoundRectangle2D.Double(1, 1, w - 2, h - 2, arc, arc));

            if (comboBox.hasFocus() || isPopupVisible(comboBox)) {
                g2.setStroke(new BasicStroke(2.6f));
                g2.setColor(new Color(10, 132, 255, 45));
                g2.draw(new RoundRectangle2D.Double(1.4, 1.4, w - 2.8, h - 2.8, arc, arc));

                g2.setStroke(new BasicStroke(1.5f));
                g2.setColor(ELECTRIC_BLUE);
            } else {
                g2.setStroke(new BasicStroke(1.2f));
                g2.setColor(hovered ? ELECTRIC_BLUE_LIGHT : INPUT_BORDER);
            }
            g2.draw(new RoundRectangle2D.Double(1, 1, w - 2, h - 2, arc, arc));
            g2.dispose();

            Rectangle bounds = rectangleForCurrentValue();
            paintCurrentValue(g, bounds, false);
        }

        @Override
        public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
            ListCellRenderer<Object> renderer = comboBox.getRenderer();
            Component c = renderer.getListCellRendererComponent(
                    listBox, comboBox.getSelectedItem(), -1, false, false);
            c.setFont(comboBox.getFont());
            c.setForeground(TEXT_WHITE);
            if (c instanceof JComponent jc) {
                jc.setOpaque(false);
            }
            currentValuePane.paintComponent(g, c, comboBox, bounds.x, bounds.y, bounds.width, bounds.height, true);
        }

        @Override
        public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
            // Painted by custom rounded fill in paint()
        }
    }

    /**
     * Full-bleed background panel that renders a wallpaper using cover-fit math
     * anchored to the right side (keeping the mascot sharp and unclipped) plus
     * a smooth left-to-right dark navy scrim gradient for high card contrast.
     */
    public static class BackgroundPanel extends JPanel {
        private final BufferedImage backgroundImage;

        public BackgroundPanel(BufferedImage backgroundImage) {
            this.backgroundImage = backgroundImage;
            setBackground(BG_NAVY);
            setOpaque(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            int w = getWidth();
            int h = getHeight();

            // Clean dark navy fallback background
            g2.setColor(BG_NAVY);
            g2.fillRect(0, 0, w, h);

            if (backgroundImage != null && w > 0 && h > 0) {
                int imgW = backgroundImage.getWidth();
                int imgH = backgroundImage.getHeight();
                double scale = Math.max((double) w / imgW, (double) h / imgH);
                int drawW = (int) Math.ceil(imgW * scale);
                int drawH = (int) Math.ceil(imgH * scale);

                // Anchor right edge so the mascot on the right never clips on resize
                int drawX = w - drawW;
                int drawY = (int) Math.round((h - drawH) * 0.35);
                g2.drawImage(backgroundImage, drawX, drawY, drawW, drawH, null);
            }

            // Smooth horizontal gradient scrim from left (deep dark navy) to transparent at ~58% width
            if (w > 0 && h > 0) {
                GradientPaint leftScrim = new GradientPaint(
                        0f, 0f, new Color(10, 14, 23, 235),
                        w * 0.58f, 0f, new Color(10, 14, 23, 0)
                );
                g2.setPaint(leftScrim);
                g2.fillRect(0, 0, w, h);

                // Subtle top vignette for top-left logo readability
                GradientPaint topScrim = new GradientPaint(
                        0f, 0f, new Color(10, 14, 23, 125),
                        0f, 92f, new Color(10, 14, 23, 0)
                );
                g2.setPaint(topScrim);
                g2.fillRect(0, 0, w, Math.min(h, 96));
            }

            g2.dispose();
        }
    }

    /**
     * Frosted dark navy glass card panel with 16px rounded corners (arc=32),
     * semi-transparent fill, thin electric blue border, and soft outer drop shadow.
     * Compatible with IntelliJ .form GridLayoutManager margins via custom-create="true".
     */
    public static class GlassCardPanel extends JPanel {
        private static final int ARC = 32;
        private static final int SHADOW_TOP = 6;
        private static final int SHADOW_SIDE = 10;
        private static final int SHADOW_BOTTOM = 12;

        public GlassCardPanel() {
            this(0, 0, 0, 0);
        }

        public GlassCardPanel(int padTop, int padLeft, int padBottom, int padRight) {
            setOpaque(false);
            setBorder(new EmptyBorder(
                    padTop + SHADOW_TOP,
                    padLeft + SHADOW_SIDE,
                    padBottom + SHADOW_BOTTOM,
                    padRight + SHADOW_SIDE
            ));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            int x = SHADOW_SIDE;
            int y = SHADOW_TOP;
            int w = getWidth() - (SHADOW_SIDE * 2);
            int h = getHeight() - SHADOW_TOP - SHADOW_BOTTOM;

            if (w > 0 && h > 0) {
                // Soft layered outer drop shadow
                int[] alphas = {10, 16, 22, 28};
                for (int i = 0; i < alphas.length; i++) {
                    int spread = (alphas.length - i) * 2;
                    g2.setColor(new Color(0, 4, 12, alphas[i]));
                    g2.fill(new RoundRectangle2D.Double(
                            x - spread * 0.5,
                            y + spread * 0.6,
                            w + spread,
                            h + spread,
                            ARC + spread,
                            ARC + spread
                    ));
                }

                RoundRectangle2D cardShape = new RoundRectangle2D.Double(x, y, w, h, ARC, ARC);

                // Semi-transparent dark navy glass fill
                g2.setColor(GLASS_CARD_FILL);
                g2.fill(cardShape);

                // Subtle frosted inner vertical highlight sheen
                GradientPaint sheen = new GradientPaint(
                        x, y, new Color(255, 255, 255, 16),
                        x, y + h * 0.45f, new Color(255, 255, 255, 0)
                );
                g2.setPaint(sheen);
                g2.fill(cardShape);

                // Soft electric blue outer halo
                g2.setStroke(new BasicStroke(3.0f));
                g2.setColor(new Color(10, 132, 255, 32));
                g2.draw(new RoundRectangle2D.Double(x - 0.5, y - 0.5, w + 1.0, h + 1.0, ARC + 1, ARC + 1));

                // Crisp 1.5px electric blue glass border
                g2.setStroke(new BasicStroke(1.5f));
                g2.setColor(GLASS_CARD_BORDER);
                g2.draw(new RoundRectangle2D.Double(x + 0.75, y + 0.75, w - 1.5, h - 1.5, ARC, ARC));
            }

            g2.dispose();
            super.paintComponent(g);
        }
    }

    /**
     * Rounded dark athletic JTextField with focus highlight, left icon, and placeholder text.
     */
    public static class DarkTextField extends JTextField {
        private static final int ARC = 20;

        private final int fieldHeight;
        private String placeholder;
        private final Icon leadingIcon;
        private final Icon focusedLeadingIcon;
        private boolean focused = false;

        public DarkTextField(String placeholder) {
            this(placeholder, new UserIcon(TEXT_PLACEHOLDER), new UserIcon(ELECTRIC_BLUE_LIGHT), 42);
        }

        public DarkTextField(String placeholder, int height) {
            this(placeholder, new UserIcon(TEXT_PLACEHOLDER), new UserIcon(ELECTRIC_BLUE_LIGHT), height);
        }

        public DarkTextField(String placeholder, Icon leadingIcon, Icon focusedLeadingIcon) {
            this(placeholder, leadingIcon, focusedLeadingIcon, 42);
        }

        public DarkTextField(String placeholder, Icon leadingIcon, Icon focusedLeadingIcon, int height) {
            this.placeholder = placeholder;
            this.leadingIcon = leadingIcon;
            this.focusedLeadingIcon = focusedLeadingIcon;
            this.fieldHeight = height;

            setOpaque(false);
            setFont(FONT_BODY);
            setForeground(TEXT_WHITE);
            setCaretColor(ELECTRIC_BLUE);
            setSelectionColor(new Color(10, 132, 255, 100));
            setSelectedTextColor(TEXT_WHITE);

            int leftPad = (leadingIcon != null) ? 40 : 14;
            setBorder(new EmptyBorder(0, leftPad, 0, 14));

            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    focused = true;
                    repaint();
                }

                @Override
                public void focusLost(FocusEvent e) {
                    focused = false;
                    repaint();
                }
            });

            getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    repaint();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    repaint();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    repaint();
                }
            });
        }

        public void setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
            repaint();
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension d = super.getPreferredSize();
            return new Dimension(Math.max(d.width, 260), fieldHeight);
        }

        @Override
        public Dimension getMinimumSize() {
            return new Dimension(180, fieldHeight);
        }

        @Override
        public Dimension getMaximumSize() {
            return new Dimension(Integer.MAX_VALUE, fieldHeight);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            // Rounded input fill
            g2.setColor(INPUT_BG);
            g2.fill(new RoundRectangle2D.Double(1, 1, w - 2, h - 2, ARC, ARC));

            // Focus glow + border
            if (focused) {
                g2.setStroke(new BasicStroke(2.8f));
                g2.setColor(new Color(10, 132, 255, 45));
                g2.draw(new RoundRectangle2D.Double(1.4, 1.4, w - 2.8, h - 2.8, ARC, ARC));

                g2.setStroke(new BasicStroke(1.5f));
                g2.setColor(ELECTRIC_BLUE);
            } else {
                g2.setStroke(new BasicStroke(1.2f));
                g2.setColor(INPUT_BORDER);
            }
            g2.draw(new RoundRectangle2D.Double(1, 1, w - 2, h - 2, ARC, ARC));

            // Left vector icon
            Icon iconToDraw = (focused && focusedLeadingIcon != null) ? focusedLeadingIcon : leadingIcon;
            if (iconToDraw != null) {
                int iy = (h - iconToDraw.getIconHeight()) / 2;
                iconToDraw.paintIcon(this, g2, 13, iy);
            }

            g2.dispose();

            // Paint typed text & caret
            super.paintComponent(g);

            // Custom-painted placeholder when empty
            if (placeholder != null && !placeholder.isEmpty() && getText().isEmpty()) {
                Graphics2D pg = (Graphics2D) g.create();
                pg.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                pg.setFont(getFont());
                pg.setColor(TEXT_PLACEHOLDER);
                Insets insets = getInsets();
                FontMetrics fm = pg.getFontMetrics();
                int baseline = (h - fm.getHeight()) / 2 + fm.getAscent();
                pg.drawString(placeholder, insets.left, baseline);
                pg.dispose();
            }
        }
    }

    /**
     * Rounded dark athletic JPasswordField with left lock icon, right eye toggle,
     * character peek-on-type, focus highlight, and optional placeholder.
     */
    public static class DarkPasswordField extends JPasswordField {
        private static final int ARC = 20;

        private final int fieldHeight;
        private final String placeholder;
        private final Icon leadingIcon;
        private final Icon focusedLeadingIcon;
        private final PasswordPeekState peekState;
        private boolean focused = false;

        public DarkPasswordField(String placeholder) {
            this(placeholder, 42);
        }

        public DarkPasswordField(String placeholder, int height) {
            this.placeholder = placeholder;
            this.fieldHeight = height;
            this.leadingIcon = new LockIcon(TEXT_PLACEHOLDER);
            this.focusedLeadingIcon = new LockIcon(ELECTRIC_BLUE_LIGHT);

            putClientProperty(PASSWORD_ENHANCED_KEY, Boolean.TRUE);
            this.peekState = installPasswordPeek(this, TEXT_SLATE, ELECTRIC_BLUE_LIGHT, 8);

            setOpaque(false);
            setFont(FONT_BODY);
            setForeground(TEXT_WHITE);
            setCaretColor(ELECTRIC_BLUE);
            setSelectionColor(new Color(10, 132, 255, 100));
            setSelectedTextColor(TEXT_WHITE);
            setBorder(new EmptyBorder(0, 40, 0, 42));

            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    focused = true;
                    repaint();
                }

                @Override
                public void focusLost(FocusEvent e) {
                    focused = false;
                    peekState.clearPeek();
                    repaint();
                }
            });
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension d = super.getPreferredSize();
            return new Dimension(Math.max(d.width, 260), fieldHeight);
        }

        @Override
        public Dimension getMinimumSize() {
            return new Dimension(180, fieldHeight);
        }

        @Override
        public Dimension getMaximumSize() {
            return new Dimension(Integer.MAX_VALUE, fieldHeight);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            // Rounded input fill
            g2.setColor(INPUT_BG);
            g2.fill(new RoundRectangle2D.Double(1, 1, w - 2, h - 2, ARC, ARC));

            // Focus glow + border
            if (focused) {
                g2.setStroke(new BasicStroke(2.8f));
                g2.setColor(new Color(10, 132, 255, 45));
                g2.draw(new RoundRectangle2D.Double(1.4, 1.4, w - 2.8, h - 2.8, ARC, ARC));

                g2.setStroke(new BasicStroke(1.5f));
                g2.setColor(ELECTRIC_BLUE);
            } else {
                g2.setStroke(new BasicStroke(1.2f));
                g2.setColor(INPUT_BORDER);
            }
            g2.draw(new RoundRectangle2D.Double(1, 1, w - 2, h - 2, ARC, ARC));

            // Left lock icon
            Icon iconToDraw = focused ? focusedLeadingIcon : leadingIcon;
            int iy = (h - iconToDraw.getIconHeight()) / 2;
            iconToDraw.paintIcon(this, g2, 13, iy);

            g2.dispose();

            // Paint password glyphs & caret
            super.paintComponent(g);

            // Placeholder when password is empty
            if (placeholder != null && !placeholder.isEmpty() && getDocument().getLength() == 0) {
                Graphics2D pg = (Graphics2D) g.create();
                pg.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                pg.setFont(getFont());
                pg.setColor(TEXT_PLACEHOLDER);
                Insets insets = getInsets();
                FontMetrics fm = pg.getFontMetrics();
                int baseline = (h - fm.getHeight()) / 2 + fm.getAscent();
                pg.drawString(placeholder, insets.left, baseline);
                pg.dispose();
            }
        }
    }

    /**
     * Primary gradient action button (#0A84FF to #0066CC) with 10px rounded corners.
     */
    public static class GradientButton extends JButton {
        private static final int ARC = 20;

        private final Color startColor;
        private final Color endColor;
        private final int buttonHeight;
        private final int minButtonWidth;
        private boolean hovered = false;
        private boolean pressed = false;

        public GradientButton(String text) {
            this(text, ELECTRIC_BLUE, ELECTRIC_BLUE_DARK, 44, 240);
        }

        public GradientButton(String text, int height, int minWidth) {
            this(text, ELECTRIC_BLUE, ELECTRIC_BLUE_DARK, height, minWidth);
        }

        public GradientButton(String text, Color startColor, Color endColor) {
            this(text, startColor, endColor, 44, 240);
        }

        public GradientButton(String text, Color startColor, Color endColor, int height, int minWidth) {
            super(text);
            this.startColor = startColor;
            this.endColor = endColor;
            this.buttonHeight = height;
            this.minButtonWidth = minWidth;

            setFont(FONT_BUTTON_ACTION);
            setForeground(TEXT_WHITE);
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setHorizontalAlignment(SwingConstants.CENTER);

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
            Dimension d = super.getPreferredSize();
            return new Dimension(Math.max(d.width + 28, minButtonWidth), buttonHeight);
        }

        @Override
        public Dimension getMinimumSize() {
            return new Dimension(Math.min(140, minButtonWidth), buttonHeight);
        }

        @Override
        public Dimension getMaximumSize() {
            return new Dimension(Integer.MAX_VALUE, buttonHeight);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            Color c1 = startColor;
            Color c2 = endColor;
            if (pressed) {
                c1 = adjustBrightness(startColor, -18);
                c2 = adjustBrightness(endColor, -18);
            } else if (hovered) {
                c1 = adjustBrightness(startColor, 22);
                c2 = adjustBrightness(endColor, 20);
            }

            RoundRectangle2D shape = new RoundRectangle2D.Double(0, 0, w, h, ARC, ARC);
            g2.setPaint(new GradientPaint(0, 0, c1, w, 0, c2));
            g2.fill(shape);

            // Subtle top specular highlight
            g2.setPaint(new GradientPaint(0, 0, new Color(255, 255, 255, 34), 0, h * 0.5f, new Color(255, 255, 255, 0)));
            g2.fill(shape);

            // Centered bold white label
            g2.setFont(getFont());
            g2.setColor(getForeground());
            FontMetrics fm = g2.getFontMetrics();
            String text = getText() != null ? getText() : "";
            int tx = (w - fm.stringWidth(text)) / 2;
            int ty = (h - fm.getHeight()) / 2 + fm.getAscent();
            g2.drawString(text, tx, ty);

            g2.dispose();
        }
    }

    /**
     * Secondary outlined button with dark translucent tint and customizable accent border
     * (used for yellow CREATE NEW ACCOUNT, red CANCEL SESSION, and slate LOGOUT buttons).
     */
    public static class OutlineAccentButton extends JButton {
        private final Color accentColor;
        private final int buttonHeight;
        private final int minButtonWidth;
        private final float strokeWidth;
        private final int arc;
        private boolean hovered = false;
        private boolean pressed = false;

        public OutlineAccentButton(String text) {
            this(text, ACCENT_YELLOW, ACCENT_YELLOW, 44, 240, 1.5f, 20);
        }

        public OutlineAccentButton(String text, Color accentColor) {
            this(text, accentColor, accentColor, 44, 240, 1.5f, 20);
        }

        public OutlineAccentButton(String text, Color borderColor, Color textColor,
                                   int height, int minWidth, float strokeWidth, int arc) {
            super(text);
            this.accentColor = borderColor;
            this.buttonHeight = height;
            this.minButtonWidth = minWidth;
            this.strokeWidth = strokeWidth;
            this.arc = arc;

            setFont(FONT_BUTTON_ACTION);
            setForeground(textColor);
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setHorizontalAlignment(SwingConstants.CENTER);

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
            Dimension d = super.getPreferredSize();
            return new Dimension(Math.max(d.width + 28, minButtonWidth), buttonHeight);
        }

        @Override
        public Dimension getMinimumSize() {
            return new Dimension(Math.min(96, minButtonWidth), buttonHeight);
        }

        @Override
        public Dimension getMaximumSize() {
            return new Dimension(Integer.MAX_VALUE, buttonHeight);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            // Dark translucent backing so outlined buttons remain legible over hero backgrounds
            g2.setColor(new Color(10, 14, 23, hovered ? 165 : 110));
            RoundRectangle2D fillShape = new RoundRectangle2D.Double(1, 1, w - 2, h - 2, arc, arc);
            g2.fill(fillShape);

            int fillAlpha = pressed ? 58 : (hovered ? 38 : 16);
            g2.setColor(new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), fillAlpha));
            g2.fill(fillShape);

            g2.setStroke(new BasicStroke(strokeWidth));
            g2.setColor(hovered ? adjustBrightness(accentColor, 32) : accentColor);
            g2.draw(new RoundRectangle2D.Double(1, 1, w - 2, h - 2, arc, arc));

            g2.setFont(getFont());
            g2.setColor(hovered ? adjustBrightness(getForeground(), 18) : getForeground());
            FontMetrics fm = g2.getFontMetrics();
            String text = getText() != null ? getText() : "";
            int tx = (w - fm.stringWidth(text)) / 2;
            int ty = (h - fm.getHeight()) / 2 + fm.getAscent();
            g2.drawString(text, tx, ty);

            g2.dispose();
        }
    }

    /**
     * Dark transparent checkbox with a custom crisp vector rounded box and checkmark.
     */
    public static class DarkCheckBox extends JCheckBox {
        public DarkCheckBox(String text) {
            super(text);
            setOpaque(false);
            setForeground(TEXT_SLATE);
            setFont(FONT_HELPER);
            setFocusPainted(false);
            setBorder(new EmptyBorder(0, 0, 0, 0));
            setIconTextGap(8);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setIcon(new CheckBoxIcon(false, false));
            setSelectedIcon(new CheckBoxIcon(true, false));
            setRolloverIcon(new CheckBoxIcon(false, true));
            setRolloverSelectedIcon(new CheckBoxIcon(true, true));
        }
    }

    /**
     * Horizontal divider line (#26334D) with centered muted text (e.g. "or").
     */
    public static class DividerWithLabel extends JPanel {
        private final String text;

        public DividerWithLabel(String text) {
            this.text = text;
            setOpaque(false);
            setPreferredSize(new Dimension(240, 18));
            setMinimumSize(new Dimension(120, 18));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 18));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int cy = h / 2;

            g2.setFont(FONT_HELPER);
            FontMetrics fm = g2.getFontMetrics();
            int textW = fm.stringWidth(text);
            int gap = 12;
            int textX = (w - textW) / 2;
            int textY = (h - fm.getHeight()) / 2 + fm.getAscent();

            g2.setColor(DIVIDER_COLOR);
            g2.setStroke(new BasicStroke(1.0f));
            if (textX - gap > 0) {
                g2.drawLine(0, cy, textX - gap, cy);
            }
            if (textX + textW + gap < w) {
                g2.drawLine(textX + textW + gap, cy, w, cy);
            }

            g2.setColor(TEXT_PLACEHOLDER);
            g2.drawString(text, textX, textY);
            g2.dispose();
        }
    }

    private static Color adjustBrightness(Color c, int delta) {
        int r = Math.max(0, Math.min(255, c.getRed() + delta));
        int g = Math.max(0, Math.min(255, c.getGreen() + delta));
        int b = Math.max(0, Math.min(255, c.getBlue() + delta));
        return new Color(r, g, b, c.getAlpha());
    }

    // -------------------------------------------------------------------------
    // Vector Icons (User, Mail, Lock, CheckBox, Eye)
    // -------------------------------------------------------------------------

    public static final class UserIcon implements Icon {
        private final Color color;

        public UserIcon(Color color) {
            this.color = color;
        }

        @Override
        public int getIconWidth() {
            return 16;
        }

        @Override
        public int getIconHeight() {
            return 16;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(1.45f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            g2.draw(new Ellipse2D.Double(x + 5.0, y + 1.8, 6.0, 6.0));
            g2.draw(new Arc2D.Double(x + 2.0, y + 9.2, 12.0, 9.5, 0, 180, Arc2D.OPEN));
            g2.dispose();
        }
    }

    public static final class MailIcon implements Icon {
        private final Color color;

        public MailIcon(Color color) {
            this.color = color;
        }

        @Override
        public int getIconWidth() {
            return 16;
        }

        @Override
        public int getIconHeight() {
            return 16;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(1.4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            g2.draw(new RoundRectangle2D.Double(x + 1.5, y + 3.0, 13.0, 10.0, 2.5, 2.5));
            Path2D flap = new Path2D.Double();
            flap.moveTo(x + 2.2, y + 4.0);
            flap.lineTo(x + 8.0, y + 8.6);
            flap.lineTo(x + 13.8, y + 4.0);
            g2.draw(flap);
            g2.dispose();
        }
    }

    public static final class LockIcon implements Icon {
        private final Color color;

        public LockIcon(Color color) {
            this.color = color;
        }

        @Override
        public int getIconWidth() {
            return 16;
        }

        @Override
        public int getIconHeight() {
            return 16;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(1.45f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            g2.draw(new Arc2D.Double(x + 4.5, y + 1.5, 7.0, 7.5, 0, 180, Arc2D.OPEN));
            g2.draw(new Line2D.Double(x + 4.5, y + 5.2, x + 4.5, y + 7.2));
            g2.draw(new Line2D.Double(x + 11.5, y + 5.2, x + 11.5, y + 7.2));
            g2.draw(new RoundRectangle2D.Double(x + 2.5, y + 7.2, 11.0, 7.5, 3.0, 3.0));
            g2.fill(new Ellipse2D.Double(x + 7.1, y + 10.1, 1.8, 1.8));
            g2.dispose();
        }
    }

    private static final class CheckBoxIcon implements Icon {
        private final boolean selected;
        private final boolean hovered;

        CheckBoxIcon(boolean selected, boolean hovered) {
            this.selected = selected;
            this.hovered = hovered;
        }

        @Override
        public int getIconWidth() {
            return 15;
        }

        @Override
        public int getIconHeight() {
            return 15;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            RoundRectangle2D box = new RoundRectangle2D.Double(x + 0.75, y + 0.75, 13.5, 13.5, 4.5, 4.5);
            if (selected) {
                g2.setColor(hovered ? adjustBrightness(ELECTRIC_BLUE, 18) : ELECTRIC_BLUE);
                g2.fill(box);

                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(1.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                Path2D check = new Path2D.Double();
                check.moveTo(x + 3.8, y + 7.8);
                check.lineTo(x + 6.4, y + 10.4);
                check.lineTo(x + 11.3, y + 4.8);
                g2.draw(check);
            } else {
                g2.setColor(new Color(19, 28, 46, 200));
                g2.fill(box);
                g2.setStroke(new BasicStroke(1.25f));
                g2.setColor(hovered ? ELECTRIC_BLUE_LIGHT : new Color(0x475569));
                g2.draw(box);
            }
            g2.dispose();
        }
    }

    // -------------------------------------------------------------------------
    // Password Peek & Eye Toggle Internals
    // -------------------------------------------------------------------------

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
        protected void paintBackground(Graphics g) {
            if (getComponent().isOpaque()) {
                super.paintBackground(g);
            }
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
        private final int rightInset;

        TrailingButtonLayout(Component button, int rightInset) {
            this.button = button;
            this.rightInset = rightInset;
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
            button.setBounds(parent.getWidth() - btnWidth - rightInset, 2, btnWidth, h);
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
                g2.draw(new Line2D.Double(x + 2.5, y + 15.5, x + 15.5, y + 2.5));
            }
            g2.dispose();
        }
    }
}
