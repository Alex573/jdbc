import java.sql.*;

public class Main {
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
//            statement.executeUpdate("DROP TABLE test");
//
//            statement.executeUpdate("CREATE TABLE Books\n" +
//                    "(\n" +
//                    "    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n" +
//                    "    name VARCHAR(50) NOT NULL\n" +
//                    ")");
//            statement.executeUpdate("INSERT INTO Books (NAME) VALUES('Java8')");
            statement.executeUpdate("INSERT INTO books set name = 'Bardak'");
            statement.executeUpdate("DELETE FROM books WHERE id > '5';");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books");
            while (resultSet.next()){
                System.out.print(resultSet.getInt(1)+"\t");
                System.out.println(resultSet.getString("name"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
