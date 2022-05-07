package fr.joupi.dungeon.listener;

import fr.joupi.dungeon.DungeonPlugin;
import fr.joupi.dungeon.common.board.DungeonBoard;
import fr.joupi.dungeon.common.profile.core.Profile;
import fr.joupi.dungeon.utils.handler.AbstractListener;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


@Getter
public class PlayerConnectionListener extends AbstractListener<DungeonPlugin> {

    private final DungeonBoard dungeonBoard;

    public PlayerConnectionListener(DungeonPlugin plugin) {
        super(plugin);
        this.dungeonBoard = new DungeonBoard(plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        getProfileManager().getProfileConfig().loadProfile(player);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        getProfileManager().remove(event.getPlayer());
    }

    @EventHandler
    public void onDebug(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (event.getMessage().equalsIgnoreCase("!debug")) {
            Profile profile = getProfileManager().getProfile(player.getUniqueId());

            getLevelManager().addExperience(profile, 1);

            event.setCancelled(true);
        }

        if (event.getMessage().equalsIgnoreCase("!3")) {
            Profile profile = getProfileManager().getProfile(player.getUniqueId());

            getLevelManager().addExperience(profile, 3);

            event.setCancelled(true);
        }

        if (event.getMessage().equalsIgnoreCase("!info")) {
            Profile profile = getProfileManager().getProfile(player.getUniqueId());

            profile.sendMessages("&6Info:", " &eLevel: &b" + profile.getLeveling().getCurrentLevel(), " &eNext Level: &b" + getLevelManager().getNextLevel(profile).getLevel(), " &eExp: &b" + profile.getLeveling().getExperience());

            event.setCancelled(true);
        }

    }
}
