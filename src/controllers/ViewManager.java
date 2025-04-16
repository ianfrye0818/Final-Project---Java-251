package controllers;

import java.awt.Window;
import enums.ViewType;
import views.*;

public class ViewManager {
  private final AppController appController;

  public ViewManager(AppController appController) {
    this.appController = appController;
  }

  public void setDisplay(ViewType view) {
    Window[] windows = Window.getWindows();
    for (Window window : windows) {
      window.dispose();
    }

    SuperView viewToDisplay = createView(view);
    if (viewToDisplay != null) {
      viewToDisplay.pack();
      viewToDisplay.setLocationRelativeTo(null);
      viewToDisplay.setVisible(true);
    }
  }

  private SuperView createView(ViewType viewType) {
    switch (viewType) {
      case COFFEE_MENU_VIEW:
        return new CoffeeMenuView(appController);
      case CREATE_ACCOUNT_VIEW:
        return new CreateAccountView(appController);
      case CREATE_COFFEE_VIEW:
        return new CreateCoffeeView(appController);
      case CREATE_ORDER_VIEW:
        return new CreateOrderView(appController);
      case ORDER_HISTORY_VIEW:
        return new CustomerOrderHistoryView(appController);
      case LOGIN_VIEW:
        return new LoginView(appController);
      case ORDER_SUMMARY_VIEW:
        return new OrderSummaryView(appController);
      case UPDATE_ACCOUNT_VIEW:
        return new UpdateAccountView(appController);
      case UPDATE_COFFEE_VIEW:
        return new UpdateCoffeeView(appController);
      default:
        return null;
    }
  }
}