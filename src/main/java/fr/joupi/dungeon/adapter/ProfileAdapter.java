package fr.joupi.dungeon.adapter;

import com.google.gson.*;
import fr.joupi.dungeon.common.profile.core.Profile;
import fr.joupi.dungeon.common.profile.core.ProfileLeveling;
import fr.joupi.dungeon.utils.file.json.IAdapter;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class ProfileAdapter implements IAdapter<Profile> {

    @Override
    public JsonElement serialize(Profile profile) {
        final JsonObject jsonObject = new JsonObject();
        final JsonArray jsonArray = new JsonArray();

        profile.getUnlockedWarp().forEach(warp -> jsonArray.add(new JsonPrimitive(warp)));

        jsonObject.addProperty("uuid", profile.getUuid().toString());
        jsonObject.addProperty("level", profile.getLeveling().getCurrentLevel());
        jsonObject.addProperty("experience", profile.getLeveling().getExperience());
        jsonObject.add("unlockedWarps", jsonArray);

        return jsonObject;
    }

    @Override
    public Profile deserialize(JsonElement jsonElement) throws JsonParseException {
        final JsonObject jsonObject = jsonElement.getAsJsonObject();
        final List<String> unlockedWarps = new LinkedList<>();
        final JsonArray jsonArray = jsonObject.getAsJsonArray("unlockedWarps");

        jsonArray.forEach(element -> unlockedWarps.add(element.getAsString()));

        return new Profile(
                UUID.fromString(jsonObject.get("uuid").getAsString()),
                unlockedWarps,
                new ProfileLeveling(
                        jsonObject.get("level").getAsInt(),
                        jsonObject.get("experience").getAsDouble())
        );
    }

}
