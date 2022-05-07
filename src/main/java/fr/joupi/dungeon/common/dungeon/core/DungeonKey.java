package fr.joupi.dungeon.common.dungeon.core;

import fr.joupi.dungeon.utils.item.ItemBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@AllArgsConstructor
public class DungeonKey {

    private final String keyName;
    private final List<String> keyDescription;

    public ItemStack toItemStack() {
        return new ItemBuilder(Material.TRIPWIRE_HOOK).setName(keyName).addLore(keyDescription).setUnbreakable(true).setGlowing(true).build();
    }

}
