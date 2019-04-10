/**
 * This class represents an (x,y) coordinate in the space of the
 * Scrabble board. x and y should be non-negative integers.
 * The co-ordinate (0, 0) is the lower left of the board.
 */
class Coordinate {
    /**
     * The x coord.
     */
    public final x;
    /**
     * The y coord.
     */
    public final y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int hashCode() {
        return (13 * this.x) ^ (-23 * this.y);
    }

    // public boolean equals(lCoordinate , IDComparable[lbl] o) {

    //    Coordinate[lbl] that = (Coordinate[lbl])o;
    //    return this.x == that.x && this.y == that.y;
    //}

}
