import java.sql.*;
import java.time.LocalDate;

public class Procedury {
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

//             CREATE PROCEDURE BooksCount (OUT cnt INT)
//                BEGIN
//                        SELECT count(*) INTO cnt FROM books;
//                END;

            CallableStatement callableStatement = connection.prepareCall("{call BooksCount(?)}");
            callableStatement.registerOutParameter(1,Types.INTEGER);
            callableStatement.execute();
            System.out.println(callableStatement.getInt(1));
            System.out.println("---------------------");

//            CREATE PROCEDURE getBooks (bookID int)
//                BEGIN
//                   SELECT * FROM books WHERE id = bookID;
//                END;

            CallableStatement callStat = connection.prepareCall("{call getBooks(?)}");
            callStat.setInt(1,1);
            if (callStat.execute()) {
                ResultSet resultSet = callStat.getResultSet();
                while (resultSet.next()){
                    System.out.println(resultSet.getInt("id"));
                    System.out.println(resultSet.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
