package fr.joupi.dungeon.common.level;

import com.google.common.collect.Lists;
import fr.joupi.dungeon.DungeonPlugin;
import fr.joupi.dungeon.common.level.core.Level;
import fr.joupi.dungeon.common.level.core.config.LevelConfig;
import fr.joupi.dungeon.common.profile.core.Profile;
import fr.joupi.dungeon.utils.handler.AbstractHandler;
import fr.joupi.dungeon.utils.visual.VisualFactory;
import lombok.Getter;
import org.bukkit.ChatColor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LevelManager extends AbstractHandler<DungeonPlugin> {

    private final List<Level> levels;

    private final LevelConfig levelConfig;

    public LevelManager(DungeonPlugin plugin) {
        super(plugin);
        this.levels = Lists.newLinkedList();
        this.levelConfig = new LevelConfig(this);
    }

    @Override
    public void unload() {
        getLevelConfig().saveLevels();
    }

    public List<Level> getLevelsDisordered() {
        return getLevels()
                .stream()
                .sorted(Comparator.comparingInt(Level::getLevel).reversed())
                .collect(Collectors.toList());
    }

    public void addExperience(Profile profile, double experience) {
        if (profile.getLeveling().getCurrentLevel() != getLastLevel().getLevel()) {
            if (profile.getLeveling().getExperience() + experience >= getLevel(profile).getExperienceNeeded())
                addLevel(profile, profile.getLeveling().getCurrentLevel() + 1, experience);
            else {
                profile.getLeveling().addExperience(experience);
                VisualFactory.sendActionbar(profile.getPlayer(), getPlugin().get().getMessages().getGainExperienceActionBar(experience));
            }
        }
    }

    public void addLevel(Profile profile, int level, double experience) {
        if (level == getLastLevel().getLevel()) {
            if (profile.getLeveling().getCurrentLevel() == getLastLevel().getLevel())
                profile.sendMessages(getPlugin().get().getMessages().getLevelMaxReached());
            else
                profile.getLeveling().setLevelMax(profile.getPlayer(), getLastLevel());
        } else {
            profile.getLeveling().levelChange(level, experience, getLevel(level - 1).getExperienceNeeded());
            profile.sendMessages(getLevel(level).levelUpMessages(profile.getLeveling(), false));
        }
    }

    public Level getLastLevel() {
        return getLevelsDisordered()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public Level getNextLevel(Profile profile) {
        return profile.getLeveling().getCurrentLevel() == getLastLevel().getLevel() ? getLevel(profile.getLeveling().getCurrentLevel()) : getLevel(profile.getLeveling().getCurrentLevel() + 1);
    }

    public Level getLevel(Profile profile) {
        return getLevel(profile.getLeveling().getCurrentLevel());
    }

    public Level getLevel(int level) {
        return getLevels().get(level);
    }

    public int getPercent(Profile profile) {
        return (int) profile.getLeveling().getExperience() * 100 / (int) getLevel(profile).getExperienceNeeded();
    }

    public String getProgressBar(Profile profile) {
        int exp = (int) profile.getLeveling().getExperience();
        int neededExp = (int) getLevel(profile).getExperienceNeeded();
        int percentage = exp * 100 / neededExp;
        int filledSize = (int) (16 * percentage / 100f);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ChatColor.GREEN);

        for (int i = 0; i < filledSize; i++)
            stringBuilder.append("|");
        if (percentage < 100) {
            stringBuilder.append(ChatColor.GRAY);
            for (int i = 0; i < (16 - filledSize); i++)
                stringBuilder.append("|");
        }
        return stringBuilder.toString();
    }

}
