package fr.joupi.dungeon.common.warp.core;

import fr.joupi.dungeon.common.profile.core.Profile;
import fr.joupi.dungeon.common.warp.WarpManager;
import fr.joupi.dungeon.common.warp.core.menu.WarpMenu;
import fr.joupi.dungeon.utils.command.annotation.DCommand;
import fr.joupi.dungeon.utils.command.annotation.DParameter;
import fr.joupi.dungeon.utils.command.annotation.DSubCommand;
import lombok.Data;
import org.bukkit.entity.Player;

@Data
public class WarpCommand<W extends WarpManager> {

    private final W warpManager;

    @DCommand(name = "warp", permission = "dungeon.command.warps")
    public void execute1(Player player, @DParameter(name = "warpName") String warpName) {
        Profile profile = getWarpManager().getPlugin().get().getProfileManager().getProfile(player.getUniqueId());
        String warpNameFormated = warpName.replaceAll(" ", "");

        if (getWarpManager().exists(warpNameFormated)) {
            if (getWarpManager().hasUnlocked(profile, warpNameFormated)) {
                profile.sendMessages("&aTéléportation vers le warp &b" + warpNameFormated);
                getWarpManager().getWarp(warpNameFormated).teleport(profile);
            } else
                profile.sendMessages("&cVous n'avez pas débloquer ce warp");
        } else
            profile.sendMessages("&cLe warp &b" + warpNameFormated + " &cn'existe pas !");
    }

    @DCommand(name = "warps", permission = "dungeon.command.warps")
    public void execute2(Player player) {
        Profile profile = getWarpManager().getPlugin().get().getProfileManager().getProfile(player.getUniqueId());

        new WarpMenu(getWarpManager().getPlugin(), profile).onOpen(profile.getPlayer());
    }

    @DSubCommand(name = "unlock", parent = "warps")
    public void execute3(Player player, @DParameter(name = "warpName") String warpName) {
        Profile profile = getWarpManager().getPlugin().get().getProfileManager().getProfile(player.getUniqueId());
        String warpNameFormated = warpName.replaceAll(" ", "");

        if (getWarpManager().exists(warpNameFormated)) {
            if (!getWarpManager().hasUnlocked(profile, warpNameFormated)) {
                getWarpManager().unlock(profile, warpNameFormated);
                profile.sendMessages("&aVous avez débloquer le warp &b" + warpNameFormated + " &a!");
            } else
                profile.sendMessages("&cVous possedez déjà le warp &b"+ warpNameFormated);
        } else
            profile.sendMessages("&cLe warp &b" + warpNameFormated + " &cn'existe pas !");

    }

}
