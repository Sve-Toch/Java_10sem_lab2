import java.sql.*;
import java.util.HashMap;
import java.util.Map.Entry;
public class DataBase {
	private Connection c=null;
	public DataBase(){
		String driver ="com.mysql.jdbc.Driver";
		try {
			Class.forName(driver).newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			System.err.println("Error of DB Driver" );
		}
		try {
			 c = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
			 
		} 
		
		catch (SQLException e) {
			System.err.println("Error DB connect" );
		}
		
	}
	public void cleanTable() throws SQLException
	{
		Statement st;

		st = c.createStatement();
		st.executeUpdate("TRUNCATE TABLE WordCount");
	}
	public synchronized void insetPage(String pageName, HashMap<String, Integer> wordToCount)
	{
		for (Entry<String, Integer> entry : wordToCount.entrySet())
		{
			Statement st;
			try {
				st = c.createStatement();
			String key = entry.getKey().toString();
		String text ="INSERT INTO WordCount(Word, Count, Adress) VALUES (\""+key+"\",'"+entry.getValue()+"','"+pageName+"')";
			st.executeUpdate(text);
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
		System.out.println("Еще одна запись в таблицу");
	}
	public void closeConn()
	{
		if (c!=null)
		{try {
			c.close();
		} catch (SQLException e) {
			System.err.println("Error connection close" );

			
		}}
	}
	public void selectAll()
	{
		try (
			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery("select * from WordCount");
) {
    while ( rs.next() ) {
 
        
         System.out.println(  rs.getObject(1) +"--"+ rs.getObject(2)+"--"+rs.getObject(3)+"--" + rs.getObject(4));
        
    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
