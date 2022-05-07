package fr.joupi.dungeon.common.mob.core;

import lombok.Getter;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class DungeonMob {

    private String name;
    private double maxHealth, spawnChance;
    private EntityType entityType;
    private ItemStack weapon;
    private ItemStack[] armor;
    private List<ItemStack> loot;

}
