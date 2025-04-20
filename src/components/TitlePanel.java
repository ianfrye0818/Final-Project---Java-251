package components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * A custom JPanel designed to display a title prominently at the top of a view.
 * It uses a {@code FlowLayout} to center the title and applies specific styling
 * for the background color, font, text color, and padding.
 */
public class TitlePanel extends JPanel {

  /**
   * Constructs a new {@code TitlePanel} with the specified title text.
   * It initializes the layout to {@code FlowLayout.CENTER}, sets the background
   * color to a light gray, creates a {@code JLabel} with the provided title,
   * applies a bold "Segoe UI" font of size 28 with a brown color, and adds
   * top and bottom padding to the panel.
   * 
   * @author Ian Frye
   * @version 1.0
   * @since 2025-04-20
   *
   * @param title The string to be displayed as the title.
   */
  public TitlePanel(String title) {
    this.setLayout(new FlowLayout(FlowLayout.CENTER));
    setBackground(new Color(245, 245, 245));
    JLabel titleLabel = new JLabel(title);
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
    titleLabel.setForeground(new Color(79, 55, 48));
    setBorder(new EmptyBorder(20, 0, 20, 0)); // Padding
    add(titleLabel);

  }
}