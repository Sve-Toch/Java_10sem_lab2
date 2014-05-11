import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;


public class PageIndex implements Runnable{
private String url;
private String fileName2;
private static DataBase dB;
	public PageIndex(String url, String fileName2, DataBase dB)
{
		this.url=url;
		this.fileName2=fileName2;
		this.dB=dB;
}
public static HashMap<String, Integer> FindIndex(String[] words,HashMap<String, Integer> wordToCount )
{ 
 for (String word : words)
{
	 
  word =word.replace(".", "").replace("‘", "").replace(".", "").replace(",", "").replace("\"","").replace("…", "");

	if (word.length()>=2)
	{
		if (!wordToCount.containsKey(word))
		{
			wordToCount.put(word, new Integer(1));
		}
		else 
		{
			int current = wordToCount.get(word)+1;
			wordToCount.remove(word);
			wordToCount.put(word, current);
		}
	}
}
 return wordToCount;
}

public static void IndexWriter(HashMap<String, Integer> wordToCount,String fileName  ) 
{
	PrintWriter file= WriteFile(fileName);
	for (Entry<String, Integer> entry : wordToCount.entrySet())
	{
		file.println(entry.getKey()+"   "+entry.getValue());		
	}
		
		 file.close();
	
}
public static PrintWriter WriteFile( String fileName ) 
{
	try {
	File file = new File(fileName);
	if(!file.exists()){
		file.createNewFile();
		}
	PrintWriter outfile = new PrintWriter(file.getAbsoluteFile());
	return outfile;
	}
	catch (IOException ioe) {
		System.err.println("File error: "+ ioe);	
		return null;
	}
}
@Override
public void run() {
	String bodyText=null;
	String pageText = null;
	try{
	URL myUrl  = new URL(url);
    String fileName = "result_html.html";
    String fileName3=fileName2+".txt";
    PrintWriter outfile = WriteFile(fileName) ;
    try {		
	BufferedReader br = new BufferedReader(
			new InputStreamReader(myUrl.openStream()));
	String line = null;			
	while((line = br.readLine())!=null)
		{
		outfile.println(line);
		pageText+= line + "\r\n";	
		}
	br.close();
	org.jsoup.nodes.Document htmlPage =  Jsoup.parse(pageText);
	bodyText = ((org.jsoup.nodes.Document) htmlPage).body().text();
	Elements href =htmlPage.select("a[href]");
	String http = href.attr("href");
	Thread pi2 =new Thread( new PageIndex(http,fileName2, dB));
	pi2.start();
	System.out.println(http);
	String[] words = bodyText.split(" ");
	HashMap<String, Integer> index2 =  new HashMap<String, Integer>();
	index2 = FindIndex(words,index2);
	IndexWriter(index2,fileName3);
	
	dB.insetPage(url, index2);
	
	
	}
	finally {				 
		outfile.close();
			}
	}
	catch(MalformedURLException me){
	System.err.println("Unnoen host: "+ me);
	}
	catch (IOException ioe) {
	System.err.println("Input error: "+ ioe);	
	}
}

}
