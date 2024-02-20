package P04_Java_DB_Apps_Introduction_Exrecise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class P04AddMinion {
    private static final String GET_TOWN_BY_NAME = "SELECT t.id FROM towns t WHERE t.name = ?";
    private static final String INSERT_INTO_TOWNS = "INSERT INTO towns(name) VALUES(?)";
    private static final String TOWN_ADDED_FORMAT = "Town %s was added to the database.%n";
    private static final String ID_LABEL = "id";
    private static final String GET_VILLAIN_BY_NAME = "SELECT id FROM villains WHERE name = ?";
    private static final String INSERT_INTO_VILLAINS = "INSERT INTO villains(name, evilness_factor) VALUES(?, ?)";
    private static final String VILLAIN_ADDED_FORMAT = "Villain %s was added to the database.%n";
    private static final String EVILNESS_FACTOR = "evil";
    private static final String INSERT_INTO_MINIONS = "INSERT INTO minions(name, age, town_id) VALUES (?,?,?)";
    private static final String GET_LAST_MINION_ID = "SELECT id FROM minions ORDER BY id DESC LIMIT 1";
    private static final String INSERT_INTO_MINION_VILLAINS = "INSERT INTO minions_villains(minion_id, villain_id) VALUES(?,?)";


    public static void main(String[] args) throws SQLException {
        Connection connection = Utils.getSQLConnection();
        Scanner scanner = new Scanner(System.in);

        String[] minionInfo = scanner.nextLine().split(" ");
        String minionName = minionInfo[1];
        int minionAge = Integer.parseInt(minionInfo[2]);
        String minionCity = minionInfo[3];

        String villainName = scanner.nextLine().split(" ")[1];

        int townId = getId(connection, List.of(minionCity), GET_TOWN_BY_NAME, INSERT_INTO_TOWNS, TOWN_ADDED_FORMAT);

        int villainId = getId(connection, List.of(villainName, EVILNESS_FACTOR), GET_VILLAIN_BY_NAME, INSERT_INTO_VILLAINS, VILLAIN_ADDED_FORMAT);

        PreparedStatement insertMinionStatement = connection.prepareStatement(INSERT_INTO_MINIONS);
        insertMinionStatement.setString(1, minionName);
        insertMinionStatement.setInt(2, minionAge);
        insertMinionStatement.setInt(3, townId);
        insertMinionStatement.executeUpdate();

        PreparedStatement selectLastMinionIdStatement = connection.prepareStatement(GET_LAST_MINION_ID);
        ResultSet insertedMinionSet = selectLastMinionIdStatement.executeQuery();
        insertedMinionSet.next();
        int lastMinionId = insertedMinionSet.getInt(ID_LABEL);

        PreparedStatement insertIntoMappingTableStmt = connection.prepareStatement(INSERT_INTO_MINION_VILLAINS);
        insertIntoMappingTableStmt.setInt(1, lastMinionId);
        insertIntoMappingTableStmt.setInt(2, villainId);
        insertIntoMappingTableStmt.executeUpdate();

        System.out.printf("Successfully added %s to be minion of %s", minionName, villainName);

        connection.close();
    }

    private static int getId(Connection connection, List<String> inputArguments, String selectQuery, String insertQuery, String printFormat) throws SQLException {
        final String name = inputArguments.get(0);

        PreparedStatement argumentStatement = connection.prepareStatement(selectQuery);
        argumentStatement.setString(1, name);
        ResultSet argumentResult = argumentStatement.executeQuery();

        if (!argumentResult.next()) {
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            for (int index = 1; index <= inputArguments.size(); index++) {
                insertStatement.setString(index, inputArguments.get(index - 1));
            }
            insertStatement.executeUpdate();

            final ResultSet newResultSet = argumentStatement.executeQuery();
            newResultSet.next();

            System.out.printf(printFormat, name);

            return newResultSet.getInt(ID_LABEL);
        }
        return argumentResult.getInt(ID_LABEL);
    }
}
