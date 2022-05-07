package fr.joupi.dungeon.common.level.core;

import fr.joupi.dungeon.common.level.LevelManager;
import fr.joupi.dungeon.utils.command.annotation.DCommand;
import fr.joupi.dungeon.utils.command.annotation.DSubCommand;
import lombok.Data;
import org.bukkit.command.CommandSender;

@Data
public class LevelCommand<L extends LevelManager> {

    private final L levelManager;

    @DCommand(name = "level", aliases = {"levels"}, permission = "dungeon.command.level")
    public void execute(CommandSender sender) {
        sender.sendMessage("Worked");
    }

    @DSubCommand(name = "show", parent = "level", permission = "dungeon.command.level")
    public void executeSubCommand(CommandSender sender) {
        getLevelManager().getLevels()
                .forEach(level -> sender.sendMessage("Level " + level.getLevel() + " | Experience: " + level.getExperienceNeeded() + " | Rewards: " + String.join(",", level.getRewards())));
    }

}
