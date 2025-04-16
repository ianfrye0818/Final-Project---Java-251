package components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TitlePanel extends JPanel {

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
