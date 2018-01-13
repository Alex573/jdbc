import java.sql.*;

public class Jdbc2Les {
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
            statement.executeUpdate("DROP TABLE useras");
            statement.executeUpdate("CREATE TABLE useras (id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR(30) NOT NULL, password VARCHAR(30) NOT NULL)");
            statement.executeUpdate("INSERT INTO useras (name, password) VALUES ('max', '123')");
            statement.executeUpdate("INSERT INTO useras SET name='lex', password='456'");
            //String userId = "1";
            String userId = "1' or 1 = '1";
//            ResultSet resultSet = statement.executeQuery("select * from useras where id ="+userId+"");
//            while (resultSet.next()){
//                System.out.println("userName: " + resultSet.getString("name"));
//                System.out.println("userpassword: " + resultSet.getString("password"));
//            }

            PreparedStatement preparedStatement = connection.prepareStatement("Select * from useras WHERE id = ?");
            preparedStatement.setString(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                System.out.println("userName: " + resultSet.getString("name"));
                System.out.println("userpassword: " + resultSet.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
