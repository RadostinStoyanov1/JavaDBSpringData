package P04_Java_DB_Apps_Introduction_Exrecise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GetMinionNames {
    private static final String GET_MINIONS_NAME_FOR_VILLAIN_ID =
            "SELECT m.name AS 'minion_name'," +
                    " m.age AS 'minion_age'," +
                    " v.name AS 'villain_name'" +
                    " FROM minions AS m" +
                    " JOIN minions_villains AS mv ON m.id = mv.minion_id" +
                    " JOIN villains AS v ON mv.villain_id = v.id" +
                    " WHERE mv.villain_id = ?";

    private static final String GET_VILLAIN_NAME_BY_ID = "SELECT v.name AS 'villain_name' FROM villains AS v\n" +
            "WHERE v.id = ?";

    private static final String VILLAIN_NAME_LABEL = "villain_name";
    private static final String MINION_NAME_LABEL = "minion_name";
    private static final String MINION_AGE_LABEL = "minion_age";
    private static final String NOT_FOUND_VILLAIN = "No villain with ID %d exists in the database.";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Connection connection = Utils.getSQLConnection();

        System.out.println("Please enter villain's id number: ");
        int villainId = scanner.nextInt();

        PreparedStatement villainStmt = connection.prepareStatement(GET_VILLAIN_NAME_BY_ID);
        villainStmt.setInt(1, villainId);
        ResultSet villainResult = villainStmt.executeQuery();

        if (!villainResult.next()) {
            System.out.printf(NOT_FOUND_VILLAIN, villainId);
            connection.close();
            return;
        }

        String villainName = villainResult.getString(VILLAIN_NAME_LABEL);
        System.out.printf("Villain: %s%n", villainName);

        PreparedStatement stmt = connection.prepareStatement(GET_MINIONS_NAME_FOR_VILLAIN_ID);
        stmt.setInt(1, villainId);
        ResultSet resultMinions = stmt.executeQuery();

        int counter = 1;

        while (resultMinions.next()) {
            String minionName = resultMinions.getString(MINION_NAME_LABEL);
            int minionAge = resultMinions.getInt(MINION_AGE_LABEL);
            System.out.printf("%d. %s %d%n", counter, minionName, minionAge);
            counter++;
        }
        connection.close();
    }
}
