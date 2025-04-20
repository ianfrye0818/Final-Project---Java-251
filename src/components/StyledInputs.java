package components;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * A utility class containing definitions for styled Swing input components.
 * These classes extend standard Swing components and apply consistent
 * styling such as font, border, and colors for a uniform look and feel.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class StyledInputs {

    /**
     * A styled password field with a specific font and internal padding.
     */
    public static class StyledPasswordField extends JPasswordField {
        /**
         * Constructs a new {@code StyledPasswordField} with the specified number of
         * columns.
         * It sets the font to "Segoe UI", plain style, size 16, and adds internal
         * padding
         * to the border.
         *
         * @param columns The number of columns for the password field.
         */
        public StyledPasswordField(int columns) {
            super(columns);
            setFont(new Font("Segoe UI", Font.PLAIN, 16));
            setBorder(BorderFactory.createCompoundBorder(
                    getBorder(),
                    BorderFactory.createEmptyBorder(8, 5, 8, 5)));
        }
    }

    /**
     * A styled text field with a specific font, internal padding, and optional
     * disabling.
     */
    public static class StyledTextField extends JTextField {

        /**
         * Constructs a new {@code StyledTextField} with optional disabling and no
         * initial text.
         *
         * @param isDisabled If {@code true}, the text field is not editable; otherwise,
         *                   it is editable.
         */
        public StyledTextField(boolean isDisabled) {
            this(isDisabled, "");
        }

        /**
         * Constructs a new {@code StyledTextField} with optional disabling and initial
         * text.
         * It sets the font to "Segoe UI", plain style, size 16, adds internal padding
         * to the border, sets the initial text, and configures its editability.
         *
         * @param isDisabled If {@code true}, the text field is not editable; otherwise,
         *                   it is editable.
         * @param text       The initial text to be displayed in the text field.
         */
        public StyledTextField(boolean isDisabled, String text) {
            setFont(new Font("Segoe UI", Font.PLAIN, 16));
            setBorder(BorderFactory.createCompoundBorder(
                    getBorder(),
                    BorderFactory.createEmptyBorder(8, 5, 8, 5)));
            setText(text);
            setEditable(!isDisabled);
        }

        /**
         * Constructs a new, editable {@code StyledTextField} with no initial text.
         */
        public StyledTextField() {
            this(false);
        }

    }

    /**
     * A styled text area with a specific font, border, internal padding, optional
     * disabling,
     * line wrapping, and a default height.
     */
    public static class StyledTextArea extends JTextArea {
        /**
         * Constructs a new {@code StyledTextArea} with the specified number of columns
         * and optional disabling,
         * with no initial text.
         *
         * @param columns    The number of columns for the text area.
         * @param isDisabled If {@code true}, the text area is not editable; otherwise,
         *                   it is editable.
         */
        public StyledTextArea(int columns, boolean isDisabled) {
            this(columns, isDisabled, "");
        }

        /**
         * Constructs a new {@code StyledTextArea} with the specified number of columns,
         * optional disabling,
         * and initial text. It sets the font to "Segoe UI", plain style, size 16, a
         * line border, internal padding,
         * the initial text, editability, line wrapping, word wrapping, a default of 4
         * rows, and a preferred size.
         *
         * @param columns    The number of columns for the text area.
         * @param isDisabled If {@code true}, the text area is not editable; otherwise,
         *                   it is editable.
         * @param text       The initial text to be displayed in the text area.
         */
        public StyledTextArea(int columns, boolean isDisabled, String text) {
            setFont(new Font("Segoe UI", Font.PLAIN, 16));
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(122, 122, 122)),
                    BorderFactory.createEmptyBorder(8, 5, 8, 5)));
            setText(text);
            setEditable(!isDisabled);
            setLineWrap(true);
            setWrapStyleWord(true);
            setRows(4); // Set a default height of 4 rows
            setPreferredSize(new Dimension(columns * 10, 100)); // Give it a reasonable default size
        }

        /**
         * Constructs a new, editable {@code StyledTextArea} with the specified number
         * of columns and no initial text.
         *
         * @param columns The number of columns for the text area.
         */
        public StyledTextArea(int columns) {
            this(columns, false);
        }

    }

    /**
     * A styled button with customizable text, background color, foreground color,
     * and font size.
     * It also provides visual feedback on rollover and press states.
     */
    public static class StyledButton extends JButton {

        /**
         * Constructs a new {@code StyledButton} with the specified text, background
         * color,
         * foreground color, and font size. It applies custom styling for font, focus,
         * border,
         * opacity, and hover/press effects.
         *
         * @param text            The text to be displayed on the button.
         * @param backgroundColor The background color of the button. If {@code null}, a
         *                        default light gray is used.
         * @param foregroundColor The foreground color of the button. If {@code null},
         *                        black is used.
         * @param fontSize        The font size for the button text.
         */
        public StyledButton(String text, Color backgroundColor, Color foregroundColor, int fontSize) {
            setText(text);
            setFont(new Font("Segoe UI", Font.BOLD, fontSize));
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(true);

            setBackground(Objects.requireNonNullElseGet(backgroundColor, () -> new Color(220, 220, 220)));
            if (foregroundColor != null) {
                setForeground(foregroundColor);
            } else {
                setForeground(Color.BLACK);
            }
            getModel().addChangeListener(e -> {
                ButtonModel model = (ButtonModel) e.getSource();
                if (model.isRollover()) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    if (backgroundColor != null) {
                        setBackground(backgroundColor.darker());
                    } else {
                        setBackground(new Color(200, 200, 200));
                    }
                } else {
                    setCursor(Cursor.getDefaultCursor());
                    setBackground(backgroundColor != null ? backgroundColor : new Color(220, 220, 220));
                }
                if (model.isPressed()) {
                    if (backgroundColor != null) {
                        setBackground(backgroundColor.darker().darker());
                    } else {
                        setBackground(new Color(180, 180, 180));
                    }
                }
            });
        }

        /**
         * Constructs a new {@code StyledButton} with the specified text and default
         * styling (light gray background, black foreground, size 16 font).
         *
         * @param text The text to be displayed on the button.
         */
        public StyledButton(String text) {
            this(text, null, null, 16);
        }

        /**
         * Constructs a new {@code StyledButton} with the specified text and font size,
         * using default background and foreground colors.
         *
         * @param text     The text to be displayed on the button.
         * @param fontSize The font size for the button text.
         */
        public StyledButton(String text, int fontSize) {
            this(text, null, null, fontSize);
        }

        /**
         * Constructs a new {@code StyledButton} with the specified text, background
         * color, and foreground color, using a default font size of 16.
         *
         * @param text            The text to be displayed on the button.
         * @param backgroundColor The background color of the button.
         * @param foregroundColor The foreground color of the button.
         */
        public StyledButton(String text, Color backgroundColor, Color foregroundColor) {
            this(text, backgroundColor, foregroundColor, 16);
        }

    }

    /**
     * A styled combo box with a specific font, white background, internal padding,
     * a fixed size, and a hand cursor on hover.
     *
     * @param <T> The type of the items in the combo box.
     */
    public static class StyledComboBox<T> extends JComboBox<T> {
        /**
         * Constructs a new {@code StyledComboBox} populated with the given items.
         * It sets the font to "Segoe UI", plain style, size 16, a white background,
         * internal padding,
         * a default size of 200x40 pixels, and a hand cursor.
         *
         * @param items An array of items to populate the combo box.
         */
        public StyledComboBox(T[] items) {
            super(items);
            setFont(new Font("Segoe UI", Font.PLAIN, 16));
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createCompoundBorder(
                    getBorder(),
                    BorderFactory.createEmptyBorder(8, 5, 8, 5)));
            setSize(200, 40);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }

    /**
     * A styled spinner with a specific font and internal padding.
     */
    public static class StyledSpinner extends JSpinner {
        /**
         * Constructs a new {@code StyledSpinner} with a "Segoe UI" plain font of size
         * 16 and internal padding.
         */
        public StyledSpinner() {
            super();
            setFont(new Font("Segoe UI", Font.PLAIN, 16));
            setBorder(BorderFactory.createCompoundBorder(
                    getBorder(),
                    BorderFactory.createEmptyBorder(8, 5, 8, 5)));
        }
    }

    /**
     * A primary style button with a specific background color (brown) and white
     * text.
     * Extends {@link StyledButton}.
     */
    public static class PrimaryButton extends StyledButton {
        /**
         * Constructs a new {@code PrimaryButton} with the given text and default
         * primary styling
         * (brown background, white foreground, size 16 font).
         *
         * @param text The text to be displayed on the button.
         */
        public PrimaryButton(String text) {
            super(text, new Color(109, 81, 70), Color.WHITE);
        }

        /**
         * Constructs a new {@code PrimaryButton} with the given text and font size,
         * using the primary styling.
         *
         * @param text     The text to be displayed on the button.
         * @param fontSize The font size for the button text.
         */
        public PrimaryButton(String text, int fontSize) {
            super(text, new Color(109, 81, 70), Color.WHITE, fontSize);
        }

    }

    /**
     * A destructive style button with a specific background color (red) and white
     * text.
     * Extends {@link StyledButton}.
     */
    public static class DestructiveButton extends StyledButton {
        /**
         * Constructs a new {@code DestructiveButton} with the given text and default
         * destructive styling
         * (red background, white foreground, size 16 font).
         *
         * @param text The text to be displayed on the button.
         */
        public DestructiveButton(String text) {
            super(text, new Color(239, 68, 68), Color.WHITE);
        }

        /**
         * Constructs a new {@code DestructiveButton} with the given text and font size,
         * using the destructive styling.
         *
         * @param text     The text to be displayed on the button.
         * @param fontSize The font size for the button text.
         */
        public DestructiveButton(String text, int fontSize) {
            super(text, new Color(239, 68, 68), Color.WHITE, fontSize);
        }
    }

}