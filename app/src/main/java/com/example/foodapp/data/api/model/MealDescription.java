package com.example.foodapp.data.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing a description of a meal as retrieved in TheMealDB API.
 */
public class MealDescription {

    // ID of the meal
    @SerializedName("idMeal")
    @Expose
    private String idMeal;
    // Name of the meal
    @SerializedName("strMeal")
    @Expose
    private String strMeal;
    // Alternate drink for the meal
    @SerializedName("strDrinkAlternate")
    @Expose
    private Object strDrinkAlternate;
    // Category of the meal
    @SerializedName("strCategory")
    @Expose
    private String strCategory;
    // Area where the meal is from
    @SerializedName("strArea")
    @Expose
    private String strArea;
    // Instructions on how to prepare the meal
    @SerializedName("strInstructions")
    @Expose
    private String strInstructions;
    // URL of an image of the meal
    @SerializedName("strMealThumb")
    @Expose
    private String strMealThumb;
    // Tags related to the meal
    @SerializedName("strTags")
    @Expose
    private String strTags;
    // URL of a YouTube video of the meal's preparation
    @SerializedName("strYoutube")
    @Expose
    private String strYoutube;
    // List of ingredients for the meal
    @SerializedName("strIngredient1")
    @Expose
    private String strIngredient1;
    @SerializedName("strIngredient2")
    @Expose
    private String strIngredient2;
    @SerializedName("strIngredient3")
    @Expose
    private String strIngredient3;
    @SerializedName("strIngredient4")
    @Expose
    private String strIngredient4;
    @SerializedName("strIngredient5")
    @Expose
    private String strIngredient5;
    @SerializedName("strIngredient6")
    @Expose
    private String strIngredient6;
    @SerializedName("strIngredient7")
    @Expose
    private String strIngredient7;
    @SerializedName("strIngredient8")
    @Expose
    private String strIngredient8;
    @SerializedName("strIngredient9")
    @Expose
    private String strIngredient9;
    @SerializedName("strIngredient10")
    @Expose
    private String strIngredient10;
    @SerializedName("strIngredient11")
    @Expose
    private String strIngredient11;
    @SerializedName("strIngredient12")
    @Expose
    private String strIngredient12;
    @SerializedName("strIngredient13")
    @Expose
    private String strIngredient13;
    @SerializedName("strIngredient14")
    @Expose
    private String strIngredient14;
    @SerializedName("strIngredient15")
    @Expose
    private String strIngredient15;
    @SerializedName("strIngredient16")
    @Expose
    private String strIngredient16;
    @SerializedName("strIngredient17")
    @Expose
    private String strIngredient17;
    @SerializedName("strIngredient18")
    @Expose
    private String strIngredient18;
    @SerializedName("strIngredient19")
    @Expose
    private String strIngredient19;
    @SerializedName("strIngredient20")
    @Expose
    private String strIngredient20;
    @SerializedName("strMeasure1")
    @Expose
    // List of measure for the meal
    private String strMeasure1;
    @SerializedName("strMeasure2")
    @Expose
    private String strMeasure2;
    @SerializedName("strMeasure3")
    @Expose
    private String strMeasure3;
    @SerializedName("strMeasure4")
    @Expose
    private String strMeasure4;
    @SerializedName("strMeasure5")
    @Expose
    private String strMeasure5;
    @SerializedName("strMeasure6")
    @Expose
    private String strMeasure6;
    @SerializedName("strMeasure7")
    @Expose
    private String strMeasure7;
    @SerializedName("strMeasure8")
    @Expose
    private String strMeasure8;
    @SerializedName("strMeasure9")
    @Expose
    private String strMeasure9;
    @SerializedName("strMeasure10")
    @Expose
    private String strMeasure10;
    @SerializedName("strMeasure11")
    @Expose
    private String strMeasure11;
    @SerializedName("strMeasure12")
    @Expose
    private String strMeasure12;
    @SerializedName("strMeasure13")
    @Expose
    private String strMeasure13;
    @SerializedName("strMeasure14")
    @Expose
    private String strMeasure14;
    @SerializedName("strMeasure15")
    @Expose
    private String strMeasure15;
    @SerializedName("strMeasure16")
    @Expose
    private String strMeasure16;
    @SerializedName("strMeasure17")
    @Expose
    private String strMeasure17;
    @SerializedName("strMeasure18")
    @Expose
    private String strMeasure18;
    @SerializedName("strMeasure19")
    @Expose
    private String strMeasure19;
    @SerializedName("strMeasure20")
    @Expose
    private String strMeasure20;

    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public Object getStrDrinkAlternate() {
        return strDrinkAlternate;
    }

    public void setStrDrinkAlternate(Object strDrinkAlternate) {
        this.strDrinkAlternate = strDrinkAlternate;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions = strInstructions;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public String getStrTags() {
        return strTags;
    }

    public void setStrTags(String strTags) {
        this.strTags = strTags;
    }

    public String getStrYoutube() {
        return strYoutube;
    }

    public void setStrYoutube(String strYoutube) {
        this.strYoutube = strYoutube;
    }

    /**
     * This method returns a map of ingredients and their corresponding quantities for the meal.
     * @return a map of ingredients and quantities.
     */
    public Map<String, String> getMapIngredientsAndQuantities() {
        List<String> ingredients = Arrays.asList(strIngredient1, strIngredient2, strIngredient3,
                strIngredient4, strIngredient5, strIngredient6,
                strIngredient7, strIngredient8, strIngredient9,
                strIngredient10, strIngredient11, strIngredient12,
                strIngredient13, strIngredient14, strIngredient15,
                strIngredient16, strIngredient17, strIngredient18,
                strIngredient19, strIngredient20);
        List<String> measures = Arrays.asList(strMeasure1, strMeasure2, strMeasure3,
                strMeasure4, strMeasure5, strMeasure6,
                strMeasure7, strMeasure8, strMeasure9,
                strMeasure10, strMeasure11, strMeasure12,
                strMeasure13, strMeasure14, strMeasure15,
                strMeasure16, strMeasure17, strMeasure18,
                strMeasure19, strMeasure20);

        // using a LinkedHashMap to keep order determined by the API
        Map<String, String> ingredientMeasureMap = new LinkedHashMap<>();
        for (int i = 0; i < ingredients.size(); i++) {
            String ingredient = ingredients.get(i);
            String measure = measures.get(i);
            if (ingredient != null && !ingredient.isEmpty()) {
                ingredientMeasureMap.put(ingredient, measure);
            }
        }
        return ingredientMeasureMap;
    }

}