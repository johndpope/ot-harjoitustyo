package roguelike.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import roguelike.domain.Level;

public class FileLevelDao implements LevelDao {
    private String appRootDir;

    public FileLevelDao() {
        this.appRootDir = System.getProperty("user.dir");
    }

    @Override
    public Level loadLevel(int level) {
        try {
            // Load level file from savedata folder
            Scanner reader = new Scanner(new File(this.appRootDir + "/savedata/levels/level" + level + ".txt"));

            ArrayList<char[]> initialLevelData = new ArrayList<>();
            while (reader.hasNextLine()) {
                String line = reader.nextLine();

                // Discard empty lines
                if (line.trim().length() > 0)
                    initialLevelData.add(line.toCharArray());
            }

            reader.close();

            // Empty level file
            if (initialLevelData.size() == 0) return null;

            // Turn data into wanted char array format
            char[][] levelData = new char[initialLevelData.size()][initialLevelData.get(0).length];
            for (int i = 0; i < initialLevelData.size(); i++) {
                levelData[i] = initialLevelData.get(i);
            }

            return new Level(level, levelData);
        } catch (Exception e) {
            System.out.println("Error while reading level \"" + level + "\": " + e.getMessage());

            return null;
        }
    }
}