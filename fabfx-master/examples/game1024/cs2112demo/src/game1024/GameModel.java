package game1024;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// A (graphics-free) model of the '1024' game.
public class GameModel {
	/** The size of the square of tiles. */
	private int size = 4;
	/** The tile values in the game. A blank tile is represented by zero. */
	private int[][] tiles = new int[size][size];
	/** Used for randomness. */
	private Random random = new Random();
	
	/** A location in the game. */
	public class Location {
		final int row;
		final int col;
		Location(int r, int c) {
			row = r;
			col = c;
		}
		/** Create a location for (x,y) in the coordinate system where
		 *  (dx, dy) is to the right.
		 */
		Location(int x, int y, int dx, int dy) {
			switch (dx * 2 + dy) {
			case 2:	row = y; col = x; break;
			case -2: row = y; col = size-1-x; break;
			case 1: row = x; col = y; break;
			case -1: row = size-1-x; col = y; break;
			default: throw new Error("Illegal location");
			}
		}
	}
	/** A move made by a tile. */
	public class Move {
		public final Location from, to;
		public boolean merge;
		public Move(Location f, Location t, boolean mrg) {
			from = f; to = t; merge = mrg;
		}
	}
	/** Get the tile at location loc. */
	public int tile(Location loc) {
		return tiles[loc.row][loc.col];
	}	
	/** Get the tile at row r, column c. */
	public int tile(int r, int c) {
		return tiles[r][c];
	}
	/** Get tile (x,y) in the coordinate system with (dx,dy) going to the right. */
	private int rtile(int x, int y, int dx, int dy) {
		return tile(new Location(x,y,dx,dy));
	}
	/** Set tile (x,y) in the coordinate system with (dx,dy) going to the right. */
	private void setrtile(int x, int y, int dx, int dy, int v) {
		Location loc = new Location(x,y,dx,dy);
		tiles[loc.row][loc.col] = v;
	}
	public int size() { return size; }
	
	/** Add a 1 tile in a random blank space, and return true.
	 *  Return false if there is no blank space on the board.
	 *  (Game Over)
	 */
	public boolean addNewTile() {
		int numFree = 0;
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				if (tiles[r][c] == 0) numFree++;
			}
		}
		if (numFree == 0) return false;
		int i = random.nextInt(numFree);
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				if (tiles[r][c] == 0 && i-- == 0) {
					tiles[r][c] = (random.nextInt(10) == 0) ? 2 : 1;
					return true;
				}
			}
		}
		throw new Error("Can't get here");		
	}

	/** Try to move the puzzle in the specified direction.
	 *  Return a list of moves performed. An empty list returned
	 *  means nothing could move.
	 */
	public List<Move> slideTiles(int dx, int dy) {
		List<Move> ret = new ArrayList<Move>();
		// only one merge can happen at a given location per turn.
		// fixed keeps track of the locations that have already merged.
		boolean[][] fixed = new boolean[size][size];
		for (int x = size - 2; x >= 0; x--) {
			for (int y = 0; y < size; y++) {
				int v = rtile(x, y, dx, dy);
				if (v != 0) {					
					int x1 = x + 1;
					assert x1 < size;
					while (x1 < size && rtile(x1, y, dx, dy) == 0)
						x1++;
					if (x1 == size) {
						// move all the way to the edge
						setrtile(x, y, dx, dy, 0);
						setrtile(size-1, y, dx, dy, v);
						ret.add(new Move(new Location(x, y, dx, dy),
										 new Location(size-1, y, dx, dy), false));
					} else if (rtile(x1, y, dx, dy) == v &&
							!fixed[x1][y]) {
						// slide and merge
						setrtile(x, y, dx, dy, 0);
						setrtile(x1, y, dx, dy, v * 2);
						fixed[x1][y] = true; // can't merge again
						ret.add(new Move(new Location(x,y,dx,dy),
										 new Location(x1, y, dx, dy), true));
					} else if (x != x1 - 1) {
						// slide up to the next tile but don't merge
						setrtile(x, y, dx, dy, 0);
						setrtile(x1 - 1, y, dx, dy, v);
						ret.add(new Move(new Location(x, y, dx, dy),
										 new Location(x1-1, y, dx, dy), false));
					}
				}
			}
		}
		return ret;
	}
	
	/** Try to do a player move in direction (dx, dy), returning a
	 * list of tiles moves that resulted, or an empty list if the move
	 * is not legal.
	 */
	public List<Move> doMove(int dx, int dy) {
		List<GameModel.Move> moves = slideTiles(dx, dy);
		if (!moves.isEmpty()) addNewTile();
		return moves;
	}
}
