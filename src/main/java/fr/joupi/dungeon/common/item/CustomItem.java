package fr.joupi.dungeon.common.item;

import fr.joupi.dungeon.DungeonPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Listener;

@Getter
public abstract class CustomItem implements Listener {

    private final DungeonPlugin plugin;

    private final String name;
    private final int id;
    @Setter private boolean craftable;

    protected CustomItem(DungeonPlugin plugin, String name, int id, boolean craftable) {
        this.plugin = plugin;
        this.name = name;
        this.id = id;
        this.craftable = craftable;
        register();
    }

    public abstract RItemUnsafe getItem();

    private void register() {
        getPlugin().getServer().getPluginManager().registerEvents(this, plugin);
    }

}
