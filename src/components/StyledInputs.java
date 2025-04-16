package components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class StyledInputs {

  public static class StyledPasswordField extends JPasswordField {
    public StyledPasswordField(int columns) {
      super(columns);
      setFont(new Font("Segoe UI", Font.PLAIN, 16));
      setBorder(BorderFactory.createCompoundBorder(
          getBorder(),
          BorderFactory.createEmptyBorder(8, 5, 8, 5)));
    }
  }

  public static class StyledTextField extends JTextField {
    public StyledTextField(int columns, boolean isDisabled) {
      // setFont(new Font("Segoe UI", Font.PLAIN, 16));
      // setBorder(BorderFactory.createCompoundBorder(
      // getBorder(),
      // BorderFactory.createEmptyBorder(8, 5, 8, 5)));
      this(columns, isDisabled, "");
    }

    public StyledTextField(int columns, boolean isDisabled, String text) {
      setFont(new Font("Segoe UI", Font.PLAIN, 16));
      setBorder(BorderFactory.createCompoundBorder(
          getBorder(),
          BorderFactory.createEmptyBorder(8, 5, 8, 5)));
      setText(text);
      setEditable(!isDisabled);
    }

    public StyledTextField(int columns) {
      this(columns, false);
    }

    public StyledTextField() {
      this(20, false);
    }
  }

  public static class StyledTextArea extends JTextArea {
    public StyledTextArea(int columns, boolean isDisabled) {
      this(columns, isDisabled, "");
    }

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

    public StyledTextArea(int columns) {
      this(columns, false);
    }

    public StyledTextArea() {
      this(20, false);
    }
  }

  public static class StyledButton extends JButton {

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

    public StyledButton(String text) {
      this(text, null, null, 16);
    }

    public StyledButton(String text, int fontSize) {
      this(text, null, null, fontSize);
    }

    public StyledButton(String text, Color backgroundColor, Color foregroundColor) {
      this(text, backgroundColor, foregroundColor, 16);
    }

  }

  public static class StyledComboBox<T> extends JComboBox<T> {
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

  public static class StyledSpinner extends JSpinner {
    public StyledSpinner() {
      super();
      setFont(new Font("Segoe UI", Font.PLAIN, 16));
      setBorder(BorderFactory.createCompoundBorder(
          getBorder(),
          BorderFactory.createEmptyBorder(8, 5, 8, 5)));
    }
  }

}
