package fr.joupi.dungeon.common.profile.core.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.joupi.dungeon.adapter.ProfileAdapter;
import fr.joupi.dungeon.common.profile.ProfileManager;
import fr.joupi.dungeon.common.profile.core.Profile;
import fr.joupi.dungeon.common.profile.core.ProfileLeveling;
import fr.joupi.dungeon.utils.file.FileUtils;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.LinkedList;

@Getter
public class ProfileConfig {

    private final ProfileManager profileManager;
    private final Gson gson;

    public ProfileConfig(ProfileManager profileManager) {
        this.profileManager = profileManager;
        this.gson = buildGson();
    }

    public void loadProfile(Player player) {
        File file = new File(getSaveDir(), player.getUniqueId().toString() + ".json");

        if (!file.exists()) {
            Profile newProfile = new Profile(player.getUniqueId(), new LinkedList<>(), new ProfileLeveling(0, 0.0D));

            FileUtils.createFile(file);
            saveProfile(newProfile);

            getProfileManager().add(newProfile);
            return;
        }

        getProfileManager().add(deserialize(FileUtils.loadContent(file)));
    }

    public void saveProfile(Profile user) {
        FileUtils.save(new File(getSaveDir(), user.getUuid().toString() + ".json"), serialize(user));
    }

    public String serialize(Profile profile) {
        return getGson().toJson(profile);
    }

    public Profile deserialize(String json) {
        return getGson().fromJson(json, Profile.class);
    }

    public File getSaveDir() {
        return new File(getProfileManager().getPlugin().getDataFolder(), "/profiles/");
    }

    private Gson buildGson() {
        return new GsonBuilder()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .registerTypeAdapter(ProfileAdapter.class, new ProfileAdapter())
                .serializeNulls()
                .create();
    }

}
