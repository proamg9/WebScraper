

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import helpers.WebScrapeUtility;
import java.io.File;

public class ScaperTests {
	
	private static Document doc;
	private static WebScrapeUtility ScrapeUtility;
	private String outputPath = ".";
	
	@BeforeEach
	private void Runner() {
		
		//initialisations
		try {
			doc = Jsoup.connect("https://www.mirror.co.uk/news/politics/top-tories-beg-mps-drop-16247222").get();
	
		} catch (Exception e){
			e.printStackTrace();
		}
		
		ScrapeUtility = new WebScrapeUtility();
	}
	
	@Test
	public void testPullImages() {
	    
		// Arrange - test against a image URL
		String expectedResult = "https://i2-prod.mirror.co.uk/incoming/article16023462.ece/ALTERNATES/s615b/0_Cabinet-meeting-in-London-United-Kingdom-30-Apr-2019.jpg";
		
		//Act
		ScrapeUtility.pullImages(doc, "figure.in-article-image", "img", "data-src",outputPath);
		
		String absURL = ScrapeUtility.getAbsImagesURLS();

		//assert
		assertEquals(absURL,expectedResult);

	}
	
	@Test
	public void testPullImagesException() {
		//act
	    Throwable thrown = assertThrows(Exception.class,()-> {
				ScrapeUtility.pullImages(doc,"h","d","d",outputPath);
	           }
	        );
	    //assert
	    assertEquals("Parsing images select error",thrown.getMessage());
	}
	
	@Test
	public void testCaptureText() throws IOException {
	    
		// Arrange
		String expectedResult = "I think we've got some good candidates, and whoever takes over will do a good job for the country.\"";
		//Act
		ScrapeUtility.captureText(doc,"p");
		//Assert
		assertEquals(expectedResult,getSubString());
		   
	}
	
	@Test
	public void testCaptureTextException() {
		//act
	    Throwable thrown = assertThrows(Exception.class,()-> {
				ScrapeUtility.captureText(doc,"h");
	           }
	        );
	    //assert
	    assertEquals("Parsing text selector error.  ",thrown.getMessage());
	}
	
	//returns substring to compare in tests
	private String getSubString() throws IOException {
        
		File textToConvertInToString = new File("results.txt");
       
        String textFromFile = FileUtils.readFileToString(textToConvertInToString,"utf-8");
        
        String newsubString = textFromFile.substring(textFromFile.length() - 99);
			return newsubString;

    }
	
	@Test
	public void testPullTitle() throws IOException {
	    
		// Arrange
		String expectedResult = "Top Tories beg MPs to drop out of 'Grand National shambles' leadership contest - Mirror Online";
		//Act
		ScrapeUtility.pullTitle(doc);
		String returnedTitle = ScrapeUtility.returnTitle();
		//Assert
		assertEquals(expectedResult,returnedTitle);
		   
	}
	
	@Test
	public void testPullTitleException() {
		
		//arrange
		//mock html without proper 'title' tags
		String html = "<html><head><titleFirst parse</title></head>"
			  + "<body><p>Parsed HTML into a doc.</p></body></html>";
		//act
	    Throwable thrown = assertThrows(Exception.class,()-> {
	    		
	    		
	    		Document doc = Jsoup.parse(html);
				
	    		ScrapeUtility.pullTitle(doc);
	           }
	        );
	    //assert
	    assertEquals("Title not Found. Parsing selector error.  ",thrown.getMessage());
	}
	
	
}
