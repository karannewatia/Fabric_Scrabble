/*******
 *
 *  A JavaFX reimplementation of Gabriele Cirulli's 2048 game as a demo for
 *  Cornell University CS 2112.
 *
 *  Andrew Myers, March 2014
 */

package game1024;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends Application {

	static Color numColor = new Color(0.4, 0.4, 0.4, 1.0);
	static Color tileColor = new Color(0.8, 0.8, 0.8, 1.0);
	static Color sepColor = new Color(0.6, 0.6, 0.6, 1.0);
	static Color[] colors = {
		tileColor,
		new Color(0.8, 0.8, 0.75, 1.0),
		new Color(0.8, 0.75, 0.75, 1.0),
		new Color(0.85, 0.75, 0.7, 1.0),
		new Color(0.8, 0.8, 0.7, 1.0),
		new Color(0.8, 0.7, 0.7, 1.0),
		new Color(0.8, 0.7, 0.6, 1.0),
		new Color(0.8, 0.6, 0.7, 1.0),
		new Color(0.7, 0.8, 0.7, 1.0),
		new Color(0.7, 0.8, 0.6, 1.0),
		new Color(0.6, 0.7, 0.8, 1.0),
		new Color(0.6, 0.6, 0.8, 1.0),
		new Color(0.8, 0.8, 0.5, 1.0),
		new Color(0.8, 0.7, 0.5, 1.0),
		new Color(0.8, 0.6, 0.5, 1.0),
		new Color(0.8, 0.5, 0.7, 1.0),
		new Color(0.8, 0.5, 0.5, 1.0)
	};
	static Font numFont = Font.font("Helvetica-Bold", FontWeight.BOLD, 64);
	static Font smallNumFont = Font.font("Helvetica-Bold", FontWeight.BOLD, 32);
	static double tileSize = 120.0;
	static double gutter;
	
	// The model of the game.
	private GameModel model;
	// The nodes corresponding to each of the tiles of the game
	private Tile[][] tiles;

	public static void main(String[] args) {
		Application.launch(args);
	}
	
	public void start(Stage stage) {
		model = new GameModel();
		model.addNewTile();
		
		Screen screen = Screen.getPrimary();
		gutter = tileSize/10;
		
		stage.setTitle("1024");
		Group outer = new Group();
		stage.setScene(new Scene(outer));
		Pane content = new Pane();
		outer.getChildren().add(content);
		
		int s = model.size();
		content.setPrefHeight(s * tileSize + (s+1) * gutter);
		content.setPrefWidth(s * tileSize + (s+1) * gutter);
		content.setStyle("-fx-background-color: #999999");

		tiles = new Tile[model.size()][model.size()];
	
		for (int i = 0; i < model.size(); i++) {
			for (int j = 0; j < model.size(); j++) {
				Tile tile = new Tile(i,j); // extra blank tile
				content.getChildren().add(tile);
				tile.setPosn();
				tile.toBack();
				
				tiles[i][j] = new Tile(i, j);
				content.getChildren().add(tiles[i][j]); // j,i
			}			
		}
		resetTilePosns();
		setLabelsFromState();

		outer.requestFocus(); // necessary to get key events
		outer.setOnKeyPressed(new ArrowHandler());
		stage.sizeToScene();
		stage.show();
	}
	
	/** A tile node on the game board. Keeps track of its contained
	 *  background and label.
	 */
	static class Tile extends StackPane {
		public final Text label;
		public final Rectangle background;
		int row, column;
		public Tile(int row, int column) {
			Rectangle r = background = new Rectangle();
			this.row = row;
			this.column = column;
			label = new Text();
			getChildren().add(r);
			getChildren().add(label);
			r.setWidth(tileSize);
			r.setHeight(tileSize);
			r.setFill(tileColor);
			r.setStrokeWidth(0);
			r.setStroke(tileColor);
			r.setArcWidth(tileSize * 0.30);
			r.setArcHeight(tileSize * 0.30);
			label.setFont(numFont);
			label.setFill(numColor);
		}
		public void setPosn() {
			setLayoutX(gutter + column * (tileSize + gutter));
			setLayoutY(gutter + row * (tileSize + gutter));
		}
	}
	
	private void resetTilePosns() {
		for (int i = 0; i < model.size(); i++) {
			for (int j = 0; j < model.size(); j++) {
				tiles[i][j].setPosn();
			}
		}
	}
	
	private void doMove(final int dx, final int dy) {
		// trying to get an animation to work...
		List<GameModel.Move> moves = model.doMove(dx, dy);
		List<KeyValue> move_anims = new ArrayList<KeyValue>();
		List<KeyValue> flash_anims = new ArrayList<KeyValue>();
		for (GameModel.Move m : moves) {
			Tile from_tile = tiles[m.from.row][m.from.col];
			Tile to_tile = tiles[m.to.row][m.to.col];
			from_tile.toFront();
			move_anims.add(new KeyValue(from_tile.layoutXProperty(),
									    to_tile.getLayoutX()));
			move_anims.add(new KeyValue(from_tile.layoutYProperty(),
									    to_tile.getLayoutY()));
			move_anims.add(new KeyValue(from_tile.background.fillProperty(),
									    from_tile.background.getFill()));
			if (m.merge)
				flash_anims.add(new KeyValue(from_tile.background.fillProperty(),
									         ((Color) (from_tile.background.getFill())).interpolate(Color.WHITE, 0.3)));
		}
		
		Timeline timeline = new Timeline(60);
		timeline.getKeyFrames().add(
			new KeyFrame(Duration.seconds(0.2), "end_slide",
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent _) {}
				}, move_anims));
		timeline.getKeyFrames().add(
				new KeyFrame(Duration.seconds(0.3), "end",
						new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent _) {
								setLabelsFromState();
								resetTilePosns();
							}
				}, flash_anims));

		timeline.play();
		return;
	}

	private void setLabelsFromState() {
	for (int r = 0; r < model.size(); r++) {
			for (int c = 0; c < model.size(); c++) {
				int v = model.tile(r,c);
				Text t = tiles[r][c].label;
				if (v == 0) {
					t.setText("");
				} else {
					t.setText(String.valueOf(v));
					t.setFont(v >= 100 ? smallNumFont : numFont);
				}
				int j = 0;
				while (v != 0) {
					v = v/2;
					j++;
				}
				if (j >= colors.length) j = colors.length - 1;
				tiles[r][c].background.setFill(colors[j]);
			}
		}
	}

	/** Handler for arrow keys to trigger moves. */
	class ArrowHandler implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent ev) {
			if (ev.getEventType() == KeyEvent.KEY_PRESSED) {
				switch (ev.getCode()) {
				case LEFT: doMove(-1, 0); break;
				case RIGHT: doMove(1, 0); break;
				case UP: doMove(0, -1); break;
				case DOWN: doMove(0, 1); break;
				default: break;
				}
			}
			ev.consume();
		}
	}
}
