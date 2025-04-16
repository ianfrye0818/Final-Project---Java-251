package views;

import java.awt.GridBagConstraints;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import components.Typography;

public class SuperView extends JFrame {
  public SuperView() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // setPreferredSize(new Dimension(500, 500));
  }

  @Override
  public String getTitle() {
    return "Java Cafe";
  }

  protected void addFormField(JPanel panel, GridBagConstraints gbc,
      String labelText, JComponent field,
      int gridY, int gridX,
      double weightX, int gridWidth) {

    // Label setup
    JLabel label = new Typography.StyledLabel(labelText);
    gbc.gridy = gridY;
    gbc.gridx = gridX;
    gbc.weightx = weightX;
    gbc.gridwidth = gridWidth;
    gbc.anchor = GridBagConstraints.WEST;
    panel.add(label, gbc);

    // Field setup
    gbc.gridy = gridY + 1;
    panel.add(field, gbc);
  }

}
