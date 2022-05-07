package fr.joupi.dungeon.common.profile;

import com.google.common.collect.Maps;
import fr.joupi.dungeon.DungeonPlugin;
import fr.joupi.dungeon.common.profile.core.Profile;
import fr.joupi.dungeon.common.profile.core.config.ProfileConfig;
import fr.joupi.dungeon.utils.handler.AbstractHandler;
import fr.joupi.dungeon.utils.threading.MultiThreading;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

@Getter
public class ProfileManager extends AbstractHandler<DungeonPlugin> {

    private final ConcurrentMap<UUID, Profile> profiles;

    private final ProfileConfig profileConfig;

    public ProfileManager(DungeonPlugin plugin) {
        super(plugin);
        this.profiles = Maps.newConcurrentMap();
        this.profileConfig = new ProfileConfig(this);
    }

    public Profile getProfile(UUID uuid) {
        return getProfiles().get(uuid);
    }

    public void add(Profile profile) {
        getProfiles().put(profile.getUuid(), profile);
        getPlugin().get().getScoreboard().onJoin(profile.getPlayer());
    }

    public void remove(Player player) {
        getPlugin().get().getScoreboard().onLeave(player);
        getProfileConfig().saveProfile(getProfile(player.getUniqueId()));
        getProfiles().remove(player.getUniqueId());
    }

}
