package fr.joupi.dungeon.utils.file.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.joupi.dungeon.adapter.WarpAdapter;
import fr.joupi.dungeon.common.level.core.Level;
import fr.joupi.dungeon.adapter.LevelAdapter;
import fr.joupi.dungeon.common.warp.core.Warp;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GsonUtils {

    public final Gson gson = buildGson();

    private Gson buildGson() {
        return new GsonBuilder()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .registerTypeAdapter(Warp.class, new WarpAdapter())
                .registerTypeAdapter(Level.class, new LevelAdapter())
                .serializeNulls()
                .create();
    }

}
