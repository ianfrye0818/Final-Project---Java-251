package listeners;

import java.awt.event.ActionListener;

import controllers.AppController;
import enums.ViewType;

public class CoffeeMenuListeners {
  private final AppController appController;

  public CoffeeMenuListeners(AppController appController) {
    this.appController = appController;
  }

  public ActionListener getLogoutButtonListener() {
    return e -> appController.getAuthService().logout();
  }

  public ActionListener getCreateOrderButtonListener() {
    return e -> appController.setDisplay(ViewType.CREATE_ORDER_VIEW);
  }

  public ActionListener getAddNewCoffeeButtonListener() {
    return e -> appController.setDisplay(ViewType.CREATE_COFFEE_VIEW);
  }
}
