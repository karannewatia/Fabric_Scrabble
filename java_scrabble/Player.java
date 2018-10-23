/**
 * This class represents a player in the Scrabble game.
 */
class Player {
    /**
     * This rack represents P's rack.
     * It's fine to make the reference to the rack public, as all the
     * information contained in the board is readable only by P
     */
    public Rack rack;
    public int score;

    public Player(){
      this.rack = new Rack();
      this.score = 0;
    }

     public String getRack() {
       StringBuilder builder = new StringBuilder(this.rack.tiles.size());
       for(Character ch: this.rack.tiles)
       {
          builder.append(ch);
       }
       return builder.toString();
      }

}
