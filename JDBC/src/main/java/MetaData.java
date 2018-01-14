import java.sql.*;

public class MetaData {
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
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null,null,null,new String[]{"Table"});
            while (resultSet.next()){
//                System.out.println(resultSet.getString(1));
//                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getString(3));
//                System.out.println(resultSet.getString(4));
            }

            System.out.println("--------------------------");
            ResultSet set = statement.executeQuery("SELECT * FROM books");
            ResultSetMetaData metaData1 = set.getMetaData();
            for (int i = 1; i <= metaData1.getColumnCount(); i++) {
                System.out.println(metaData1.getColumnLabel(i));
                System.out.println(metaData1.getColumnType(i));

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
