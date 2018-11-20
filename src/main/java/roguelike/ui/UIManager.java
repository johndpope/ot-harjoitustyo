package roguelike.ui;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import roguelike.domain.Level;
import roguelike.domain.LevelManager;

public class UIManager {
    private Stage window;
    private LevelManager levelManager;
    private Text[] levelTextData;

    // Size of characters on screen
    final int CHAR_HEIGHT = 17;
    final int CHAR_WIDTH = 11;

    // Extra padding in the window
    final int WINDOW_PADDING = 10;

    /**
     * Initializes the UI Manager
     * @param stage A JavaFX stage object to be used as the application window
     */
    public UIManager(Stage stage) {
        this.window = stage;
        this.levelManager = new LevelManager();

        this.window.setTitle("Roguelike");
        this.window.setResizable(false);
    }
    
    /**
     * Loads in the current level into the window
     */
    public void showCurrentLevel() {
        this.window.hide();

        Level level = this.levelManager.getCurrentLevel();
        if (level == null) {
            this.window.show();
            return;
        }

        // Store the level in a Text object array so it can easily be modified
        this.levelTextData = new Text[level.levelData.length];

        // Setup text objects with fonts and colors
        for (int i = 0; i < level.levelData.length; i++) {
            Text t = new Text();
            t.setText(new String(level.levelData[i]));
            t.setFill(Color.WHITE);
            t.setY(i * CHAR_HEIGHT);
            t.setFont(Font.font("consolas", FontWeight.BOLD, FontPosture.REGULAR, 20));
            this.levelTextData[i] = t;
        }

        // Create containers for text
        GridPane grid = new GridPane();
        Group group = new Group(this.levelTextData);
        grid.add(group, 0, 0, 3, 3);

        // Create scene and set its size to fit the level
        Scene scene = new Scene(
            grid, level.levelData[0].length * CHAR_WIDTH - WINDOW_PADDING,
            level.levelData.length * CHAR_HEIGHT - WINDOW_PADDING
        );

        scene.setFill(Color.BLACK);

        // Event handlers
        this.setupEvents(scene);

        this.window.setScene(scene);
        this.window.show();
    }

    /**
     * Updates the text in the current scene
     */
    private void updateTextData() {
        Level level = this.levelManager.getCurrentLevel();
        if (level == null) return;

        for (int i = 0; i < level.levelData.length; i++) {
            this.levelTextData[i].setText(new String(level.levelData[i]));
        }
    }

    /**
     * Sets up event handlers for a scene
     * @param scene Scene that the event handlers should be bound to
     */
    private void setupEvents(Scene scene) {
        UIManager self = this;

        // Setup arrow key events
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.LEFT) {
                    self.levelManager.movePlayer(-1, 0);
                } else if (e.getCode() == KeyCode.RIGHT) {
                    self.levelManager.movePlayer(1, 0);
                } else if (e.getCode() == KeyCode.UP) {
                    self.levelManager.movePlayer(0, -1);
                } else if (e.getCode() == KeyCode.DOWN) {
                    self.levelManager.movePlayer(0, 1);
                } else if (e.getCode() == KeyCode.DIGIT1) {
                    self.levelManager.changeLevel(1);
                    self.showCurrentLevel();
                } else if (e.getCode() == KeyCode.DIGIT2) {
                    self.levelManager.changeLevel(2);
                    self.showCurrentLevel();
                }

                self.updateTextData();
            }
        });
    }
}