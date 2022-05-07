package fr.joupi.dungeon.common.warp.core.menu;

import fr.joupi.dungeon.DungeonPlugin;
import fr.joupi.dungeon.common.profile.core.Profile;
import fr.joupi.dungeon.common.warp.core.Warp;
import fr.joupi.dungeon.utils.item.ItemBuilder;
import fr.joupi.dungeon.utils.gui.GuiButton;
import fr.joupi.dungeon.utils.gui.PageableGui;
import fr.joupi.dungeon.utils.item.SkullBuilder;
import lombok.Getter;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@Getter
public class WarpMenu extends PageableGui<DungeonPlugin, WarpButton> {

    private final Profile profile;

    public WarpMenu(DungeonPlugin plugin, Profile profile) {
        super(plugin, "&aWarps", 6, 10);
        this.profile = profile;

        getPlugin().get().getWarpManager().getWarps()
                .forEach(this::addWarp);
    }

    @Override
    public void setup() {
        for (int i = 0; i < getPage().countElements(); i++) {
            if (i <= 4)
                setItem(20 + i, getPage().getElements().get(i));
            else
                setItem(24 + i, getPage().getElements().get(i));
        }

        setItems(getBorders(), new GuiButton(new ItemBuilder(Material.STAINED_GLASS_PANE).setName(" ").setDyeColor(DyeColor.CYAN).build()));

        setItem(48, previousPageButton());

        setItem(50, nextPageButton());

        setItem(53, new GuiButton(new ItemBuilder(Material.BARRIER).setName("&cFermer").build(),
                event -> close((Player) event.getWhoClicked())));
    }

    private void addWarp(Warp warp) {
        getPagination().addElement(new WarpButton(warp, getProfile(), getPlugin()));
    }

    @Override
    public GuiButton nextPageButton() {
        return new GuiButton(new ItemBuilder(SkullBuilder.getRightArrowSkull()).setName("&aSuivant").build(), event -> {
            if (!getPagination().hasNext(getPage())) return;

            updatePage(getPagination().getNext(getPage()));
        });
    }

    @Override
    public GuiButton previousPageButton() {
        return new GuiButton(new ItemBuilder(SkullBuilder.getLeftArrowSkull()).setName("&cRetour").build(), event -> {
            if (!getPagination().hasPrevious(getPage())) return;

            updatePage(getPagination().getPrevious(getPage()));
        });
    }
}

class WarpButton extends GuiButton {

    public WarpButton(Warp warp, Profile profile, DungeonPlugin plugin) {
        super(plugin.get().getWarpManager().hasUnlocked(profile, warp.getName())
                ? new ItemBuilder(warp.getIcon()).setName(warp.getName()).build()
                : new ItemBuilder(SkullBuilder.getQuestionSkull()).setName("&cPas encore débloqué").build(),

        event -> {
            if (plugin.get().getWarpManager().hasUnlocked(profile, warp.getName()))
                warp.teleport(profile);
        });
    }

}
