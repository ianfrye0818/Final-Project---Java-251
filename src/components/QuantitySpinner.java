package components;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;

public class QuantitySpinner extends StyledInputs.StyledSpinner {
  public QuantitySpinner() {
    JComponent editor = getEditor();
    if (editor instanceof JSpinner.DefaultEditor) {
      JFormattedTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
      tf.setBorder(BorderFactory.createCompoundBorder(
          tf.getBorder(),
          BorderFactory.createEmptyBorder(8, 5, 8, 5)));
    }
  }

}
