package fr.joupi.dungeon.utils.handler;

import fr.joupi.dungeon.DungeonPlugin;
import fr.joupi.dungeon.common.dungeon.DungeonManager;
import fr.joupi.dungeon.common.level.LevelManager;
import fr.joupi.dungeon.common.profile.ProfileManager;
import fr.joupi.dungeon.common.region.RegionManager;
import fr.joupi.dungeon.common.warp.WarpManager;
import lombok.Data;
import org.bukkit.event.Listener;

@Data
public abstract class AbstractListener<P extends DungeonPlugin> implements Listener {

    private final P plugin;

    public ProfileManager getProfileManager() {
        return getPlugin().get().getProfileManager();
    }

    public LevelManager getLevelManager() {
        return getPlugin().get().getLevelManager();
    }

    public WarpManager getWarpManager() {
        return getPlugin().get().getWarpManager();
    }

    public RegionManager getRegionManager() {
        return getPlugin().get().getRegionManager();
    }

    public DungeonManager dungeonManager() {
        return getPlugin().get().getDungeonManager();
    }

}
