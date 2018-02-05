package server;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class DBUtil {
	public static final String TABLE_PASSWORD = "table_user_password";  
    public static final String TABLE_USERINFO = "table_user_info"; 
    public static final String TABLE_PICINFO = "table_picInfo";
  
    // connect to MySql database  
    public static Connection getConnect() {  
        String url = "jdbc:mysql://localhost:3306/id"; // 数据库的Url  
        Connection connecter = null;  
        try {  
            Class.forName("com.mysql.jdbc.Driver"); // java反射，固定写法  
            connecter = (Connection) DriverManager.getConnection(url, "root", "app123");  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        } catch (SQLException e) {  
            System.out.println("SQLException: " + e.getMessage());  
            System.out.println("SQLState: " + e.getSQLState());  
            System.out.println("VendorError: " + e.getErrorCode());  
        }  
        return connecter;  

}

}
