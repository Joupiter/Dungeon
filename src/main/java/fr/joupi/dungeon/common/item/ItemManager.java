package fr.joupi.dungeon.common.item;

import com.google.common.collect.Lists;
import fr.joupi.dungeon.DungeonPlugin;
import fr.joupi.dungeon.utils.handler.AbstractHandler;
import lombok.Getter;

import java.util.List;

@Getter
public class ItemManager extends AbstractHandler<DungeonPlugin> {

    private final List<CustomItem> items;

    public ItemManager(DungeonPlugin plugin) {
        super(plugin);
        this.items = Lists.newLinkedList();
    }


}
