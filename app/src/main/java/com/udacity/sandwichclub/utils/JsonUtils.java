package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();


    public static Sandwich parseSandwichJson(String json) {

        String name = "";
        List<String> alsoKnownAs = null;
        String placeOfOrigin = "";
        String description = "";
        String imageUrl = "";
        List<String> ingredients = null;

        try{
            JSONObject jsonObject = new JSONObject(json);

            JSONObject nameObject = jsonObject.getJSONObject("name");

            name = nameObject.getString("mainName");

            alsoKnownAs = new ArrayList<>();
            JSONArray alsoKnownArray = nameObject.getJSONArray("alsoKnownAs");
            for (int i = 0; i < alsoKnownArray.length(); i++){
                alsoKnownAs.add(alsoKnownArray.getString(i));
            }

            placeOfOrigin = jsonObject.getString("placeOfOrigin");
            description = jsonObject.getString("description");
            imageUrl = jsonObject.getString("image");

            ingredients = new ArrayList<>();
            JSONArray ingredientArray = jsonObject.getJSONArray("ingredients");
            for (int i = 0; i < ingredientArray.length(); i++){
                ingredients.add(ingredientArray.getString(i));
            }

        } catch (JSONException e){
            Log.d(TAG, "Error message:", e);
        }
        return new Sandwich(name, alsoKnownAs, placeOfOrigin, description, imageUrl, ingredients);
    }
}
