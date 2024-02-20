package P04_Java_DB_Apps_Introduction_Exrecise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class P08IncreaseMinionsAge {
    private static final String UPDATE_NAMES_TO_LOWERCASE = "UPDATE minions AS m" +
            " SET m.name = LOWER(m.name)" +
            " WHERE m.id IN (%s)";
    private static final String UPDATE_AGE_OF_MINIONS_BY_ONE = "UPDATE minions AS m" +
            " SET m.age = m.age + 1" +
            " WHERE m.id IN (%s)";
    private static final String GET_MINION_NAMES_AND_AGES = "SELECT m.name, m.age FROM minions m";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Connection connection = Utils.getSQLConnection();

        List<Integer> minionIds = Arrays.stream(scanner.nextLine()
                .split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        String ids = minionIds.toString().replaceAll("\\[", "").replaceAll("]", "");
        String stmtForMinionNamesUpdate = String.format(UPDATE_NAMES_TO_LOWERCASE, ids);
        String stmtForMinionAgeIncrease = String.format(UPDATE_AGE_OF_MINIONS_BY_ONE, ids);

        PreparedStatement setNameToLower = connection.prepareStatement(stmtForMinionNamesUpdate);
        setNameToLower.executeUpdate();

        PreparedStatement increaseAgeStmt = connection.prepareStatement(stmtForMinionAgeIncrease);
        increaseAgeStmt.executeUpdate();

        PreparedStatement getMinionNamesAndAgeStmt = connection.prepareStatement(GET_MINION_NAMES_AND_AGES);
        ResultSet minionsNameAndAgeSet = getMinionNamesAndAgeStmt.executeQuery();

        while (minionsNameAndAgeSet.next()) {
            System.out.printf("%s %d%n", minionsNameAndAgeSet.getString(Constants.COLUMN_LABEL_NAME)
                    , minionsNameAndAgeSet.getInt("age"));
        }
    }
}
