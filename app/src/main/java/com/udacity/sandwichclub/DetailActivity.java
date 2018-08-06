package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich sandwich;

    TextView mDescriptionTextView, mIngredientsTextView, mPlaceOfOriginTextView, mAlsoKnownAsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        mDescriptionTextView = findViewById(R.id.description_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);
        mPlaceOfOriginTextView = findViewById(R.id.origin_tv);
        mAlsoKnownAsTextView = findViewById(R.id.also_known_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        //Convert alsoKnownAs array into String
        StringBuilder alsoKnownAsBuilder = new StringBuilder();
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        for (String aka : alsoKnownAs){
            if (alsoKnownAs.indexOf(aka) == (alsoKnownAs.size() - 1)){
                alsoKnownAsBuilder.append(aka);
                alsoKnownAsBuilder.append(".");
            } else {
                alsoKnownAsBuilder.append(aka);
                alsoKnownAsBuilder.append(", ");
            }
        }

        //Convert ingredients array into String
        StringBuilder ingredientsBuilder = new StringBuilder();
        List<String> ingredients = sandwich.getIngredients();
        for (String ingredient : ingredients){
            if (ingredients.indexOf(ingredient) == (ingredients.size() - 1)){
                ingredientsBuilder.append(ingredient);
                ingredientsBuilder.append(".");
            } else {
                ingredientsBuilder.append(ingredient);
                ingredientsBuilder.append(", ");
            }
        }

        mDescriptionTextView.setText(sandwich.getDescription());
        mPlaceOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        mIngredientsTextView.setText(ingredientsBuilder);
        mAlsoKnownAsTextView.setText(alsoKnownAsBuilder);
    }
}
