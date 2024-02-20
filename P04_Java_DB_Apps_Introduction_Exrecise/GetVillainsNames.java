package P04_Java_DB_Apps_Introduction_Exrecise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetVillainsNames {
    private static final String GET_MINIONS_COUNT_OF_VILLAINS =
        "SELECT v.name, " +
            "COUNT(DISTINCT mv.minion_id) AS 'minion_count' " +
            "FROM villains AS v " +
            "JOIN minions_villains AS mv ON v.id = mv.villain_id " +
            "GROUP BY v.name " +
            "HAVING minion_count > ? " +
            "ORDER BY `minion_count` DESC";

    public static void main(String[] args) throws SQLException {
        Connection connection = Utils.getSQLConnection();

        PreparedStatement statement = connection.prepareStatement(GET_MINIONS_COUNT_OF_VILLAINS);
        statement.setInt(1, 15);
        final ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String villainName = resultSet.getString("name");
            int minionsCount = resultSet.getInt("minion_count");
            System.out.println(villainName + " " + minionsCount);
        }

        connection.close();
    }
}
