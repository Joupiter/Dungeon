package fr.joupi.dungeon.common.region;

import fr.joupi.dungeon.DungeonPlugin;
import fr.joupi.dungeon.utils.handler.AbstractHandler;
import lombok.Getter;

@Getter
public class RegionManager extends AbstractHandler<DungeonPlugin> {
    public RegionManager(DungeonPlugin plugin) {
        super(plugin);
    }
}
