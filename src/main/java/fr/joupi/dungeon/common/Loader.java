package fr.joupi.dungeon.common;

import fr.joupi.dungeon.DungeonPlugin;
import fr.joupi.dungeon.common.board.DungeonBoard;
import fr.joupi.dungeon.common.dungeon.DungeonManager;
import fr.joupi.dungeon.common.level.core.LevelCommand;
import fr.joupi.dungeon.common.level.LevelManager;
import fr.joupi.dungeon.common.message.MessageConfig;
import fr.joupi.dungeon.common.profile.ProfileManager;
import fr.joupi.dungeon.common.region.RegionManager;
import fr.joupi.dungeon.common.warp.WarpManager;
import fr.joupi.dungeon.common.warp.core.WarpCommand;
import fr.joupi.dungeon.listener.PlayerConnectionListener;
import fr.joupi.dungeon.utils.command.DCommandHandler;
import fr.joupi.dungeon.utils.handler.AbstractHandler;
import fr.joupi.dungeon.utils.visual.scoreboard.DScoreboard;
import lombok.Getter;
import org.bukkit.event.Listener;

import java.util.Arrays;

@Getter
public class Loader extends AbstractHandler<DungeonPlugin> {

    private final DCommandHandler commandHandler;

    private final MessageConfig messages;

    private final ProfileManager profileManager;
    private final WarpManager warpManager;
    private final LevelManager levelManager;
    private final RegionManager regionManager;
    private final DungeonManager dungeonManager;

    private final DScoreboard<DungeonPlugin> scoreboard;

    public Loader(DungeonPlugin plugin) {
        super(plugin);
        this.messages = new MessageConfig(plugin);
        this.commandHandler = new DCommandHandler(plugin, "dungeon");
        this.profileManager = new ProfileManager(plugin);
        this.warpManager = new WarpManager(plugin);
        this.levelManager = new LevelManager(plugin);
        this.regionManager = new RegionManager(plugin);
        this.dungeonManager = new DungeonManager(plugin);
        this.scoreboard = new DScoreboard<>(plugin, new DungeonBoard(plugin));

        load();
    }

    @Override
    public void load() {
        registerListeners(new PlayerConnectionListener(getPlugin()));
        getCommandHandler().registerCommands(
                new WarpCommand<>(getWarpManager()),
                new LevelCommand<>(getLevelManager())
        );
    }

    @Override
    public void unload() {
        getWarpManager().unload();
        getLevelManager().unload();
    }

    private void registerListeners(Listener... listeners) {
        Arrays.asList(listeners)
                .forEach(this::registerListener);
    }

    private void registerListener(Listener listener) {
        getPlugin().getServer().getPluginManager().registerEvents(listener, getPlugin());
    }

}
