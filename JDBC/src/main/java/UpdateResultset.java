import java.sql.*;

public class UpdateResultset {
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
            statement.executeUpdate("INSERT INTO books (name) VALUES ('Code Davinchi ')");
            statement.executeUpdate("INSERT INTO books (name) VALUES ('Spring')");

            Statement stat = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//            PreparedStatement preparedStatement = connection.prepareStatement("",ResultSet.TYPE_SCROLL_SENSITIVE,
//                    ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = stat.executeQuery("SELECT * FROM books");
            while (resultSet.next()){
                System.out.print(resultSet.getInt("id")+"\t");
                System.out.println(resultSet.getString("name"));
            }

            resultSet.last();
            resultSet.updateString("name", "New Books");
            resultSet.updateRow();

            resultSet.moveToInsertRow();
            resultSet.updateString("name","Jva 10");
            resultSet.insertRow();


            resultSet.absolute(2);
            resultSet.deleteRow();

            resultSet.beforeFirst();
            while (resultSet.next()){
                System.out.print(resultSet.getInt("id")+"\t");
                System.out.println(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
