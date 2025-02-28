package com.example.tastify.model.dataClasses;

public class Meal implements ListItem{
    public String strIngredient;

    public String getStrIngredient() {
        return strIngredient;
    }

    @Override
    public int getType() {
        return ListItemType.TYPE_INGREDIENT;
    }
}