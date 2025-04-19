import Interfaces.Container;
import controllers.AppController;

public class Main {

//    public static void main(String[] args) {
//        AppController.getInstance().start();
//    }

    public static void main(String[] args) {
        AppController app = AppController.getInstance();
        Container container = app.getContainer();


    }

}