package fr.joupi.dungeon.utils.command.converter;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerConverter implements IConverter<Player> {

    @Override
    public Class<Player> getType() {
        return Player.class;
    }

    @Override
    public Player getFromString(CommandSender sender, String string) {
        return Bukkit.getServer().getPlayer(string);
    }

    @Override
    public List<String> tabComplete(CommandSender sender) {
        return Bukkit.getServer().getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }

}
