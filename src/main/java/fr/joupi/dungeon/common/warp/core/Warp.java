package fr.joupi.dungeon.common.warp.core;

import fr.joupi.dungeon.common.profile.core.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

@Getter
@AllArgsConstructor
public class Warp {

    private final String name, displayName;
    @Setter private Location location;
    @Setter private Material icon;

    public Warp(String name, String displayName, Location location, String icon) {
        this(name, displayName, location, Material.getMaterial(icon));
    }

    public double getX() {
        return getLocation().getX();
    }

    public double getY() {
        return getLocation().getY();
    }

    public double getZ() {
        return getLocation().getZ();
    }

    public World getWorld() {
        return getLocation().getWorld();
    }

    public void teleport(Profile profile) {
        profile.getPlayer().teleport(getLocation());
    }

}
