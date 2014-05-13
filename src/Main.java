//import java.io.BufferedReader;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Main {
private static Logger log=LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {

		String url="http://example.com/";		
		System.out.println("Введите сайт");
		/*BufferedReader input=null;
		try {
		input = new BufferedReader(new InputStreamReader(System.in,"cp866"));
					
			 url=input.readLine();
		} catch (IOException e) {
			System.err.println("Input url error: "+ e);
		} 
		*/ 
		System.out.println("Введите имя файла");
		String fileName2="file.txt";
		/*try {			
			 fileName2=input.readLine();
		} catch (IOException e) {
			log.error("Input file name error: "+ e);
		}  
		*/
		DataBase dB = new DataBase();	
		try {
			dB.cleanTable();
		} catch (SQLException e1) {
			log.error("Ощибка очистки БД");
		}
		Thread pi = new Thread(new PageIndex(url,fileName2, dB));
		pi.start();
		//Thread pi2 =new Thread( new PageIndex("http://javatechig.com/",fileName2, dB));
		//Thread pi2 =new Thread( new PageIndex("http://hadoop.apache.org/docs/r1.2.1/mapred_tutorial.html#Example%3A+WordCount+v1.0",fileName2, dB));
		//pi2.start();
		
		try {
			pi.join();
			log.info("Первый сайт кончился");			
		} catch (InterruptedException e) {
			log.error("Error join");
		}
	/*	try {
			pi2.join();
			log.info("второй сайт кончился");

		} catch (InterruptedException e) {
				log.error("Error join2")
		}
		*/
		
		//dB.selectAll();
		//dB.closeConn();

		
	}

	
}
