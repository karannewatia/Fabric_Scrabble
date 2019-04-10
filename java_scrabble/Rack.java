import java.util.ArrayList;

/**
 * This class represents a player's rack in the Scrabble game.
 */
public class Rack{

    public ArrayList<Character> tiles;

    public Rack(){
       this.tiles = new ArrayList<>();
    }

    public void populateRackTile(Bag bag){
       for(int i=0; i < 7; i++){
         char temp = bag.getTile();
         this.tiles.add(temp);
       }
    }

    private void swapTile(char tile, Bag bag){
      if(this.tiles.indexOf(tile) != -1 ){
        char newTile = bag.swapTile(tile);
        this.tiles.remove(this.tiles.indexOf(tile));
        this.tiles.add(newTile);
      }

    }

    public void swapTiles(char[] tileList, Bag bag){
      for (int i=0; i<tileList.length ;i++) {
       this.swapTile(tileList[i], bag);
      }
    }

}
