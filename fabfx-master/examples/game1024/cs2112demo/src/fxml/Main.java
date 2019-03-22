package fxml;

import java.io.IOException;
import java.net.URL;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

// a small example of reading a scene layout from an XML file instead of
// creating all the objects.
public class Main extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }
    String message = "Button was clicked";
    public void start(Stage stage) throws Exception {
        try {
            URL r = getClass().getResource("simple.fxml");
            if (r == null) {
                System.out.println("No FXML resource found.");
                try {stop();} catch (Exception _) {}
                return;
                
            }
            Parent node = FXMLLoader.load(r);
            Scene scene = new Scene(node);
            stage.setTitle("FXML demo");
            stage.setScene(scene);
            stage.sizeToScene();
           
            // Handling events
            final Button b = (Button) scene.lookup("#pressme");
            b.setOnAction(new PrintIt());
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent _) {
                    System.out.println(message);
                    Timeline tl = new Timeline();
                    tl.getKeyFrames().add(new KeyFrame(
                    		Duration.millis(500),
                    		"move",
                    		new KeyValue(b.layoutYProperty(), b.getLayoutY() + 10.0)));
                    tl.play();
                }
            });
            // Watching for property changes.          
            TextArea t = (TextArea) scene.lookup("#typeme");
            t.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> obs,
                        String before, String after) {
                    System.out.println("Changed from \"" + before + "\" to \""
                            + after + "\"");
                }
            });
            
            stage.show();
        
        } catch (Exception e) {
            System.out.println("Can't load FXML file.");
            e.printStackTrace();
            try { stop(); } catch (Exception _) {}
        }
    
    }
    class PrintIt implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent _) {
            System.out.println(message);
        }
    }
}
