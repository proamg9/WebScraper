package helpers;
import java.io.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class WriteToDisk {
	
	
	public static void WritingFiles(String headings, String outputText) throws IOException{
	
	try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("results.txt",true), StandardCharsets.UTF_8))) {
	    writer.append("\n" + headings + "\n" + outputText);
	    writer.close();
	} 
	catch (IOException ex) {
	    System.out.println("Writing to file error" + ex);
	}
	

	}
}
