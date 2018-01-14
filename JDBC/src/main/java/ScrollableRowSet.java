import java.sql.*;

public class ScrollableRowSet {
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
            statement.executeUpdate("INSERT INTO books (name) VALUES ('Code Davinchi ')", statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate("INSERT INTO books (name) VALUES ('Spring')");

            Statement stat = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//            PreparedStatement preparedStatement = connection.prepareStatement("", ResultSet.TYPE_SCROLL_INSENSITIVE,
//                    ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = stat.executeQuery("SELECT * FROM books");
            if (resultSet.next()){
                System.out.println(resultSet.getString("name"));
            }
            if (resultSet.next()){
                System.out.println(resultSet.getString("name"));
            }
            if (resultSet.previous()){
                System.out.println(resultSet.getString("name"));
            }
            if (resultSet.relative(2)){
                System.out.println(resultSet.getString("name"));
            }
            if (resultSet.relative(-2)){
                System.out.println(resultSet.getString("name"));
            }
            if (resultSet.absolute(2)){
                System.out.println(resultSet.getString("name"));
            }
            if (resultSet.first()){
                System.out.println(resultSet.getString("name"));
            }
            if (resultSet.last()){
                System.out.println(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
