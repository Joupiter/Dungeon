package fr.joupi.dungeon.common.warp.core.config;

import com.google.common.reflect.TypeToken;
import fr.joupi.dungeon.common.warp.WarpManager;
import fr.joupi.dungeon.common.warp.core.Warp;
import fr.joupi.dungeon.utils.file.FileUtils;
import fr.joupi.dungeon.utils.file.json.GsonUtils;
import lombok.Getter;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

@Getter
public class WarpConfig {

    private final WarpManager warpManager;

    public WarpConfig(WarpManager warpManager) {
        this.warpManager = warpManager;
        this.loadWarps();
    }

    public void loadWarps() {
        if (!getWarpFile().exists())
            FileUtils.createFile(getWarpFile());

        try {
            Type listType = new TypeToken<List<Warp>>() {}.getType();
            List<Warp> warpsList = GsonUtils.gson.fromJson(new FileReader(getWarpFile()), listType);

            warpsList.forEach(getWarpManager().getWarps()::add);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void saveWarps() {
        try {
            Writer writer = new FileWriter(getWarpFile(), false);
            GsonUtils.gson.toJson(getWarpManager().getWarps(), writer);
            writer.flush();
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public File getWarpFile() {
        return new File(getWarpManager().getPlugin().getDataFolder(), "warps.json");
    }


}
