import java.io.*;
import java.util.ArrayList;

// implementation of trie
public class Trie {

    // Alphabet size (# of symbols)
    static final int ALPHABET_SIZE = 26;

    // trie node
    static class TrieNode
    {
        TrieNode[] children = new TrieNode[ALPHABET_SIZE];

        // isEndOfWord is true if the node represents
        // end of a word
        boolean isEndOfWord;

        TrieNode(){
            isEndOfWord = false;
            for (int i = 0; i < ALPHABET_SIZE; i++)
                children[i] = null;
        }
    };

    static TrieNode root;

    // If not present, inserts key into trie
    // If the key is prefix of trie node,
    // just marks leaf node
    public static void insert(String key)
    {
        int level;
        int length = key.length();
        int index;

        TrieNode pCrawl = root;

        for (level = 0; level < length; level++)
        {
            index = key.charAt(level) - 'a';
            if (pCrawl.children[index] == null)
                pCrawl.children[index] = new TrieNode();

            pCrawl = pCrawl.children[index];
        }

        // mark last node as leaf
        pCrawl.isEndOfWord = true;
    }

    // Returns true if key presents in trie, else false
    public static boolean search(String key)
    {
        int level;
        int length = key.length();
        int index;
        TrieNode pCrawl = root;

        for (level = 0; level < length; level++)
        {
            index = key.charAt(level) - 'a';

            if (pCrawl.children[index] == null)
                return false;

            pCrawl = pCrawl.children[index];
        }

        return (pCrawl != null && pCrawl.isEndOfWord);
    }

    // Driver
    public static boolean createDict(String word)
    {
        ArrayList<String> words = new ArrayList<>();

        File file = new File("/Users/karannewatia/Desktop/java_scrabble/forward_dict.txt");

        try {
          FileReader fileReader = new FileReader(file);
          BufferedReader reader = new BufferedReader(fileReader);
         String line = reader.readLine();
         while (line != null) {
            words.add(line);
            // read next line
            line = reader.readLine();
          }
          reader.close();
        } catch (Exception e ) {
            e.printStackTrace();
          }

        root = new TrieNode();

        // Construct trie
        for (int i = 0; i < words.size() ; i++){
            insert(words.get(i));
        }
        return search(word);
        //String output[] = {"Not present in trie", "Present in trie"};

        // if(search("cynoc") == true)
        //     System.out.println("cynoc --- " + output[1]);
        // else System.out.println("cynoc --- " + output[0]);
        //
        // if(search("these") == true)
        //     System.out.println("these --- " + output[1]);
        // else System.out.println("these --- " + output[0]);
        //
        // if(search("their") == true)
        //     System.out.println("their --- " + output[1]);
        // else System.out.println("their --- " + output[0]);
        //
        // if(search("thaw") == true)
        //     System.out.println("thaw --- " + output[1]);
        // else System.out.println("thaw --- " + output[0]);
      }
}
