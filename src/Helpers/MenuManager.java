package Helpers;
import Controllers.ButtonsController;
import Controllers.MainController;

public class MenuManager {

    //ref to main and buttons con for ui updates
    private ButtonsController buttonsController;
    private MainController mainController;

    // give menu manager access to buttons con methods
    public void setButtonsController(ButtonsController buttonsController) {
        this.buttonsController = buttonsController;
    }
    // give menu manager access to main con methods
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void buttonVisibility(ButtonsController.Menu currentMenu) {
        buttonsController.hideAltButtons();
        buttonsController.hideMainButtons();
        buttonsController.hideAmountInputVBOX();
        buttonsController.hideViewButtons();
        buttonsController.hideDailyScrollVBOX();
        buttonsController.hideViewSpendOrEarnVBOX();
        buttonsController.hideBackButtonHBOX();
        switch (currentMenu) {
            case MAIN ->{
                buttonsController.showMainButtons();

                //mainController.hideTopLabelsHBOX();
                //mainController.hideEarningsChart();
                //mainController.hideSpendingsChart();
            }
            case addSpendingsOrEarnings -> {
                buttonsController.showAltButtons();
                buttonsController.showBackButtonHBOX();

            }
            case inputAmount -> {
                buttonsController.showAmountInputVBOX();
                buttonsController.showBackButtonHBOX();

                //mainController.hideTopLabelsHBOX();
                //mainController.hideEarningsChart();
                //mainController.hideSpendingsChart();
            }
            case viewSpendingsOrEarnings -> {
                buttonsController.showViewSpendOrEarnVBOX();
                buttonsController.showBackButtonHBOX();

                //mainController.hideTopLabelsHBOX();
                //mainController.hideEarningsChart();
                //mainController.hideSpendingsChart();

            }
            case viewPeriods -> {
               buttonsController.showViewButtons();
                buttonsController.showBackButtonHBOX();

            }
            case scrollingMenu -> {
                buttonsController.showDailyScrollVBOX();
                buttonsController.showBackButtonHBOX();
            }
        }
    }


    public void chartVisibility(MainController.chartMenu currentMenu) {

        switch (currentMenu) {
            case EARNINGSCHART ->{
                mainController.showEarningsChart();
                mainController.showTopLabelsHBOX();

                mainController.chart = mainController.getEarningsChart();
                mainController.accessTopLabel().setStyle("-fx-text-fill: #A6FFB0; ");  // light green text
                mainController.accessTopLabel().setText("Earnings");
                mainController.hideSpendingsChart();
            }

            case SPENDINGSCHART ->{
                mainController.showSpendingsChart();
                mainController.showTopLabelsHBOX();

                mainController.chart = mainController.getSpendingsChart();
                mainController.accessTopLabel().setStyle("-fx-text-fill: #FF7F7F"); // coral red text
                mainController.accessTopLabel().setText("Spendings");
                mainController.hideEarningsChart();
            }
        }
    }



}
