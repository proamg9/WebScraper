package scrapeutility;


import helpers.Connect;
import helpers.WebScrapeUtility;
import org.jsoup.nodes.Document;

public class Scraper {
		
	private static String url;

	public static void main(String[] args) throws Exception {
		// initialise worker class
		
		WebScrapeUtility ScrapeUtility = new WebScrapeUtility();
		
		/*input data
		 * 
		 * */
		String outputPath = ".";
		url = "https://www.dailymail.co.uk/news/article-7064685/Theresa-address-nation-TODAY-pledge-Britain-new-PM-summer.html";
		// inner html class selector var
		String innerClass = "div.mol-img-group";
		// target specific tag for images
		String targetImagesTag = "img";
		String targetTextTag = "p.mol-para-with-font";
		// images attribute value for target domain
		String innerAttrValue = "src";
		
		/* Program execution 
		 * */
		
		// call connect class and get required 'document'
		Connect con = new Connect();
			Document doc = con.documentInit(url);
		
		//get body Main Title
	    ScrapeUtility.pullTitle(doc);
		
	    //pull body text and write to file
        ScrapeUtility.captureText(doc,targetTextTag);
        	
        // scrape images from body
        ScrapeUtility.pullImages(doc, innerClass, targetImagesTag, innerAttrValue,outputPath);

	}


}

