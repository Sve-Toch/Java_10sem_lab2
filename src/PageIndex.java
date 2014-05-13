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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class PageIndex implements Runnable{
Logger log=LoggerFactory.getLogger(PageIndex.class);
private String url;
private String fileName2;
private DataBase dB;
	public PageIndex(String url, String fileName2, DataBase dB)
{
		this.url=url;
		this.fileName2=fileName2;
		this.dB=dB;
}
public  HashMap<String, Integer> FindIndex(String[] words,HashMap<String, Integer> wordToCount )
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

public  void IndexWriter(HashMap<String, Integer> wordToCount,String fileName  ) 
{
	PrintWriter file= WriteFile(fileName);
	for (Entry<String, Integer> entry : wordToCount.entrySet())
	{
		file.println(entry.getKey()+"   "+entry.getValue());		
	}
		
		 file.close();
	
}
public  PrintWriter WriteFile( String fileName ) 
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
		log.error("File error: "+ ioe);	
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
	if (!http.equals("/"))
	{
	Thread pi2 =new Thread( new PageIndex(http,fileName2, dB));
	pi2.start();
	log.info("Найдена сссылка на: "+http);
	}
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
	log.error("Unnoen host: "+ me);
	}
	catch (IOException ioe) {
	log.error("Input error: "+ ioe);	
	}
}

}
