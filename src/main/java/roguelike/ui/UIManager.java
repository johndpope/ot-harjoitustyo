package roguelike.ui;

import java.util.ArrayList;

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
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;

import roguelike.domain.Level;
import roguelike.domain.LevelManager;
import roguelike.domain.Armor;
import roguelike.domain.Weapon;
import roguelike.util.Logger;

public class UIManager {
    private Stage window;
    private LevelManager levelManager;
    private Text[] levelTextData;
    private Timeline delayFunc;

    // Size of characters on screen
    final int charHeight = 17;
    final int charWidth = 12;
    final int nrLogLines = 5;
    final int nrStatsLines = 5;
    final int delayFuncMS = 50;
    final int bottomTextSpace = charHeight * (nrLogLines + 1);
    final int topTextSpace = charHeight * (nrStatsLines + 1);
    final int levelDataStartY = nrStatsLines + 1;

    // Extra padding in the window
    final int windowPadding = 10;

    /**
     * Initializes the UI Manager
     * @param stage A JavaFX stage object to be used as the application window
     */
    public UIManager(Stage stage) {
        this.window = stage;
        UIManager self = this;
        this.levelManager = new LevelManager();
        Logger.nrLines = this.nrLogLines;
        this.setupCustomEventListeners();

        this.window.setTitle("Roguelike");
        this.window.setResizable(false);

        // Delay function for when player moves
        this.delayFunc = new Timeline(new KeyFrame(
            Duration.millis(delayFuncMS),
            ae -> {
                Level l = self.levelManager.getCurrentLevel();

                if (l != null && l.player != null) {
                    l.moveEnemies();
                    self.updateTextData();
                }
            }
        ));
    }

    /**
     * Sets up event listeners for different parts of the app
     */
    private void setupCustomEventListeners() {
        Logger.setOnLog(() -> {
            this.createLogTextData();
            this.updateTextData();
        });

        this.setupStatsUpdateEventListener();

        this.levelManager.onLevelChange(() -> {
            Logger.clearLog();
            this.showCurrentLevel();

            this.setupStatsUpdateEventListener();
        });
    }

    /**
     * Sets up the event listener for stats updates
     */
    private void setupStatsUpdateEventListener() {
        Level l = this.levelManager.getCurrentLevel();
        if (l != null && l.player != null) {
            l.player.setOnStatsUpdate(() -> {
                this.updateStatsTextData();
            });
        }
    }
    
    /**
     * Loads in the current level into the window
     */
    public void showCurrentLevel() {
        this.window.hide();

        Level level = this.levelManager.getCurrentLevel();
        if (level == null || level.player == null) {
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
            grid, level.getWidth() * charWidth - windowPadding,
            level.getHeight() * charHeight - windowPadding + bottomTextSpace + topTextSpace
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
        this.levelTextData = new Text[level.getHeight() + this.nrLogLines + 1 + this.nrStatsLines + 1];

        // Setup text objects with fonts and colors
        for (int i = 0; i < this.levelTextData.length; i++) {
            Text t = new Text();

            if (i <= this.nrStatsLines) {
                t.setText("");
                t.setFill(Color.LIGHTGRAY);
            } else if (i < level.getHeight() + levelDataStartY) {
                t.setText(new String(level.getRow(i - (levelDataStartY))));
                t.setFill(Color.WHITE);
            } else {
                t.setText("");
                t.setFill(Color.LIGHTGRAY);
            }

            t.setY(i * charHeight);
            t.setFont(Font.font("monospace", FontWeight.BOLD, FontPosture.REGULAR, 20));
            
            this.levelTextData[i] = t;
        }

        this.createLogTextData();
        this.updateStatsTextData();
    }

    /**
     * Creates log text data that can be then outputted to the screen
     */
    private void createLogTextData() {
        int idx = this.levelTextData.length - 1;

        // Update log messages
        ArrayList<String> logMessages = Logger.messages;
        for (int i = 0; i < logMessages.size(); i++) {
            this.levelTextData[idx--].setText(logMessages.get(i));
        }
    }

    /**
     * Updates the player's stats text data at the top of the screen
     */
    private void updateStatsTextData() {
        Level level = this.levelManager.getCurrentLevel();
        if (level == null || level.player == null) {
            return;
        }

        Weapon w = level.player.weapon;
        Armor a = level.player.armor;

        String hp = Integer.toString(level.player.health);
        String dmg = Integer.toString((w == null) ? level.player.damage : (level.player.damage + w.damage));
        String armor = Integer.toString((a == null) ? 0 : a.armor);
        String bombs = Integer.toString(level.player.bombs.size());

        this.levelTextData[1].setText("Health: " + hp + " | Damage: " + dmg + " | Armor: " + armor + " | Bombs: " + bombs);
        this.levelTextData[3].setText("Weapon: " + ((w == null) ? "none" : w.name + " (" + w.damage + " damage)"));
        this.levelTextData[4].setText("Armor: " + ((a == null) ? "none" : a.name + " (" + a.armor + " armor)"));
    }

    /**
     * Updates the text in the current scene
     */
    private void updateTextData() {
        Level level = this.levelManager.getCurrentLevel();
        if (level == null) {
            return;
        }

        for (int i = 0; i < level.getHeight(); i++) {
            this.levelTextData[i + levelDataStartY].setText(new String(level.getRow(i)));
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
            movePlayer(0, -1);
        } else if (keyCode == KeyCode.RIGHT) {
            movePlayer(0, 1);
        } else if (keyCode == KeyCode.UP) {
            movePlayer(-1, 0);
        } else if (keyCode == KeyCode.DOWN) {
            movePlayer(1, 0);
        } else if (keyCode == KeyCode.SPACE) {
            useBomb();
        }

        this.updateTextData();
    }

    /**
     * Moves the player by a specified amount
     * @param yDiff Amount the player should be moved in the y direction
     * @param xDiff Amount the player should be moved in the x direction
     */
    private void movePlayer(int yDiff, int xDiff) {
        this.checkForGameEnd();

        if (this.levelManager.movePlayer(yDiff, xDiff)) {
            this.delayFunc.play();
        }
    }

    /**
     * Uses a bomb on the player
     */
    private void useBomb() {
        this.checkForGameEnd();

        if (this.levelManager.useBomb()) {
            this.delayFunc.play();
        }
    }

    /**
     * Checks if the game has ended and closes the window if it has
     */
    private void checkForGameEnd() {
        if (this.levelManager.getCurrentLevel().player == null) {
            this.window.close();
            return;
        }
    }
}