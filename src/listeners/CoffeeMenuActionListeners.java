package listeners;

import java.awt.event.ActionListener;

import components.tables.CoffeeTable;
import controllers.AppController;
import entites.Coffee;
import enums.ViewType;
import utils.DialogUtils;
import views.SuperView;

/**
 * Provides {@code ActionListener} implementations for actions within the coffee
 * menu
 * view, such as handling the selection of a coffee in the table and creating a
 * new order.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */

public class CoffeeMenuActionListeners {

  private final AppController controller;

  /**
   * Constructs a new {@code CoffeeMenuActionListeners} with the specified
   * application controller.
   *
   * @param controller The application controller providing access to services and
   *                   navigation.
   */
  public CoffeeMenuActionListeners(AppController controller) {
    this.controller = controller;
  }

  /**
   * Returns an {@code ActionListener} that is triggered when an item is selected
   * in the
   * {@link CoffeeTable}. If a coffee is selected, it calls the table's
   * {@code handleViewDetails()} method (assuming this method exists in
   * {@code CoffeeTable}).
   * If no coffee is selected, it displays an error message prompting the user to
   * select a coffee.
   *
   * @param coffeeTable The {@code CoffeeTable} from which the selection event
   *                    originates.
   * @param parent      The parent {@code SuperView} used for displaying error
   *                    dialogs.
   * @return An {@code ActionListener} for handling coffee table selections.
   */
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

  /**
   * Returns an {@code ActionListener} that navigates the application to the view
   * for creating a new order. This listener is typically attached to a "Create
   * Order"
   * button in the coffee menu.
   *
   * @return An {@code ActionListener} for navigating to the create order view.
   */
  public ActionListener getCreateOrderButtonActionListener() {
    return e -> controller.setDisplay(ViewType.CREATE_ORDER_VIEW);
  }
}