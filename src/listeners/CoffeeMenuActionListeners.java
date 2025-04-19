package listeners;

import java.awt.event.ActionListener;

import components.tables.CoffeeTable;
import controllers.AppController;
import entites.Coffee;
import enums.ViewType;
import utils.DialogUtils;
import views.SuperView;

public class CoffeeMenuActionListeners {

  private final AppController controller;

  public CoffeeMenuActionListeners(AppController controller) {
    this.controller = controller;
  }

  public ActionListener getCoffeeTableSelectedItemListener(CoffeeTable coffeeTable, SuperView parent) {
    return e -> {
      {
        Coffee selectedCoffee = coffeeTable.getSelectedItem();
        if (selectedCoffee != null) {
          coffeeTable.handleViewDetails();
        } else {
          DialogUtils.showError(parent, "Please select a coffee to update");
        }
      }
      ;
    };
  }

  public ActionListener getCreateOrderButtonActionListener() {
    return e -> controller.setDisplay(ViewType.CREATE_ORDER_VIEW);
  }
}
