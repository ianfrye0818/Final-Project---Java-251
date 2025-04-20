
/**
 * 
 * Ian Frye
 * CSC 251 - Advanced Java Programming
 * Final Project - Coffee Ordering System
 * 04/20/2025
 * This application provides a
 * graphical user interface for managing a coffee shop's operations, including
 * user authentication (login and account creation), browsing and ordering coffee,
 * viewing order history, managing customer accounts (viewing details, updating,
 * and deleting), and administrative functionalities for managing the coffee menu
 * (creating, updating, and deleting coffee items) and viewing all orders. The
 * application utilizes a Model-View-Controller (MVC) architecture, with the
 * {@link controllers.AppController} serving as the central controller to manage
 * application state and flow between different views. Data persistence and
 * business logic are handled by service classes invoked by the controller. The
 * UI is built using Swing components with custom styling and layout management.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 * 
 */
import controllers.AppController;

public class Main {

    public static void main(String[] args) {
        AppController.getInstance().start();
    }

}