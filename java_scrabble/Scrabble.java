import java.util.*;
import java.io.*;
import java.lang.*;

/**
 * The class Scrabble is responsible for coordinating gameplay
 * between two Players.
 */
public class Scrabble {

  private Player[] players;
  private Bag bag;
  private int currentTurn;
  private Board board;

  public Scrabble(){
    this.players = new Player[2];
    this.bag = new Bag();
    this.currentTurn = 0;
    this.board = new Board();
  }

  public void joinGame(){
    Player alice = new Player();
    Player bob = new Player();
    this.players[0] = alice;
    this.players[1] = bob;
  }

  public void startGame(){
    this.bag.populateBag();
    for(int i=0; i<this.players.length;i++){
      this.players[i].rack.populateRackTile(this.bag);
    }
  }

  public char[] stringToCharArray(String word) {
		return word.toCharArray();
	}

  public void gameLoop(){
    int currentPlayer = 0;
    while(this.board.moveNumber < 50 && this.bag.tiles.size() > 0){
      System.out.print("  ");//beginning 2 spaces
      for(int i = 0; i < this.board.gameBoard.length; ++i){
        if(i>9) System.out.print(" " + (i) +" ");//print letters seperately.
        else System.out.print(" " + (i) +"  ");//print letters seperately.
      }

      for(int i = 0; i < this.board.gameBoard.length; i++){
      System.out.println();
      for(int j = 0; j < this.board.gameBoard.length; j++){
          if(j == 0){
              if(i>9) {
              System.out.print((i));
              System.out.print("| ");
            }
            else {
              System.out.print(" "+ (i));
              System.out.print("| ");
            }
          }
          System.out.print(this.board.gameBoard[i][j] + " | ");
        }
     }
     System.out.printf("%n");
     try{
     BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
     System.out.println("Player " + currentPlayer + "'s turn \n");
     System.out.println("Score: " + this.players[currentPlayer].score + "\n");
     System.out.println("Rack: " + this.players[currentPlayer].getRack() + "\n");

     boolean success = false;
     while(!success) {
       System.out.println("place or swap or pass \n");
       String moveType = reader.readLine();
       
       if (moveType.equals("swap")){
          System.out.println("which letters do you want to swap \n");
          String word = reader.readLine();
          char[] letters = this.stringToCharArray(word);
          this.players[currentPlayer].rack.swapTiles(letters, this.bag);
          currentPlayer = (currentPlayer + 1) % 2;
          success = true;
          continue;
       }

       else if (moveType.equals("place")){
         boolean success = false;
         while(!success)
           System.out.println(" word formed \n");
           String word = reader.readLine();
           System.out.println(" h or v \n");
           String dirString = reader.readLine();
           boolean dir = true;
           if (dirString.equals("v")){
             dir = false;
           }
           System.out.println(" startRow \n");
           String rowString = reader.readLine();
           System.out.println(" startCol \n");
           String colString = reader.readLine();
           int row = Integer.parseInt(rowString);
           int col = Integer.parseInt(colString);

           String newEntries = this.board.getNewEntries(word, dir, row, col);
           if (this.board.isMoveValid(this.players[currentPlayer], word, newEntries, dir, row, col)){
             this.board.placeWord(this.players[currentPlayer], word, dir, row, col);
             this.board.incScore(this.players[currentPlayer], newEntries.toCharArray());
             this.players[currentPlayer].rack.swapTiles(newEntries.toCharArray(), this.bag);
             currentPlayer = (currentPlayer + 1) % 2;
             this.board.moveNumber++;
             success = true;
             continue;
           }

           else {
           System.out.println("invalid place");
         }

       }

       else if (moveType.equals("pass")){
         success= true;
         System.out.println("turn passed");
       }

       else{
         System.out.println("try again");
       }

     }

     //System.out.println(moveType);
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    currentPlayer = (currentPlayer + 1) % 2;
    }
  }

}
