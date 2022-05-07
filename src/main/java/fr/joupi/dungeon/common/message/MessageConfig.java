package fr.joupi.dungeon.common.message;

import fr.joupi.dungeon.DungeonPlugin;
import fr.joupi.dungeon.utils.file.yml.AbstractConfig;
import fr.joupi.dungeon.utils.visual.VisualFactory;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;

public class MessageConfig extends AbstractConfig<DungeonPlugin> {

    public MessageConfig(DungeonPlugin plugin) {
        super(plugin, "messages");
    }

    public List<String> getLevelMaxReached() {
        return getMessage("level-max-reached");
    }

    public String getGainExperienceActionBar(double experience) {
        return formatMessage(getString("level-experience-gain-actionbar")).replace("%exp%", VisualFactory.decimalFormat().format(experience));
    }

    private List<String> getMessage(String path) {
        return Arrays.asList(formatMessage(getString(path)).split("@"));
    }

    private String formatMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message.replace("%prefix%", getString("prefix")));
    }

}
