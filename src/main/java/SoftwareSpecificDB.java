import java.util.ArrayList;

import org.smu.wordsimilarity.WordSimDBFacade;

import com.google.common.base.Joiner;

public class SoftwareSpecificDB {
	public static void main(String args[]) {
		//read in database
		String inputFile = "/home/hebi/data/SEWordSim-r1.db";
		WordSimDBFacade facade = new WordSimDBFacade(inputFile);
		
		//stem word
		String inputWord = "number";
		String stemmedInputWord = facade.stemWord(inputWord);
		String comparedWord = "number";
		String stemmedComparedWord = facade.stemWord(comparedWord);
		
		//set parameters
		double minSimilarityScore = 0.5;
		int N = 10;
		
		//search and print result
		System.out.print(facade.isInDatabase(stemmedInputWord) + "\n");
		System.out.print(facade.computeSimilarity(stemmedInputWord, stemmedComparedWord) + "\n");
		System.out.print(facade.findMostSimilarWord(stemmedInputWord) + "\n");
		System.out.print(facade.findMostSimilarWords(stemmedInputWord, minSimilarityScore) + "\n");
		System.out.print(facade.findTopNWords(stemmedInputWord, N) + "\n");
		System.out.print(facade.getAllWords());
	}
	
	public static String queryExpansion(String query){
		int N = 2;
		double minSimilarityScore = 0.4;
		
		//read in database
		String inputFile = "/home/hebi/data/SEWordSim-r1.db";
		WordSimDBFacade facade = new WordSimDBFacade(inputFile);
		String[] spited = query.split(" ");
		ArrayList<String> stemmed = new ArrayList<String>();
		for (String word : spited) {
			String stem = facade.stemWord(word);
			stemmed.add(word);
			try{
				stemmed.addAll(facade.findTopNWords(stem, N) );
			}
			catch(Exception ignored){}
		}
		String retVal = Joiner.on(" ").join(stemmed);
		System.out.println(retVal);
		return retVal;
	}
}