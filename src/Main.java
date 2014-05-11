import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.Jsoup;

import javax.swing.text.Document;


public class Main {


	public static void main(String[] args) {
	
		String url="http://example.com/";		
		System.out.println("Введите сайт");
		BufferedReader input=null;
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
			System.err.println("Input file name error: "+ e);
		}  
		*/
		DataBase dB = new DataBase();	
		try {
			dB.cleanTable();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Thread pi = new Thread(new PageIndex(url,fileName2, dB));
		pi.start();
		//Thread pi2 =new Thread( new PageIndex("http://javatechig.com/",fileName2, dB));
		//Thread pi2 =new Thread( new PageIndex("http://hadoop.apache.org/docs/r1.2.1/mapred_tutorial.html#Example%3A+WordCount+v1.0",fileName2, dB));
		//pi2.start();
		
		try {
			pi.join();
			System.out.println("первый сайт кончился");
		} catch (InterruptedException e) {
			System.err.println("sss");
		}
	/*	try {
			pi2.join();
			System.out.println("второй сайт кончился");

		} catch (InterruptedException e) {
			System.err.println("sss2");
		}
		*/
		
		//dB.selectAll();
		//dB.closeConn();
	
		
	}

	
}
