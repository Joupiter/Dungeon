package fr.joupi.dungeon.common.dungeon;

import fr.joupi.dungeon.DungeonPlugin;
import fr.joupi.dungeon.utils.handler.AbstractHandler;
import lombok.Getter;

@Getter
public class DungeonManager extends AbstractHandler<DungeonPlugin> {
    public DungeonManager(DungeonPlugin plugin) {
        super(plugin);
    }
}
