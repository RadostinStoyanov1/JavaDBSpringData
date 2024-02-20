package P04_Java_DB_Apps_Introduction_Exrecise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class P06RemoveVillain {
    private static final String GET_VILLAIN_NAME_BY_ID = "SELECT v.name FROM villains v WHERE v.id = ?";
    private static final String NO_VILLAIN_FOUND_FORMAT = "No such villain was found";
    private static final String GET_MINION_COUNT_OF_VILLAIN = "SELECT COUNT(mv.minion_id) AS 'minion_count' " +
            "FROM minions_villains mv WHERE mv.villain_id = ?";
    private static final String FREE_MINIONS_OF_VILLAIN_ID = "DELETE mv FROM minions_villains mv WHERE mv.villain_id = ?";
    private static final String DELETE_VILLAIN_BY_ID = "DELETE v FROM villains v WHERE v.id = ?";
    private static final String VILLAIN_DELETED_FORMAT = "%s was deleted%n";
    private static final String MINIONS_COUNT_RELEASED_FORMAT = "%d minions released%n";

    public static void main(String[] args) throws SQLException {
        Connection connection = Utils.getSQLConnection();
        Scanner scanner = new Scanner(System.in);

        int villainId = scanner.nextInt();

        PreparedStatement getVillainNameStmt = connection.prepareStatement(GET_VILLAIN_NAME_BY_ID);
        getVillainNameStmt.setInt(1, villainId);
        ResultSet villainSet = getVillainNameStmt.executeQuery();

        if (!villainSet.next()) {
            System.out.println(NO_VILLAIN_FOUND_FORMAT);
            connection.close();
            return;
        }

        String villainName = villainSet.getString("name");

        PreparedStatement getMinionsCountStmt = connection.prepareStatement(GET_MINION_COUNT_OF_VILLAIN);
        getMinionsCountStmt.setInt(1, villainId);
        ResultSet minionsCountSet = getMinionsCountStmt.executeQuery();
        minionsCountSet.next();

        int minionsCount = minionsCountSet.getInt("minion_count");

        connection.setAutoCommit(false);

        try (PreparedStatement freeMinionStmt = connection.prepareStatement(FREE_MINIONS_OF_VILLAIN_ID);
             PreparedStatement deleteVillainStmt = connection.prepareStatement(DELETE_VILLAIN_BY_ID))
        {
            freeMinionStmt.setInt(1, villainId);
            freeMinionStmt.executeUpdate();

            deleteVillainStmt.setInt(1, villainId);
            deleteVillainStmt.executeUpdate();

            connection.commit();

            System.out.printf(VILLAIN_DELETED_FORMAT, villainName);
            System.out.printf(MINIONS_COUNT_RELEASED_FORMAT, minionsCount);
        } catch (SQLException e) {
            e.printStackTrace();

            connection.rollback();
        }
        connection.close();
    }
}
