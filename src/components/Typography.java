package components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Typography {

  public static class StyledTitleField extends JLabel {

    public StyledTitleField(String text) {
      super(text);
      setFont(new Font("Segoe UI", Font.BOLD, 24));
      setForeground(new Color(79, 55, 48));
      setHorizontalAlignment(SwingConstants.CENTER);
    }

  }

  public static class StyledSubtitleField extends JLabel {

    public StyledSubtitleField(String text) {
      super(text);
      setFont(new Font("Segoe UI", Font.ITALIC, 14));
      setForeground(Color.GRAY);
    }
  }

  public static class StyledLabel extends JLabel {
    public StyledLabel(String text) {
      super(text);
      setFont(new Font("Segoe UI", Font.PLAIN, 16));
      setForeground(Color.BLACK);
    }
  }
}
