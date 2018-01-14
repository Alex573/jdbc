import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateSql {
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
                    " name VARCHAR(30) NOT NULL, dt DATE)");
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO books" +
                    "(name, dt) VALUES ('den', ?)");
            preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));
            preparedStatement.execute();
            System.out.println(preparedStatement);
            ResultSet rs = statement.executeQuery("SELECT * FROM books");
            while (rs.next()){
                System.out.println(rs.getDate("dt"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
