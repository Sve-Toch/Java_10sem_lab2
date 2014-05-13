import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Main {
private static Logger log=LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		boolean work=true;
		while(work==true)
		{
		BufferedReader input=null;
		DataBase dB = new DataBase();
		System.out.println("********************************");		
		System.out.println("* Для индексации нажмите     1.*\n* Для поиска нажмите         2.*\n* Для очистки данных нажмите 3.*\n* Для выхода нажмите         4.*");
		System.out.println("********************************");	
		char c=60;
		try {
			input = new BufferedReader(new InputStreamReader(System.in,"cp866"));
			c  =(char)input.read();
			
		} catch (IOException e2) {
			log.error("ошибка чтения меню");
		}
	switch (c){
	case 49:
	{
		String url="http://example.com/";		
		System.out.println("Введите сайт");
		
		/*try {
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
	break;}
	case 50:
	{
		log.info("Поиск");
		System.out.println("Введите слово для поиска");
		try {
			input = new BufferedReader(new InputStreamReader(System.in,"cp866"));
			String word =input.readLine();
			dB.serch(word.toLowerCase());
		} catch (IOException e) {
		log.error("ошибка ввода сслова для поиска");		
		}
		
		break;}
	case 51:{
		try {
			dB.cleanTable();
		} catch (SQLException e1) {
			log.error("Ощибка очистки БД");
		}
	break;}
	case 52:{
		work=false;
		break;}
	
	}}
		log.info("Пока");
		System.exit(0);
	}
	

	
}
