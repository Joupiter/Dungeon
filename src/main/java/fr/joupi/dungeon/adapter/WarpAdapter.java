package fr.joupi.dungeon.adapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import fr.joupi.dungeon.common.warp.core.Warp;
import fr.joupi.dungeon.utils.file.json.IAdapter;
import fr.joupi.dungeon.utils.visual.VisualFactory;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class WarpAdapter implements IAdapter<Warp> {

    @Override
    public JsonElement serialize(Warp warp) {
        final JsonObject jsonObject = new JsonObject();
        final JsonElement locationElement = new JsonObject();

        locationElement.getAsJsonObject().addProperty("x", warp.getX());
        locationElement.getAsJsonObject().addProperty("y", warp.getY());
        locationElement.getAsJsonObject().addProperty("z", warp.getZ());
        locationElement.getAsJsonObject().addProperty("yaw", warp.getLocation().getYaw());
        locationElement.getAsJsonObject().addProperty("pitch", warp.getLocation().getPitch());
        locationElement.getAsJsonObject().addProperty("world", warp.getWorld().getName());

        jsonObject.addProperty("name", warp.getName());
        jsonObject.addProperty("displayName", warp.getDisplayName());
        jsonObject.addProperty("icon", warp.getIcon().name());
        jsonObject.add("location", locationElement);

        return jsonObject;
    }

    @Override
    public Warp deserialize(JsonElement jsonElement) throws JsonParseException {
        final JsonObject jsonObject = jsonElement.getAsJsonObject();
        final JsonObject locationElement = jsonObject.get("location").getAsJsonObject();
        final Location location = new Location(Bukkit.getWorld(locationElement.get("world").getAsString()), locationElement.get("x").getAsDouble(), locationElement.getAsJsonObject().get("y").getAsDouble(), locationElement.get("z").getAsDouble(), locationElement.get("yaw").getAsFloat(), locationElement.get("pitch").getAsFloat());

        return new Warp(
                jsonObject.get("name").getAsString(),
                VisualFactory.coloredText(jsonObject.get("displayName").getAsString()),
                location,
                jsonObject.get("icon").getAsString());
    }

}
