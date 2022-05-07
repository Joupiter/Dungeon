package fr.joupi.dungeon.common.board;

import fr.joupi.dungeon.DungeonPlugin;
import fr.joupi.dungeon.common.profile.core.Profile;
import fr.joupi.dungeon.utils.visual.scoreboard.AbstractBoard;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class DungeonBoard extends AbstractBoard<DungeonPlugin> {

    public DungeonBoard(DungeonPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getTitle() {
        return "&6Dungeon";
    }

    @Override
    public List<String> getLines(Player player) {
        Profile profile = getProfileManager().getProfile(player.getUniqueId());

        return Arrays.asList(
                "&1",
                "&7Pièce d'or : &e0",
                "&2",
                "&7Niveau : &b" + profile.getLeveling().getCurrentLevel(),
                "&3",
                "&7[" + getLevelManager().getProgressBar(profile) + "&7] (&e" + getLevelManager().getPercent(profile) + "%&7)",
                "&4",
                "&eplay.dungeon.fr"
        );
    }
}
