import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class OtherTransactional {
    static String username = "root";
    static String pass = "root";
    static String url = "jdbc:mysql://localhost:3306/jdbc?characterEncoding=UTF-8&useSSL=false&relaxAutoCommit=true";


    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(url, username, pass);
             Statement stmt = conn.createStatement()
        ) {
            System.out.println("Connceted with databaze");
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            stmt.execute("UPDATE books SET name = 'new value' WHERE id = 1");
            new OtTrans().start();
            Thread.sleep(2000);
            conn.rollback();


        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    static class  OtTrans extends Thread {
        @Override
        public void run() {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            try (Connection conn = DriverManager.getConnection(url, username, pass);
                 Statement stmt = conn.createStatement()
            ) {
                System.out.println("Connceted with databaze");
                conn.setAutoCommit(false);
                conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
                ResultSet resultSet = stmt.executeQuery("SELECT * FROM books");
                while (resultSet.next()){
                    System.out.println(resultSet.getString("name"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
