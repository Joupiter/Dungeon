package fr.joupi.dungeon.common.level.core;

import fr.joupi.dungeon.common.profile.core.Profile;
import fr.joupi.dungeon.common.profile.core.ProfileLeveling;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public class Level {

    private final int level;
    private final double experienceNeeded;
    private final List<String> rewards;

    public void giveRewards(Profile profile) {
        profile.sendMessages("&aVous avez reçus une récompense !");
    }

    public List<String> levelUpMessages(ProfileLeveling leveling, boolean isLevelMax) {
        return isLevelMax ? Arrays.asList(
                "&7&m---------------------------",
                "",
                "&7 &8 &9 &5 &a&l⇪ &bNIVEAU SUPERIEUR &a&l⇪",
                "",
                "&eTu es maintenant niveau &b" + leveling.getCurrentLevel() + " &e!",
                "&aVous avez atteint le niveau max bien joué !",
                "",
                "&7&m---------------------------"
        ) : Arrays.asList(
                "&7&m---------------------------",
                "",
                "&7 &8 &9 &5 &a&l⇪ &bNIVEAU SUPERIEUR &a&l⇪",
                "",
                "&eTu es maintenant niveau &b" + leveling.getCurrentLevel() + " &e!",
                "&7Il vous manque &b" + (getExperienceNeeded() - leveling.getExperience()) + " &7pour le niveau &b" + (getLevel() + 1),
                "",
                "&7&m---------------------------"
        );
    }

}
