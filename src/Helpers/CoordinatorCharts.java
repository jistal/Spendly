package Helpers;
import DataReaders.ReadTransactions;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import java.time.Month;
import java.util.ArrayList;

public class CoordinatorCharts {

    AreaChart<String, Number> chart;

    private enum Period{
        DAILY, MONTHLY, YEARLY
    } Period period;

    // called by coordinator to set chart ref
    public void setChart(AreaChart<String, Number> chart) {
        this.chart = chart;
    }

    // ref to data reader
    ReadTransactions readTransactions = new ReadTransactions();

// period setters used by coordinator
    public void setPeriodDaily(){period = Period.DAILY;}
    public void setPeriodMonthly(){period = Period.MONTHLY;}
    public void setPeriodYearly(){period = Period.YEARLY;}


    public void getDataForChart(String fileName, String month, String year) {

        ArrayList<int[]> data = new ArrayList<>();
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        switch (period) {
            case DAILY -> {
                readTransactions.setPeriodDaily();
                data = readTransactions.data(fileName, month, year);
                series.setName("Currently viewing: " + numToMonth(month) + " - " + year);
            }
            case MONTHLY -> {
                readTransactions.setPeriodMonthly();
                data = readTransactions.data(fileName, null, year);
                series.setName("Currently viewing: " + year);
            }
            case YEARLY -> {
                readTransactions.setPeriodYearly();
                data = readTransactions.data(fileName, null, null);
                series.setName("Currently viewing all years stored");
            }
        }
        displayTheData(data, series);
    }


    private void displayTheData(ArrayList<int[]> data, XYChart.Series<String, Number> series){
        chart.getData().clear();
        for (int[] row : data) {
            String xAxis;

            // show months instead of numbers for monthly view
            if (period == Period.MONTHLY) {
                xAxis = numToMonth(String.valueOf(row[0]));
            } else {
                xAxis = String.valueOf(row[0]);
            }

            int amount = row[1];
            series.getData().add(new XYChart.Data<>(xAxis, amount));
        }

        chart.getData().add(series);
        chart.setLegendVisible(true);

    }


    private String numToMonth(String num){
        String monthName = Month.of(Integer.parseInt(num)).name();
        monthName = monthName.substring(0, 3);
        return monthName;
    }
}
