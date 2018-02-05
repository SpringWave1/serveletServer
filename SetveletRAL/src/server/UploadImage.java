package server;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;


@WebServlet("/UploadImage")
public class UploadImage extends HttpServlet {
	
	

	private static final long serialVersionUID = 1L;
       

    public UploadImage() {
        super();
        // TODO Auto-generated constructor stub
    }
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		接收图片与用户Id
		changeUserImage(request, response);
	}
	
		private void changeUserImage(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {
			String message = "";
			String fieldName ="";
			String fieldValue="";
			
//				大哥们，我知道用这种方法取两个string是二逼的，下次改，下次改
					
			try{
				DiskFileItemFactory dff = new DiskFileItemFactory();
				ServletFileUpload sfu = new ServletFileUpload(dff);
				List<FileItem> items = sfu.parseRequest(request);
				for(FileItem item:items){
					if(item.isFormField()){
						//普通表单
						fieldName = item.getFieldName();
						fieldValue = item.getString();				
						System.out.println("name="+fieldName + ", value="+ fieldValue);
					} else {// 获取上传字段
						// 更改文件名为唯一的
						String filename = item.getName();
						if (filename != null) {
							filename = IdGenertor.generateGUID() + "." + FilenameUtils.getExtension(filename);
						}
						// 生成存储路径
						String storeDirectory = getServletContext().getRealPath("/files/images");
						File file = new File(storeDirectory);
						if (!file.exists()) {
							file.mkdir();
						}
						String path = genericPath(filename, storeDirectory);
						// 处理文件的上传
						try {
							item.write(new File(storeDirectory + path, filename));
							String filePath = "/files/images" + path + "/" + filename;
							System.out.println("filePath="+filePath);
//								message = filePath;
							System.out.println(filePath);
							Connection connect = DBUtil.getConnect();  
					        Statement statement = (Statement) connect.createStatement();
					        String[] buf2 = fieldName.split(";");
					        String pic_userId = buf2[0];
					        String pic_title = buf2[buf2.length-1];
					        String[] buf = fieldValue.split(";");
					        String pic_username = buf[0];
					        String pic_content = buf[buf2.length-1];
					        String sqlInsertInfo = "insert into " + DBUtil.TABLE_PICINFO + "(pic_path,pic_username,pic_userId,pic_content,pic_title) values('" + filePath +"','"+pic_username+"','"+pic_userId+"','"+pic_content+"','"+pic_title+"')";   
		                    int row1 = statement.executeUpdate(sqlInsertInfo);
		                    if(row1 ==1) {
		                    	System.out.println("注册成功");
		                    	
		                    	message = "图片上传成功";
		                    }else {
		                    	System.out.println("注册失败");
		                    	
		                    }
							
						} catch (Exception e) {
							message = "上传图片失败";
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				message = "上传图片失败";
			} finally {
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().write(message);
			}
		}
	
	//计算文件的存放目录
	private String genericPath(String filename, String storeDirectory) {
		int hashCode = filename.hashCode();
		int dir1 = hashCode&0xf;
		int dir2 = (hashCode&0xf0)>>4;
		
		String dir = "/"+dir1+"/"+dir2;
		
		File file = new File(storeDirectory,dir);
		if(!file.exists()){
			file.mkdirs();
		}
		return dir;
	}

/**
 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
 */
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
	doGet(request, response);
}

}
