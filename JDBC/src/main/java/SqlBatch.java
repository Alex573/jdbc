import java.sql.*;

public class SqlBatch {
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
            Statement stat = connection.createStatement()
        ){
            System.out.println("Connceted with databaze");
            connection.setAutoCommit(false);
            stat.addBatch("DROP TABLE books");
            stat.addBatch("CREATE TABLE books (id INT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                    " name VARCHAR(30) NOT NULL)");
            stat.addBatch("INSERT INTO books (name) VALUES ('Java8')");
            stat.addBatch("INSERT INTO books (name) VALUES ('Code Davinchi ')");
            stat.addBatch("INSERT INTO books (name) VALUES ('Spring')");
            if (stat.executeBatch().length == 5) {
                connection.commit();
            }else {
                connection.rollback();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
