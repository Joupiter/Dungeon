package fr.joupi.dungeon.adapter;

import com.google.gson.*;
import fr.joupi.dungeon.common.level.core.Level;
import fr.joupi.dungeon.utils.file.json.IAdapter;

import java.util.LinkedList;
import java.util.List;

public class LevelAdapter implements IAdapter<Level> {

    @Override
    public JsonElement serialize(Level level) {
        final JsonObject jsonObject = new JsonObject();
        final JsonArray jsonArray = new JsonArray();

        level.getRewards().forEach(rewards -> jsonArray.add(new JsonPrimitive(rewards)));

        jsonObject.addProperty("level", level.getLevel());
        jsonObject.addProperty("experience", level.getExperienceNeeded());
        jsonObject.add("rewards", jsonArray);

        return jsonObject;
    }

    @Override
    public Level deserialize(JsonElement jsonElement) throws JsonParseException {
        final JsonObject jsonObject = jsonElement.getAsJsonObject();
        final List<String> rewardsList = new LinkedList<>();
        final JsonArray jsonArray = jsonObject.get("rewards").getAsJsonArray();

        jsonArray.forEach(element -> rewardsList.add(element.getAsString()));

        return new Level(
                jsonObject.get("level").getAsInt(),
                jsonObject.get("experience").getAsDouble(),
                rewardsList);
    }


}
