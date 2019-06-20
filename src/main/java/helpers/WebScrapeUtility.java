package helpers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

public class WebScrapeUtility {

	private static String absImagesURL;
	private static Elements getDocumentClass;
	private static String title;
	
	public void pullImages(Document doc,String innerClass,String targetHTMLTag,
						String innerAttrValue, String outputPath) {
		
		getDocumentClass = doc.select(innerClass);
			//System.out.print(getDocumentClass);
			if(getDocumentClass.isEmpty() == true) {
				throw new Selector.SelectorParseException("Parsing images select error");
			}

		// select "image" tag with specific class in it's resource
   		Elements imageElements = getDocumentClass.select(targetHTMLTag);
   		
   		//Regexp for selecting image file extensions from "img type " classes
   		imageElements = imageElements.select("img["+innerAttrValue+"~=(?i)\\.(png|jpg|jpe?g|gif)]"); 
    	
    		for (Element image : imageElements) {  

    			/*get url of each and call downloadImage method
    			 * Change image.attr() to the correct target attribute of domain** 
    			 */
    			String absValue = "abs:";//Don't alter this
    		
    			absImagesURL = image.attr(absValue+innerAttrValue);
  
    			downloadImage(absImagesURL,outputPath);
    		}
	}
	
	public String getAbsImagesURLS() {
		return(absImagesURL);
	}

	// downloads images to specified directory
	private void downloadImage(String absImageURL, String outputPath)	{
    
		//get file name from image path
		String strImageName = 
            absImageURL.substring( absImageURL.lastIndexOf("/") + 1 );
    
		System.out.println("Saving: " + strImageName + ", from: " + absImageURL);
    
    	try {
        
    		//open the stream from URL
    		URL urlImage = new URL(absImageURL);
    		InputStream in = urlImage.openStream();
        
    		byte[] buffer = new byte[4096];
    		int n = -1;
    
    		
			OutputStream os = 
    				new FileOutputStream( outputPath  + "/" + strImageName );
        
    		//write bytes to the output stream
    		while ( (n = in.read(buffer)) != -1 ){
            
    			os.write(buffer, 0, n);
    		}
    		//close the stream
    		os.close();
        
    		System.out.println("Image saved");
        
    		} catch (IOException e) {
    				e.printStackTrace();
    		}
    
	}
	
	public void captureText(Document doc, String targetTextTag) {

		String articleText = doc.select(targetTextTag).text();

			if (articleText.isEmpty()== true)	{
				throw new Selector.SelectorParseException("Parsing text selector error.  ");
			}
		//heading for textfile before text is written
		String heading = "Body Text";

        try {
        	WriteToDisk.WritingFiles(heading, articleText);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void pullTitle(Document doc) {
		
    	title = doc.title();
    	
    	if (title.isEmpty()== true)	{
			throw new Selector.SelectorParseException("Title not Found. Parsing selector error.  ");
		}
	//heading for textfile before text is written
    	String heading = "Body Title";

    	try {
    		WriteToDisk.WritingFiles(heading, title);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
	}
	
	public String returnTitle() {
		return(title);
		
	}

}