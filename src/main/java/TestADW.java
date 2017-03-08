import it.uniroma1.lcl.adw.ADW;
import it.uniroma1.lcl.adw.DisambiguationMethod;
import it.uniroma1.lcl.adw.ItemType;
import it.uniroma1.lcl.adw.comparison.Cosine;
import it.uniroma1.lcl.adw.comparison.SignatureComparison;
import it.uniroma1.lcl.adw.comparison.WeightedOverlap;

import org.smu.wordsimilarity.WordSimDBFacade;

public class TestADW {
    public static void main(String args[]) {

        // testing ADW

        String text1 = "Hello";
        String text2 = "Hello";
        //types of the two lexical items
        ItemType srcTextType = ItemType.SURFACE;
        ItemType trgTextType = ItemType.SURFACE;

        text1 = "a mill that is powered by the wind";
        text2 = "windmill.n.1";

        trgTextType = ItemType.WORD_SENSE;

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
//        measure = new Cosine();
//        double similarity1 = pipeLine.getPairSimilarity(text1, text2,
//                disMethod, measure,
//                srcTextType, trgTextType);

        System.out.println("Similarity: " + similarity);


        // Testing SEWordSim

        String inputFile = "/home/hebi/data/SEWordSim-r1.db";
        WordSimDBFacade facade = new WordSimDBFacade(inputFile);
        String word = "folder";
        String stem = facade.stemWord(word);
        System.out.println(facade.isInDatabase(stem));
        System.out.println(facade.findMostSimilarWord(stem));
    }
}