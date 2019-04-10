package fabric.util;
import java.io.*;

import fabric.lang.security.Label;

public class Dictionary {


	protected final Label L;
	private String[] words;

	public Dictionary(Label L) {
		this.L = L;
		words = new String[8178];
	}

	public String[] getDict() {
	  	File file = new File("/Users/karannewatia/Documents/GitHub/Fabric_Scrabble/fabric_scrabble/src/simple_dict.txt");
			int count = 0;
        try {
          FileReader fileReader = new FileReader(file);
          BufferedReader reader = new BufferedReader(fileReader);
          String line = reader.readLine();
          while (line != null) {
            words[count] = line;
						count = count + 1;
            // read next line
            line = reader.readLine();
          }
          reader.close();
        } catch (Exception e ) {
            e.printStackTrace();
          }
		 return words;
	}

}
