package fr.joupi.dungeon.utils.command.data;

import fr.joupi.dungeon.utils.command.annotation.DSubCommand;
import lombok.Data;

import java.lang.reflect.Method;

@Data
public class DSubCommandData {

    private final Object object;
    private final Method method;

    private final DSubCommand dSubCommand;

}
