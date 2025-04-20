package components;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;

/**
 * A custom spinner component for selecting quantities.
 * Extends {@link StyledInputs.StyledSpinner} and applies custom
 * padding to the text field within the spinner for better visual appearance.
 */
public class QuantitySpinner extends StyledInputs.StyledSpinner {
  /**
   * Constructs a new {@code QuantitySpinner}.
   * It accesses the editor component of the spinner, which is expected
   * to be a {@code JSpinner.DefaultEditor}, and adds an empty border
   * to the text field within the editor to provide internal padding.
   * 
   * @author Ian Frye
   * @version 1.0
   * @since 2025-04-20
   */
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