package fr.joupi.dungeon.common.profile.core;

import fr.joupi.dungeon.utils.visual.VisualFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Profile {

    private final UUID uuid;

    private final List<String> unlockedWarp;

    private final ProfileLeveling leveling;

    /*
        Utils Methods
     */

    public void sendMessages(List<String> messages) {
        VisualFactory.sendMessages(getPlayer(), messages);
    }

    public void sendMessages(String... messages) {
        VisualFactory.sendMessages(getPlayer(), messages);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

}