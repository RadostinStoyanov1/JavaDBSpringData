package P04_Java_DB_Apps_Introduction_Exrecise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class P05ChangeTownNamesCasing {

    private static final String CHANGE_TOWN_NAMES_OF_COUNTRY = "UPDATE towns t SET t.name = UPPER(t.name) WHERE t.country = ?";
    private static final String GET_ALL_TOWN_NAMES_IN_COUNTRY = "SELECT t.name FROM towns t WHERE t.country = ?";
    private static final String NO_TOWNS_WERE_AFFECTED_FORMAT = "No town names were affected.";
    private static final String COUNT_OF_AFFECTED_TOWNS_FORMAT = "%d town names were affected.%n";

    private static final String TOWN_NAME_LABEL = "name";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        final Connection connection = Utils.getSQLConnection();

        String countryName = scanner.nextLine();

        PreparedStatement updateNamesStmt = connection.prepareStatement(CHANGE_TOWN_NAMES_OF_COUNTRY);
        updateNamesStmt.setString(1, countryName);
        final int updatesTownsCount = updateNamesStmt.executeUpdate();

        if (updatesTownsCount == 0) {
            System.out.println(NO_TOWNS_WERE_AFFECTED_FORMAT);
            connection.close();
            return;
        } else {
            System.out.printf(COUNT_OF_AFFECTED_TOWNS_FORMAT, updatesTownsCount);
        }

        PreparedStatement getTownsStmt = connection.prepareStatement(GET_ALL_TOWN_NAMES_IN_COUNTRY);
        getTownsStmt.setString(1, countryName);
        ResultSet changedTownsSet = getTownsStmt.executeQuery();

        ArrayList<String> towns = new ArrayList<>();

        while (changedTownsSet.next()) {
            towns.add(changedTownsSet.getString(TOWN_NAME_LABEL));
        }

        System.out.println(towns);
    }
}
