// package components;

// import javax.swing.*;
// import java.awt.*;
// import java.util.Objects;

// public class StyledButton extends JButton {

// public StyledButton(String text, Color backgroundColor, Color
// foregroundColor, int fontSize) {
// setText(text);
// setFont(new Font("Segoe UI", Font.BOLD, fontSize));
// setFocusPainted(false);
// setBorderPainted(false);
// setOpaque(true);

// setBackground(Objects.requireNonNullElseGet(backgroundColor, () -> new
// Color(220, 220, 220)));
// if (foregroundColor != null) {
// setForeground(foregroundColor);
// } else {
// setForeground(Color.BLACK);
// }
// getModel().addChangeListener(e -> {
// ButtonModel model = (ButtonModel) e.getSource();
// if (model.isRollover()) {
// setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
// if (backgroundColor != null) {
// setBackground(backgroundColor.darker());
// } else {
// setBackground(new Color(200, 200, 200));
// }
// } else {
// setCursor(Cursor.getDefaultCursor());
// setBackground(backgroundColor != null ? backgroundColor : new Color(220, 220,
// 220));
// }
// if (model.isPressed()) {
// if (backgroundColor != null) {
// setBackground(backgroundColor.darker().darker());
// } else {
// setBackground(new Color(180, 180, 180));
// }
// }
// });
// }

// public StyledButton(String text) {
// this(text, null, null, 16);
// }

// public StyledButton(String text, int fontSize) {
// this(text, null, null, fontSize);
// }

// public StyledButton(String text, Color backgroundColor, Color
// foregroundColor) {
// this(text, backgroundColor, foregroundColor, 16);
// }

// }
