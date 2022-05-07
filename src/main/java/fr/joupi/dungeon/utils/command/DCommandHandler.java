package fr.joupi.dungeon.utils.command;

import com.google.common.collect.Lists;
import fr.joupi.dungeon.utils.command.annotation.DCommand;
import fr.joupi.dungeon.utils.command.annotation.DSubCommand;
import fr.joupi.dungeon.utils.command.converter.*;
import fr.joupi.dungeon.utils.command.data.DCommandData;
import fr.joupi.dungeon.utils.command.data.DSubCommandData;
import lombok.Getter;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DCommandHandler {

    private CommandMap commandMap;

    private final String fallbackPrefix;

    private final List<IConverter<?>> converters = Lists.newArrayList();

    private final List<DCustomCommand> customCommands = Lists.newArrayList();

    public DCommandHandler(JavaPlugin plugin, String fallbackPrefix) {
        this.fallbackPrefix = fallbackPrefix;

        try {
            Field field = plugin.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            this.commandMap = (CommandMap) field.get(plugin.getServer());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        this.registerConverters(new IntegerConverter(),
                new LongConverter(),
                new PlayerConverter(),
                new StringConverter());
    }

    public void registerConverters(IConverter<?>... converters) {
        Arrays.asList(converters).forEach(this::registerConverter);
    }

    public void registerConverter(IConverter<?> converter) {
        if (converter == null)
            throw new IllegalArgumentException("The converter you attempted to pass was null");

        this.converters.add(converter);
    }

    public IConverter<?> getConverter(Class<?> clazz) {
        for (IConverter<?> converter : this.converters)
            if (converter.getType().equals(clazz))
                return converter;

        return null;
    }

    public void registerCommands(Object... objects) {
        Arrays.asList(objects).forEach(this::registerCommand);
    }

    public void registerCommand(Object object) {
        Method[] rawMethods = object.getClass().getMethods();
        List<Method> commandMethods = Arrays.stream(rawMethods).filter(method -> method.getAnnotation(DCommand.class) != null).collect(Collectors.toList());
        List<Method> subCommandMethods = Arrays.stream(rawMethods).filter(method -> method.getAnnotation(DSubCommand.class) != null).collect(Collectors.toList());

        for (Method method : commandMethods) {
            DCommand command = method.getAnnotation(DCommand.class);
            DCommandData commandData = new DCommandData(object, method, command);
            DCustomCommand customCommand = new DCustomCommand(commandData, this);

            customCommands.add(customCommand);
            this.commandMap.register(fallbackPrefix, customCommand);
        }

        for (Method method : subCommandMethods) {
            DSubCommand subCommand = method.getAnnotation(DSubCommand.class);

            DCustomCommand parentCommand = this.customCommands
                    .stream().filter(customCommand ->
                            customCommand.getCommandData().getCommand().name()
                                    .equalsIgnoreCase(subCommand.parent()) || Arrays.stream(customCommand.getCommandData().getCommand().aliases())
                                            .filter(alias -> subCommand.parent().equalsIgnoreCase(alias))
                                            .findFirst()
                                            .orElse(null) != null).findFirst().orElse(null);

            if (parentCommand == null) {
                System.out.println("Failed to find parent command " + subCommand.parent() + " for command " + subCommand.name());
                return;
            }

            parentCommand.getCommandData().getSubCommands().add(new DSubCommandData(object, method, subCommand));
        }
    }

}
