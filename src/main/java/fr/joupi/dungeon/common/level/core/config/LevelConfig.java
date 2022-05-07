package fr.joupi.dungeon.common.level.core.config;

import com.google.common.reflect.TypeToken;
import fr.joupi.dungeon.common.level.LevelManager;
import fr.joupi.dungeon.common.level.core.Level;
import fr.joupi.dungeon.utils.file.FileUtils;
import fr.joupi.dungeon.utils.file.json.GsonUtils;
import lombok.Getter;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

@Getter
public class LevelConfig {

    private final LevelManager levelManager;

    public LevelConfig(LevelManager levelManager) {
        this.levelManager = levelManager;
        this.loadLevels();
    }

    public void loadLevels() {
        if (!getLevelFile().exists())
            FileUtils.createFile(getLevelFile());

        try {
            Type listType = new TypeToken<List<Level>>() {}.getType();
            List<Level> levelsList = GsonUtils.gson.fromJson(new FileReader(getLevelFile()), listType);

            levelsList.forEach(getLevelManager().getLevels()::add);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void saveLevels() {
        try {
            Writer writer = new FileWriter(getLevelFile(), false);
            GsonUtils.gson.toJson(getLevelManager().getLevels(), writer);
            writer.flush();
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public File getLevelFile() {
        return new File(getLevelManager().getPlugin().getDataFolder(), "levels.json");
    }

}
