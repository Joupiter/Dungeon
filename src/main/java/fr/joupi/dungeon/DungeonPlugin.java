package fr.joupi.dungeon;

import fr.joupi.dungeon.common.Loader;
import fr.joupi.dungeon.utils.threading.MultiThreading;
import org.bukkit.plugin.java.JavaPlugin;

public class DungeonPlugin extends JavaPlugin {

    private Loader loader;

    @Override
    public void onEnable() {
        this.loader = new Loader(this);
    }

    public Loader get() {
        return loader;
    }

    @Override
    public void onDisable() {
        MultiThreading.stopTask();
    }

}
