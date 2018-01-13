import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;

public class Blob {
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
            statement.executeUpdate("CREATE TABLE books (id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR(30) NOT NULL, img BLOB)");

            BufferedImage image = ImageIO.read(new File("smile.jpg"));
            java.sql.Blob blob = connection.createBlob();
            try(OutputStream os = blob.setBinaryStream(1)) {
                ImageIO.write(image, "jpg", os);
            }

            PreparedStatement stat = connection.prepareStatement("INSERT INTO books (name, img) VALUES (?, ?)");
            stat.setString(1,"inferno");
            stat.setBlob(2, blob);
            stat.execute();

            ResultSet rs = stat.executeQuery("SELECT * FROM  books");
            while (rs.next()){
                java.sql.Blob blob2 = rs.getBlob("img");
                BufferedImage image2 = ImageIO.read(blob2.getBinaryStream());
                File outfile = new File("retsnile.png");
                ImageIO.write(image2,"png", outfile);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
