package Helpers;
import Controllers.ButtonsController;

public class inputAmount {

    private ButtonsController buttonsController;
    public void setButtonsController(ButtonsController buttonsController) {
        this.buttonsController = buttonsController;
    }

    // input amount to add for spendings / earnings
    public void getInput(String fileName) {
        String amount;
        amount = buttonsController.getAmountInputField().getText();

        if (amount != null && !amount.isEmpty() && amount.matches("\\d*")) {     // check if input is an int

            writer write = new writer();
            write.writeToFile(Integer.parseInt(amount), fileName);
            buttonsController.getAmountInputField().clear();
        } else {
            notificationHandler.getInstance().callNotificationHandler("Invalid input, try again.");
            buttonsController.getAmountInputField().clear();
        }
    }

}
