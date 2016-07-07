import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SaveToDB{
	Connection con;			
	public Statement stmt;	
	public ResultSet rs;	
	public SaveToDB(){}
	public SaveToDB(String s,String user,String password) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
		if(s.equals("mysql"))
			SaveToMysql(user,password);
		if(s.equals("access"))
			SaveToAccess(user,password);
	}
	public void SaveToMysql(String user,String password) throws ClassNotFoundException, SQLException	
	{
		con=null;
		stmt=null;
		rs=null;
		
		String url = "jdbc:mysql://localhost:3306/notebook?useUnicode=true&characterEncoding=UTF-8";	
  		Class.forName("com.mysql.jdbc.Driver");  
		Connection con = DriverManager.getConnection(url, user, password);
	    stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
	    System.out.println( "成功连接到mysql" );
   }
	public void SaveToAccess(String user,String password) throws ClassNotFoundException, SQLException	
, InstantiationException, IllegalAccessException
	{
		con=null;
		stmt=null;
		rs=null;

		/*Class.forName("sun.jdbc.odbc.JdbcOdbcDriver"); 
		//String url="jdbc:odbc:mynote";   
		String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=E:/note.mdb";
		Connection con = DriverManager.getConnection(url); */
        String url="jdbc:Access:///E:note.mdb";
        Class.forName("com.hxtt.sql.access.AccessDriver").newInstance();
        con=DriverManager.getConnection(url,user,password);
        //String url="Jdbc:Odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=note.mdb";
	    stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
	    
	}
}