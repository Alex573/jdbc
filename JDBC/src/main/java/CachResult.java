import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.net.URL;
import java.sql.*;

public class CachResult {
    static String username = "root";
    static String pass = "root";
    static String connctionUrl = "jdbc:mysql://localhost:3306/jdbc?characterEncoding=UTF-8&useSSL=false&relaxAutoCommit=true";


    public static void main(String[] args) {
        ResultSet resultSet = getData();
        try {
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id"));
                System.out.println(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CachedRowSet cachedRowSet = (CachedRowSet) resultSet;


        try {
            cachedRowSet.setUrl(connctionUrl);
            cachedRowSet.setUsername(username);
            cachedRowSet.setPassword(pass);
//            cachedRowSet.setCommand("select * from books where id = ?");
//            cachedRowSet.setInt(1, 1);
//            cachedRowSet.setPageSize(20);
//            cachedRowSet.execute();
//            do {
//                while (cachedRowSet.next()){
//                    System.out.println(cachedRowSet.getInt("id"));
//                    System.out.println(cachedRowSet.getString("name"));
//                }
//            }while (cachedRowSet.nextPage());
            CachedRowSet cachedRowSet2 = (CachedRowSet) resultSet;
            cachedRowSet2.setTableName("Books");
            cachedRowSet2.absolute(1);
            cachedRowSet2.deleteRow();
            cachedRowSet2.beforeFirst();
            while (cachedRowSet2.next()){
                System.out.println(cachedRowSet2.getInt("id"));
                System.out.println(cachedRowSet2.getString("name"));
            }
            cachedRowSet2.acceptChanges(DriverManager.getConnection(connctionUrl,username,pass));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static ResultSet getData() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        try (Connection connection = DriverManager.getConnection(connctionUrl, username, pass);
             Statement stat = connection.createStatement()
        ) {
            System.out.println("Connceted with databaze");
            stat.executeUpdate("DROP TABLE books");
            stat.executeUpdate("CREATE TABLE books (id INT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                    " name VARCHAR(30) NOT NULL)");
            stat.executeUpdate("INSERT INTO books (name) VALUES ('Java8')", Statement.RETURN_GENERATED_KEYS);
            stat.executeUpdate("INSERT INTO books (name) VALUES ('Code Davinchi ')", Statement.RETURN_GENERATED_KEYS);
            stat.executeUpdate("INSERT INTO books (name) VALUES ('Spring')", Statement.RETURN_GENERATED_KEYS);

            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet cachedRowSet = factory.createCachedRowSet();


            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = state.executeQuery("SELECT * FROM books");
            cachedRowSet.populate(resultSet);
            return cachedRowSet;
        } catch (SQLException e) {
            return null;
        }
    }

}
