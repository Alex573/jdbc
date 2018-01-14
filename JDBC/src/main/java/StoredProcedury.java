import java.sql.*;

public class StoredProcedury {
    public static void main(String[] args) {
        String username = "root";
        String pass = "root";
        String connctionUrl = "jdbc:mysql://localhost:3306/jdbc?characterEncoding=UTF-8&useSSL=false";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try(Connection connection = DriverManager.getConnection(connctionUrl,username,pass );
            Statement statement = connection.createStatement()
        ){
            System.out.println("Connceted with databaze");
            statement.executeUpdate("DROP TABLE books");
            statement.executeUpdate("CREATE TABLE books (id INT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                    " name VARCHAR(30) NOT NULL)");
            statement.executeUpdate("INSERT INTO books (name) VALUES ('Java8')");
            statement.executeUpdate("INSERT INTO books (name) VALUES ('Spring')");


//            CREATE PROCEDURE getCount ()
//              BEGIN
//                   SELECT count(*) FROM useras;
//                   SELECT count(*) FROM books ;
//               END;

            CallableStatement callStat = connection.prepareCall("{call getCount()}");
            boolean hasresult = callStat.execute();
            while (hasresult){
                ResultSet resultSet = callStat.getResultSet();
                while (resultSet.next()){
                    System.out.println(resultSet.getInt(1));
                }
                hasresult = callStat.getMoreResults();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
