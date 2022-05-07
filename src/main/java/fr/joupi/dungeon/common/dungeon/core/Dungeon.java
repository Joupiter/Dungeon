package fr.joupi.dungeon.common.dungeon.core;

import fr.joupi.dungeon.common.mob.core.DungeonMob;
import fr.joupi.dungeon.common.region.core.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;

import java.util.List;

@Getter
@AllArgsConstructor
public class Dungeon {

    private final String name;

    private final List<String> description, rewards;
    private final List<DungeonMob> mobs;

    private final Region region;
    private final Location spawn;
    private final DungeonKey key;

}
