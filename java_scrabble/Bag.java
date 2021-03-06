import java.util.Random;
import java.util.ArrayList;

/**
 * This class represents the bag in the Scrabble game.
 */
public class Bag {

    public ArrayList<Character> tiles;

    private final int counts[] = {2,9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1};

    public Bag(){
        this.tiles = new ArrayList<>();
    }

    public void populateBag(){
       for(int i=0; i < counts.length; i++){
           for(int j=0; j < counts[i]; j++){
            char temp;
            temp = i == 0 ? '*' : (char)('a' + i - 1);
            this.tiles.add(temp);
           }
       }
    }

    public char getTile(){
       Random rand = new Random();
       int rand_value = rand.nextInt(this.tiles.size());
       char tile = this.tiles.get(rand_value);
       this.tiles.remove(rand_value);
       return tile;
    }

    public char swapTile(char tile){
      char newTile = this.getTile();
      this.tiles.add(tile);
      return newTile;
    }

}
