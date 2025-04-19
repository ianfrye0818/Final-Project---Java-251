package components;

import controllers.AppController;
import enums.ViewType;

import javax.swing.*;
import java.awt.*;

public class TableJPanel extends JPanel {
    public TableJPanel(AppController controller, JButton viewDetailsButton) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        setBackground(new Color(245, 245, 245));


        JButton backButton = new StyledInputs.StyledButton("Back to menu");
        backButton.addActionListener(e -> controller.setDisplay(ViewType.COFFEE_MENU_VIEW));

        add(backButton);
        add(viewDetailsButton);
    }
}

