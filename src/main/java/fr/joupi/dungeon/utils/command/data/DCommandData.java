package fr.joupi.dungeon.utils.command.data;

import com.google.common.collect.Lists;
import fr.joupi.dungeon.utils.command.annotation.DCommand;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;

@Data
public class DCommandData {

    private final Object object;
    private final Method method;

    private final DCommand command;
    private final List<DSubCommandData> subCommands = Lists.newLinkedList();

}
