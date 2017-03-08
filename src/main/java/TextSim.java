import it.uniroma1.lcl.adw.ADW;
import it.uniroma1.lcl.adw.DisambiguationMethod;
import it.uniroma1.lcl.adw.ItemType;
import it.uniroma1.lcl.adw.comparison.Cosine;
import it.uniroma1.lcl.adw.comparison.SignatureComparison;
import it.uniroma1.lcl.adw.comparison.WeightedOverlap;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


public class TextSim {
	
	static String readFile(String path) throws InterruptedException  {
		for (int i = 0; i < 5; i++) {
			try{
				byte[] encoded = Files.readAllBytes(Paths.get(path));
				return Charset.defaultCharset().decode(ByteBuffer.wrap(encoded)).toString();
			}catch (Exception ignored){
				Thread.sleep(100);
			}
		}
		return null;
	}
	
	static void writeToFile(String path, String msg) throws InterruptedException{
		for (int i = 0; i < 5; i++) {
			try {
				Files.write(Paths.get(path), msg.getBytes());
				return;
			} catch (IOException e) {
				Thread.sleep(100);
			}
		}
		System.out.println("ERROR!!!");
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		String text1 = "";
		String text2 = "";
		String outFile = "";
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (args.length < 3){
			//the two lexical items to be compared
			text1 = "Download file from HTTPS server using Java";
			//text1 = "c#";
			//text1 = "How to order a list using?";
			text2 = "How do I download a file over HTTP using Python?";
			//text2 = " In, I want a list to be sorted.";
			
			text1 = "sort int";
			text2 = "order number";
			
			text1 = "How do you convert byte array to hex String";
			text2 = "Convert a string representation of a hex to a byte array";
			
			text1 = "folder";
			text2 = "directory";
			
			text1 = "How can we order a list using?";
			text2 = "In, I want a list to be sorted";
		} else {
			System.out.println(args[0] + " " + args[1]);
			text1 = readFile(args[0]);
			text2 = readFile(args[1]);
			outFile = args[2];
		}
		
		//text1 = "I want to download a file from the server which is using the secured connection protocol HTTPS. I could do it in the normal server, But, how I can do it using the HTTPS. If anyone have used the sample API, please help me to find the useful resources.";
		//text2 = "I have a small utility that I use to download a MP3 from a website on a schedule and then builds/updates a podcast XML file which I've obviously added to iTunes. The text processing that creates/updates the XML file is written in Python. I use wget inside a Windows .bat file to download the actual MP3 however. I would prefer to have the entire utility written in Python though. I struggled though to find a way to actually down load the file in Python, thus why I resorted to wget. So, how do I download the file using Python?";
		//text1 = "Generating list of all possible permutations of a string in?";
		//text2 = "Generating all permutations of a given string";
		
		
		text1 = SoftwareSpecificDB.queryExpansion(text1);
		text2 = SoftwareSpecificDB.queryExpansion(text2);
		
		
		//types of the two lexical items
		 ItemType srcTextType = ItemType.SURFACE;  
		 ItemType trgTextType = ItemType.SURFACE;

		// if lexical items has to be disambiguated
		// optional NONE for better performance, not relevant with long texts
		DisambiguationMethod disMethod = DisambiguationMethod.ALIGNMENT_BASED;      

		// measure for comparing semantic signatures
		// optional weigtedOvelap
		SignatureComparison measure = new WeightedOverlap();

		ADW pipeLine = new ADW();		
		
		//long t = System.currentTimeMillis();
		double similarity = pipeLine.getPairSimilarity(text1, text2,
		                          disMethod, measure,
		                          srcTextType, trgTextType);
		measure = new Cosine();
		double similarity1 = pipeLine.getPairSimilarity(text1, text2,
                disMethod, measure,
                srcTextType, trgTextType);
		//System.out.println((System.currentTimeMillis() - t)/1000);
		if (!outFile.equals("")){ 
			writeToFile(args[2], String.valueOf(similarity + "," + similarity1));
		}
		System.gc();
		System.out.println(similarity + "," + similarity1);
	}

}
