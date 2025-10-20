package DataReaders;
import Helpers.NotificationHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ReadTransactions {

    private enum Period {
        DAILY, MONTHLY, YEARLY
    } Period period;

    // api used by coordinator charts
    public void setPeriodDaily() {
        period = Period.DAILY;
    }

    public void setPeriodMonthly() {
        period = Period.MONTHLY;
    }

    public void setPeriodYearly() {
        period = Period.YEARLY;
    }


    ArrayList<int[]> transactionData = new ArrayList<>();
    Map<String, Integer> map = new TreeMap<>();

    // reads data (x, y) in pairs for chart
    public ArrayList<int[]> data(String fileName, String selectedMonthByUser, String selectedYearByUser) {
        transactionData.clear();
        map.clear();
        String line;

        try {
            File file = new File(fileName);
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // read and process each line for selected period
            while ((line = reader.readLine()) != null) {
                switch (period) {
                    case DAILY -> daily(line, selectedMonthByUser, selectedYearByUser);
                    case MONTHLY -> monthly(line, selectedYearByUser);
                    case YEARLY -> yearly(line);
                }
            }

            // convert map entries to array list pairs
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                int[] pair = new int[2];
                pair[0] = Integer.parseInt(entry.getKey());
                pair[1] = entry.getValue();

                transactionData.add(pair);
            }
        } catch (IOException e) {
            NotificationHandler.getInstance().callNotificationHandler("Could not load data, try again.");
        }
        return transactionData;
    }


    // reads daily transactions (based on year & month picked by user)
    public void daily(String lineRead, String selectedMonthByUser, String selectedYearByUser) {

        String[] parts = lineRead.split("-");
        String yearRead = parts[0].trim();
        String monthRead = parts[1].trim();
        String dateRead = parts[2].trim();
        String amountRead = parts[3].trim();

        // check if we are reading the same year / month user selected
        if (selectedYearByUser.equals(yearRead) && selectedMonthByUser.equals(monthRead)) {
            // if a dupe is found, sum up the amount
            int amount = Integer.parseInt(amountRead);
            map.put(dateRead, map.getOrDefault(dateRead, 0) + amount);
        }

    }

    // reads transactions monthly (based on year picked by user)
    public void monthly(String lineRead, String selectedYearByUser) {

        String[] parts = lineRead.split("-");
        String yearRead = parts[0].trim();
        String monthRead = parts[1].trim();
        String amountRead = parts[3].trim();

        // check if we are still reading data from same year the user selected
        if (yearRead.equals(selectedYearByUser)) {
            int amount = Integer.parseInt(amountRead);
            // if a dupe is found, sum up the amount
            map.put(monthRead, map.getOrDefault(monthRead, 0) + amount);
        }
    }

    // reads transactions daily
    public void yearly(String lineRead) {

        String[] parts = lineRead.split("-");
        String yearRead = parts[0].trim();
        String amountRead = parts[3].trim();

        // sum up dupes
        int amount = Integer.parseInt(amountRead);
        map.put(yearRead, map.getOrDefault(yearRead, 0) + amount);
    }
}





