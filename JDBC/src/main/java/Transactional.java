import java.sql.*;

public class Transactional {
    public static void main(String[] args) {
        String username = "root";
        String pass = "root";
        String connctionUrl = "jdbc:mysql://localhost:3306/jdbc?characterEncoding=UTF-8&useSSL=false";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        try{
           connection = DriverManager.getConnection(connctionUrl,username,pass);
            Statement statement = connection.createStatement();
            System.out.println("Connceted with databaze");

            connection.setAutoCommit(false);
            statement.executeUpdate("DROP TABLE books");
            statement.executeUpdate("CREATE TABLE books (id INT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                    " name VARCHAR(30) NOT NULL)");
            statement.executeUpdate("INSERT INTO books (name) VALUES ('Java8')");
           // Savepoint savepoint = connection.setSavepoint();
            statement.executeUpdate("INSERT INTO books (name) VALUES ('Code Davinchi ')");
            statement.executeUpdate("INSERT INTO books (name) VALUES ('Spring')");

            //connection.commit();//1
            // connection.rollback();//2
//            connection.rollback(savepoint);//3
//            connection.commit();
//            connection.releaseSavepoint(savepoint);
            connection.commit();

        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
}
