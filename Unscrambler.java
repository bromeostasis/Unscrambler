import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by esnyder on 6/14/14.
 */
public class Unscrambler {

//    private List<String> dictionary = new ArrayList<String>();
    private HashSet<String> dictionary = new HashSet<String>();
    private List<String> words = new ArrayList<String>();
    private int counter = 0;


    public Unscrambler(){
        intializeDictionary();
    }


    //We need to return a list of permutations at the recursive level, but return a master list of approved words as called from the outside user
    public List<String> unscramble(String input){
        words.clear();
        for (int i = 0; i < input.length(); i++) {
            List<String> matches = unscrambleWithReturn(input.substring(0, i) + input.substring(i+1, input.length()));
            for (int j = 0; j < matches.size(); j++) {
                String potentialWord = input.charAt(i) + matches.get(j);
                if(dictionary.contains(potentialWord)){
                    if(!words.contains(potentialWord))
                        words.add(potentialWord);
                }

            }
        }
        return words;
    }

    private List<String> unscrambleWithReturn(String input){
        if(input.length() < 2) {
            List<String> output = new ArrayList<String>();
            output.add(input);
            return output;
        }
        //Check each letter plus all permutations of remaining letters. For example: if input is cat,
        //we need to check c with permutations of at, a with ct, and t with ac. In this example, c a and t are 'prefixes' while at, ct, and ac are 'newInput'.
        //The returned permutations of these new inputs will be combined with the appropriate prefix and tested
        // We check if the combination is a word as well as if all permutations
        //are words themselves (at would be a word in this case)
        else{
            List<String> oldSuffixes;
            List<String> newSuffixes = new ArrayList<String>();
            for (int i = 0; i < input.length(); i++) {
                Character prefix = input.charAt(i);
                String newInput =input.substring(0, i) + input.substring(i+1, input.length());
                oldSuffixes = unscrambleWithReturn(newInput);
                for (int j = 0; j < oldSuffixes.size(); j++) {
                    String potentialWord = prefix + oldSuffixes.get(j);
                    if(dictionary.contains(potentialWord)){
                        if(!words.contains(potentialWord))
                            words.add(potentialWord);
                    }
                    newSuffixes.add(potentialWord);

                }
            }
            return newSuffixes;
        }


    }

    private void intializeDictionary(){
        URL url = getClass().getResource("words");
        File file = new File(url.getPath());
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String curLine;
            while ((curLine = br.readLine()) != null) {
                dictionary.add(curLine);
            }
            fis.close();
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashSet<String> getDictionary() {
        return dictionary;
    }

    public void setDictionary(HashSet<String> dictionary) {
        this.dictionary = dictionary;
    }

}
