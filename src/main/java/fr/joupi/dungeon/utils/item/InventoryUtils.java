package fr.joupi.dungeon.utils.item;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@UtilityClass
public class InventoryUtils {

    public boolean isNullItem(ItemStack item) {
        return item == null || item.getType() == Material.AIR;
    }

    public void addItem(Player player, ItemStack itemStack) {
        addItem(player, itemStack, 1);
    }

    public void addItem(Player player, ItemStack item, int quantity) {
        Inventory inventory = player.getInventory();
        ItemStack current = new ItemStack(item);
        int max = current.getMaxStackSize();

        if (quantity > max) {
            int leftOver = quantity;
            while (leftOver > 0) {
                int add = 0;
                add += Math.min(leftOver, max);
                current = current.clone();
                current.setAmount(add);
                inventory.addItem(current);

                leftOver -= add;
            }
        } else {
            current = current.clone();
            current.setAmount(quantity);
            inventory.addItem(current);
        }
    }

    public boolean isSimilar(Player player, String string) {
        return player.getItemInHand().getType() != null && !player.getItemInHand().getType().equals(Material.AIR) && player.getItemInHand().hasItemMeta() && player.getItemInHand().getItemMeta().hasDisplayName() && player.getItemInHand().getItemMeta().getDisplayName().equals(string);
    }

    public boolean haveRequiredItem(Player player, ItemStack item) {
        return haveRequiredItem(player, item, 1);
    }

    public boolean haveRequiredItem(Player player, ItemStack item, int quantity) {
        int quantityInInventory = getItemCount(player, item);
        return quantityInInventory >= quantity;
    }

    public static boolean isFullInventory(Player player) {
        Inventory inventory = player.getInventory();
        for (ItemStack current : inventory.getContents())
            if (isNullItem(current))
                return false;
        return true;
    }

    public boolean hasSpaceInventory(Player player, ItemStack item, int count) {
        int left = count;
        ItemStack[] items = player.getInventory().getContents();

        for (ItemStack stack : items) {
            if (stack == null || stack.getType() == Material.AIR) {
                left -= item.getMaxStackSize();
            } else {
                if (stack.getType() == item.getType() && stack.getData().getData() == item.getData().getData())
                    if (item.getMaxStackSize() > 1 && stack.getAmount() < item.getMaxStackSize())
                        left -= (stack.getMaxStackSize() - stack.getAmount());
            }
            if (left <= 0)
                break;
        }
        return left <= 0;
    }

    public int getItemCount(Player player, ItemStack item) {
        int quantityInInventory = 0;
        Inventory inventory = player.getInventory();
        for (ItemStack current : inventory.getContents()) {
            if (!isNullItem(current))
                if (current.getType() == item.getType() && current.getData().getData() == item.getData().getData())
                    quantityInInventory += current.getAmount();
        }
        return quantityInInventory;
    }

    public void decrementCurrentItem(Player player, ItemStack item, int quantity) {
        int currentAmount = item.getAmount();
        if (currentAmount <= 1)
            player.setItemInHand(null);
        else {
            int amount = currentAmount - quantity;
            item.setAmount(amount);
        }
    }

    public void decrementItem(Player player, ItemStack item, int quantity) {
        int toRemove = quantity;
        Inventory inv = player.getInventory();
        for (ItemStack is : inv.getContents()) {
            if (toRemove <= 0) break;

            if (is != null && is.getType() == item.getType() && is.getData().getData() == item.getData().getData()) {
                int amount = is.getAmount() - toRemove;
                toRemove -= is.getAmount();

                if (amount <= 0)
                    player.getInventory().removeItem(is);
                else
                    is.setAmount(amount);
            }
        }
    }

    public void addItems(Player player, List<ItemStack> items) {
        for (ItemStack item : items)
            InventoryUtils.addItem(player, item, item.getAmount());
    }

    public boolean haveSpaceInInventory(Player player, List<ItemStack> items) {
        int itemsCount = 0;
        for (ItemStack item : items)
            if (InventoryUtils.hasSpaceInventory(player, item, item.getAmount()))
                itemsCount++;

        return itemsCount >= items.size();
    }
}
