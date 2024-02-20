package P04_Java_DB_Apps_Introduction_Exrecise;

import java.sql.*;
import java.util.Scanner;

public class P09IncreaseAgeProcedure {
    private static final String CALL_USP_PROCEDURE_GET_OLDER = "CALL usp_get_older(?)";

    public static void main(String[] args) throws SQLException {
        Connection connection = Utils.getSQLConnection();
        Scanner scanner = new Scanner(System.in);

        int minionId = scanner.nextInt();

        CallableStatement cs = connection.prepareCall(CALL_USP_PROCEDURE_GET_OLDER);
        cs.setInt(1, minionId);
        ResultSet rs = cs.executeQuery();

        rs.next();

        System.out.println(rs.getString(Constants.COLUMN_LABEL_NAME)
                + " "
                + rs.getInt("age"));

        connection.close();
    }
}
