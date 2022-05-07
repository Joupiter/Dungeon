package fr.joupi.dungeon.utils.visual.scoreboard;

import org.bukkit.entity.Player;

import java.util.List;

public interface DBoard {

    String getTitle();

    List<String> getLines(Player player);

}
