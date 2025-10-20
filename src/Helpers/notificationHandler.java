package Helpers;
import Controllers.MainController;
import javafx.scene.paint.Color;

    public class notificationHandler {

        // single global instance
        private static final notificationHandler INSTANCE = new notificationHandler();

        private MainController mainController;
        public void setMainController(MainController main) {
            this.mainController = main;
        }

        // private constructor
        private notificationHandler() {
        }
        // access point for everyone
        public static notificationHandler getInstance() {
            return INSTANCE;
        }

        public void callNotificationHandler(String message) {
            if (mainController == null) {
                System.err.println("notificationHandler - MainController not set!");
                return;
            }
            mainController.accessTopLabel().setText(message);
            mainController.accessTopLabel().setTextFill(Color.web("#FF0000"));
            mainController.showTopLabelsContainer();
        }
    }
