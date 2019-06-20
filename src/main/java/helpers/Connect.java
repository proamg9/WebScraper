package helpers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Connect {
	

	public Document documentInit(String url) {
		
		Document doc = null;
		
		try {
			
			doc = Jsoup.connect(url).get();
	
		} catch (Exception e) {
			
			System.out.println("error connecting to url");
			e.printStackTrace();	
		}
		return(doc);
	}
	
	

}
