package fr.joupi.dungeon.common.profile.core;

import fr.joupi.dungeon.common.level.core.Level;
import fr.joupi.dungeon.utils.visual.VisualFactory;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
public class ProfileLeveling {

    @Setter private int currentLevel;
    @Setter private double experience;

    public ProfileLeveling(int currentLevel, double experience) {
        this.currentLevel = currentLevel;
        this.experience = experience;
    }

    public void levelChange(int level, double experienceRest, double experienceNeeded) {
        setCurrentLevel(level);
        addExperience(experienceRest);
        removeExperience(experienceNeeded);
    }

    public void setLevelMax(Player player, Level levelMax) {
        setCurrentLevel(levelMax.getLevel());
        setExperience(levelMax.getExperienceNeeded());
        VisualFactory.sendMessages(player, levelMax.levelUpMessages(this, true));
    }

    public void addExperience(double experience) {
        setExperience(getExperience() + experience);
    }

    public void removeExperience(double experience) {
        setExperience(experience > getExperience() ? 0 : this.experience - experience);
    }

}