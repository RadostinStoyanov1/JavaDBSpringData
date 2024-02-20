package P04_Java_DB_Apps_Introduction_Exrecise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class P07PrintAllMinionNames {
    private static final String GET_MINIONS_NAMES = "SELECT m.name FROM minions m";
    public static void main(String[] args) throws SQLException {
        Connection connection = Utils.getSQLConnection();
        PreparedStatement getMinionNamesStmt = connection.prepareStatement(GET_MINIONS_NAMES);
        ResultSet minionNamesSet = getMinionNamesStmt.executeQuery();

        ArrayList<String> minionNames = new ArrayList<>();

        while (minionNamesSet.next()) {
            minionNames.add(minionNamesSet.getString(Constants.COLUMN_LABEL_NAME));
        }

        while (minionNames.size() > 0) {
            System.out.println(minionNames.remove(0));
            if (minionNames.size() == 0) {
                break;
            }
            System.out.println(minionNames.remove(minionNames.size() - 1));
        }
    }
}
