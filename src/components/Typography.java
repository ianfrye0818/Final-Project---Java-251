package components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * A utility class containing definitions for styled typography components
 * (JLabels).
 * These classes extend {@code JLabel} and apply consistent styling for titles,
 * subtitles, and regular labels.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class Typography {

  /**
   * A styled label intended for titles, featuring a bold "Segoe UI" font,
   * a brown foreground color, and centered text alignment.
   */
  public static class StyledTitleField extends JLabel {

    /**
     * Constructs a new {@code StyledTitleField} with the specified text.
     * It sets the font to "Segoe UI", bold style, size 24, the foreground color
     * to a brown shade, and the horizontal alignment to center.
     *
     * @param text The text to be displayed in the title label.
     */
    public StyledTitleField(String text) {
      super(text);
      setFont(new Font("Segoe UI", Font.BOLD, 24));
      setForeground(new Color(79, 55, 48));
      setHorizontalAlignment(SwingConstants.CENTER);
    }

  }

  /**
   * A styled label intended for subtitles, featuring an italic "Segoe UI" font
   * and a gray foreground color.
   */
  public static class StyledSubtitleField extends JLabel {

    /**
     * Constructs a new {@code StyledSubtitleField} with the specified text.
     * It sets the font to "Segoe UI", italic style, size 14, and the foreground
     * color to gray.
     *
     * @param text The text to be displayed in the subtitle label.
     */
    public StyledSubtitleField(String text) {
      super(text);
      setFont(new Font("Segoe UI", Font.ITALIC, 14));
      setForeground(Color.GRAY);
    }
  }

  /**
   * A styled label for regular text, featuring a plain "Segoe UI" font and
   * a black foreground color.
   */
  public static class StyledLabel extends JLabel {
    /**
     * Constructs a new {@code StyledLabel} with the specified text.
     * It sets the font to "Segoe UI", plain style, size 16, and the foreground
     * color to black.
     *
     * @param text The text to be displayed in the label.
     */
    public StyledLabel(String text) {
      super(text);
      setFont(new Font("Segoe UI", Font.PLAIN, 16));
      setForeground(Color.BLACK);
    }
  }
}