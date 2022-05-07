package fr.joupi.dungeon.utils.visual.scoreboard;

import fr.joupi.dungeon.DungeonPlugin;
import fr.joupi.dungeon.common.dungeon.DungeonManager;
import fr.joupi.dungeon.common.level.LevelManager;
import fr.joupi.dungeon.common.profile.ProfileManager;
import fr.joupi.dungeon.common.region.RegionManager;
import lombok.Data;

@Data
public abstract class AbstractBoard<P extends DungeonPlugin> implements DBoard {

    private final P plugin;

    public ProfileManager getProfileManager() {
        return getPlugin().get().getProfileManager();
    }

    public LevelManager getLevelManager() {return getPlugin().get().getLevelManager();}

    public RegionManager getRegionManager() {
        return getPlugin().get().getRegionManager();
    }

    public DungeonManager dungeonManager() {
        return getPlugin().get().getDungeonManager();
    }

}
