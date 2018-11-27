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
    final int charHeight = 17;
    final int charWidth = 11;

    // Extra padding in the window
    final int windowPadding = 10;

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

        this.createTextDataForLevel(level);

        // Create containers for text
        GridPane grid = new GridPane();
        Group group = new Group(this.levelTextData);
        grid.add(group, 0, 0, 3, 3);

        // Create scene and set its size to fit the level
        Scene scene = new Scene(
            grid, level.levelData[0].length * charWidth - windowPadding,
            level.levelData.length * charHeight - windowPadding
        );

        scene.setFill(Color.BLACK);

        // Event handlers
        this.setupEvents(scene);

        this.window.setScene(scene);
        this.window.show();
    }

    /**
     * Creates text data for a level that can be then outputted to the screen
     * @param level The number of the level that the data should be created for
     */
    private void createTextDataForLevel(Level level) {
        // Store the level in a Text object array so it can easily be modified
        this.levelTextData = new Text[level.levelData.length];

        // Setup text objects with fonts and colors
        for (int i = 0; i < level.levelData.length; i++) {
            Text t = new Text();
            t.setText(new String(level.levelData[i]));
            t.setFill(Color.WHITE);
            t.setY(i * charHeight);
            t.setFont(Font.font("consolas", FontWeight.BOLD, FontPosture.REGULAR, 20));
            this.levelTextData[i] = t;
        }
    }

    /**
     * Updates the text in the current scene
     */
    private void updateTextData() {
        Level level = this.levelManager.getCurrentLevel();
        if (level == null) {
            return;
        }

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
                self.onKeyPress(e.getCode());
            }
        });
    }

    /**
     * Handles a key press
     * @param keyCode Code of the key that was pressed
     */
    private void onKeyPress(KeyCode keyCode) {
        if (keyCode == KeyCode.LEFT) {
            this.levelManager.movePlayer(-1, 0);
        } else if (keyCode == KeyCode.RIGHT) {
            this.levelManager.movePlayer(1, 0);
        } else if (keyCode == KeyCode.UP) {
            this.levelManager.movePlayer(0, -1);
        } else if (keyCode == KeyCode.DOWN) {
            this.levelManager.movePlayer(0, 1);
        } else if (keyCode == KeyCode.DIGIT1) {
            this.levelManager.changeLevel(1);
            this.showCurrentLevel();
        } else if (keyCode == KeyCode.DIGIT2) {
            this.levelManager.changeLevel(2);
            this.showCurrentLevel();
        }

        this.updateTextData();
    }
}