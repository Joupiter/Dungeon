package fr.joupi.dungeon.common.warp;

import com.google.common.collect.Lists;
import fr.joupi.dungeon.DungeonPlugin;
import fr.joupi.dungeon.common.profile.core.Profile;
import fr.joupi.dungeon.common.warp.core.Warp;
import fr.joupi.dungeon.common.warp.core.config.WarpConfig;
import fr.joupi.dungeon.utils.handler.AbstractHandler;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class WarpManager extends AbstractHandler<DungeonPlugin> {

    private final List<Warp> warps;

    private final WarpConfig warpConfig;

    public WarpManager(DungeonPlugin plugin) {
        super(plugin);
        this.warps = Lists.newLinkedList();
        this.warpConfig = new WarpConfig(this);
    }

    @Override
    public void unload() {
        getWarpConfig().saveWarps();
    }

    public Warp getWarp(String warpName) {
        return getWarps()
                .stream()
                .filter(warp -> warp.getName().equalsIgnoreCase(warpName))
                .findFirst()
                .orElse(null);
    }

    public void unlock(Profile profile, String warpName) {
        if (!hasUnlocked(profile, warpName))
            profile.getUnlockedWarp().add(warpName);
    }

    public boolean hasUnlocked(Profile profile, String warpName) {
        return profile.getUnlockedWarp().contains(warpName);
    }

    public boolean exists(String warpName) {
        return getWarpsName().contains(warpName);
    }

    public List<String> getWarpsName() {
        return getWarps().stream().map(Warp::getName).collect(Collectors.toList());
    }

    public String getWarpsNames() {
        return String.join(", ", getWarpsName());
    }

}
