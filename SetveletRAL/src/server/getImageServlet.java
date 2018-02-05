package server;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 * 这是用来获取服务器上存储的图片的
 */
@WebServlet("/getImageServlet")
public class getImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        String responseText="";
		Connection connect = DBUtil.getConnect();  
		try{
			 	Statement statement = (Statement) connect.createStatement();  
//			 	获取db数据库上所有的数据
	            String sql = "select pic_id,pic_path,pic_username,pic_userId,pic_content,pic_title from " + DBUtil.TABLE_PICINFO ;  
	            System.out.println(sql);  
	            ResultSet result = statement.executeQuery(sql);  
	            while(result.next()) {
	            responseText =	responseText+getInfo(result)+";";
	            }
		}catch(SQLException e){
			e.printStackTrace();	
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(responseText);
		System.out.println(responseText);
		
	}
	private String getInfo(ResultSet result) {
		int id = 1;
		String username=null;
		String userId = null;
		String content =null;
		String title = null;
		String path = null;
	    	try {
	    			username =result.getString("pic_username");
	    			userId = result.getString("pic_userId");
	    			content = result.getString("pic_content");	    			
				id = result.getInt("pic_id");
				title =result.getString("pic_title");
				path = result.getString("pic_path");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	return String.valueOf(id)+","+path+","+username+","+userId+","+content+","+title;
		
	}
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
