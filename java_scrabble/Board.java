import java.util.*;
import java.lang.*;
import java.io.*;

/**
 * This class represents a board in the Scrabble game.
 *.
 */
class Board {
    public char[][] gameBoard;
    public int moveNumber;
    public HashMap<Character, Integer> points;

    public Board() {
      this.moveNumber = 0;
      this.gameBoard = new char[15][15];
      this.points = new HashMap<Character, Integer>();
        for (int i=0; i<15; i++) {
            for (int j=0; j<15; j++) {
                this.gameBoard[i][j] = ' ';
            }
        }
      this.setPoints();

    }

    public void setPoints(){
      this.points.put('a', 1);
      this.points.put('e', 1);
      this.points.put('i', 1);
      this.points.put('l', 1);
      this.points.put('n', 1);
      this.points.put('o', 1);
      this.points.put('r', 1);
      this.points.put('s', 1);
      this.points.put('t', 1);
      this.points.put('u', 1);

      this.points.put('d', 2);
      this.points.put('g', 2);

      this.points.put('b', 3);
      this.points.put('c', 3);
      this.points.put('m', 3);
      this.points.put('p', 3);

      this.points.put('f', 4);
      this.points.put('h', 4);
      this.points.put('v', 4);
      this.points.put('w', 4);
      this.points.put('y', 4);

      this.points.put('k', 5);

      this.points.put('j', 8);
      this.points.put('x', 8);

      this.points.put('q', 10);
      this.points.put('z', 10);

      this.points.put('*', 0);
    }

    private boolean validateWord(String word){
      return Trie.createDict(word);
    }

    public char getTileOnBoard(int row, int col) {
      if (row<=0) row = 0;
      if (col<=0) col = 0;
      if (row>=14) row = 14;
      if (row>=14) col = 14;
      return this.gameBoard[row][col];
    }



    private String constructWord(int row, int col, char letter, boolean direction) {
        StringBuilder newWord = new StringBuilder();
        int start;
        int end;
        int i = 1;
        int j = 1;

        if (direction) {
            while (col-j >= 0 && this.getTileOnBoard(row, col-j) != ' ') {
                j++;
            }
            start = col-j+1;
            j = 1;

            while (col+j <= 14 && this.getTileOnBoard(row, col+j) != ' ') {
                j++;
            }
            end = col+j-1;

            for (int newCol=start; newCol<=end;  newCol++) {
                if (newCol == col) {    newWord.append(letter);                  }
                else {  newWord.append(this.getTileOnBoard(row, newCol));  }
            }

            return newWord.toString().trim();

        } else {

            while (row-i >= 0 && this.getTileOnBoard(row-i, col) != ' ') {
                i++;
            }
            start = row-i+1;
            i = 1;

            while (row+i <= 14 && this.getTileOnBoard(row+i, col) != ' ') {
                i++;
            }
            end = row+i-1;

            for (int newRow = start; newRow <= end; newRow++) {
                if (newRow == row) {    newWord.append(letter);                  }
                else {  newWord.append(this.getTileOnBoard(newRow, col));  }
            }

            return newWord.toString().trim();
        }
    }

    public ArrayList<String> getAdjWords(String word,boolean dir, int row, int col) {
        ArrayList<String> adjWords = new ArrayList<String>();

        for (int i=0; i<word.length(); i++) {
            if (dir) {
                if (this.getTileOnBoard(row, col+i) == ' ') {

                    if (this.getTileOnBoard(row-1, col+i) != ' ' ||
                            this.getTileOnBoard(row+1, col+i) != ' ') {
                        adjWords.add(this.constructWord(row, col+i ,word.charAt(i), dir));
                    }
                }
                for(int j =0; j<word.length();j++){
                  if (this.getTileOnBoard(row-1,col+j) != ' '){
                    adjWords.add(this.constructWord(row-1, col+j ,this.getTileOnBoard(row-1, col +j), !dir));
                  }
                  if (this.getTileOnBoard(row+1,col+j) != ' '){
                    adjWords.add(this.constructWord(row+1, col+j ,this.getTileOnBoard(row+1, col +j), !dir));
                  }
                }
            } else {
                if (this.getTileOnBoard(row+i, col) == ' ') {
                    if (this.getTileOnBoard(row+i, col-1) != ' ' ||
                            this.getTileOnBoard(row+i, col+1) != ' ') {
                        adjWords.add(this.constructWord(row+i, col, word.charAt(i), dir));
                    }
                }
                for(int j =0; j<word.length();j++){
                  if (this.getTileOnBoard(row+j,col-1) != ' '){
                    adjWords.add(this.constructWord(row+j, col-1 ,this.getTileOnBoard(row+j, col -1), !dir));
                  }
                  if (this.getTileOnBoard(row+j,col+1) != ' '){
                    adjWords.add(this.constructWord(row+j, col+1 ,this.getTileOnBoard(row+j, col+1), !dir));
                  }
                }
            }
        }

        return adjWords;
    }

    private boolean validateAdjWords(ArrayList<String> list) {
      for (String str: list) {
          if (!this.validateWord(str)) {
              System.out.println(str + " not valid secondary word.");
              return false;
          }
       }
       return true;
    }

    private boolean tileHelper(String word, String rackCopy){
      if (word.length() == 0 ) return true;

      if(rackCopy.indexOf(word.charAt(0)) !=-1){
        int idx = rackCopy.indexOf(word.charAt(0));
        String newRack = rackCopy.substring(0, idx) + rackCopy.substring(idx + 1);
        String newWord = word.substring(1);
        return tileHelper(newWord, newRack);
      }

      else if (rackCopy.indexOf("*") !=-1){
        String newRack = rackCopy.substring(0, rackCopy.indexOf("*")) + rackCopy.substring(rackCopy.indexOf("*") + 1);
        String newWord = word.substring(1);
        return tileHelper(newWord, newRack);
      }
      else return false;
    }

    private boolean hasTilesinRack(Player player, String word) {

      String rackCopy = player.getRack();
      if (rackCopy.length() < word.length() ) return false;
      return tileHelper(word, rackCopy);
    }

    public boolean isMoveValid(Player player, String word, String newEntries, boolean dir, int row, int col) {

        String rackCopy = player.getRack();
        if (!hasTilesinRack(player, newEntries)){
          System.out.println("isMoveValid err: : Letters not in rack. Invalid move!");
          return false;
        }

        boolean tilesPresent = false;

        // DOES THE BOARD OVERFLOW
        if ((dir&& (col + word.length() > 14)) ||
                (!dir && (row + word.length() > 14))) {
            System.out.println("isMoveValid err: : Board overflow. Invalid move!");
            return false;
        }

        // IS THE WORD VALID USING A DICT
        if (!this.validateWord(word)) {
            System.out.println("isMoveValid err: This word doesn't exist.");
            return false;
        }

        // INTENDED WORD SHOULD BE THE LARGEST CONTIGUOUS STRING IN THAT DIRECTION
        if (dir) {
            if (this.getTileOnBoard(row, col+word.length()+1) != ' ' ||
                    (col-1>0 && this.getTileOnBoard(row, col-1) != ' ')) {
                System.out.println("isMoveValid err: Incomplete input word ");
                return false;
            }
        } else {
            if (this.getTileOnBoard(row+word.length()+1, col) != ' ' ||
                    (row-1>0 && this.getTileOnBoard(row-1, col) != ' ')) {
                System.out.println("Incomplete input word");
                return false;
            }
        }

        // DOES THE FIRST MOVE CROSS THE BOARD CENTER
        if (this.moveNumber == 0) {
            if ((dir&& (col + word.length() < 7)) ||
                    (!dir && (row + word.length() < 7)) ||
                    (row > 7 && col > 7)) {
                System.out.println("isMoveValid err: First move should touch the board center!");
                return false;
            }
        }

        // DOES THE SECOND (or greater) MOVE TOUCH ONE OF THE EXISTING TILES
        if (this.moveNumber >= 1) {
            for (int i=0; i<word.length(); i++) {
                if (dir) {
                    if (this.getTileOnBoard(row, col+i) != ' ')    {   tilesPresent = true;    }
                } else {
                    if (this.getTileOnBoard(row+i, col) != ' ')    {   tilesPresent = true;    }
                }
            }
            if (!tilesPresent)  {
                System.out.println("isMoveValid err: New word has to touch an existing word");
                return false;
            }
        }

        // ARE SECONDARY WORDS VALID IF THEY EXIST
        ArrayList<String> adjList = this.getAdjWords(word, dir, row, col);
        if (!this.validateAdjWords(adjList)) {
            System.out.println("isMoveValid err: The other words you tried to create are invalid. Sorry!");
            return false;
        }

        return true;
    }

    public String getNewEntries(String word, boolean isHorizontal, int row, int col) {
      ArrayList<Character> newTiles = new ArrayList<>();
      char[] letters = word.toCharArray();
      if (isHorizontal){
        for(int i=0; i< letters.length; i++){
          if(this.getTileOnBoard(row, i + col) == letters[i]){
            continue;
          }
          else if (this.getTileOnBoard(row, i + col) != ' '){
            newTiles.add('#');
          }
          else {
            newTiles.add(letters[i]);
          }
        }
      }
      else{
        for(int j=0; j< letters.length; j++){
          if(this.getTileOnBoard(row + j, col) == letters[j]){
            continue;
          }
          else if (this.getTileOnBoard(row + j, col) != ' '){
            newTiles.add('#');
          }
          else {
            newTiles.add(letters[j]);
          }
        }
      }
      StringBuilder builder = new StringBuilder(newTiles.size());
      for(Character ch: newTiles)
      {
         builder.append(ch);
      }
      return builder.toString();
    }

    public void placeWord(Player player, String word, boolean isHorizontal, int row, int col){
       char[] letters = word.toCharArray();
       if (isHorizontal){
         for (int i =0; i< letters.length; i++){
           this.gameBoard[row][col + i] = letters[i];
         }
       }
       else{
         for (int j =0; j< letters.length; j++){
           this.gameBoard[row + j][col] = letters[j];
         }
       }
    }

    public void incScore(Player player, char[] tiles){
      for(int i =0; i< tiles.length; i++){
        player.score += this.points.get(tiles[i]);
      }
    }
}
