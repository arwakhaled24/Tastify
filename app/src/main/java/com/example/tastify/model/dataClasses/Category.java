package com.example.tastify.model.dataClasses;

public class Category implements ListItem{
    public Category(String idCategory, String strCategory, String strCategoryThumb, String strCategoryDescription) {
        this.idCategory = idCategory;
        this.strCategory = strCategory;
        this.strCategoryThumb = strCategoryThumb;
        this.strCategoryDescription = strCategoryDescription;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public String getStrCategoryThumb() {
        return strCategoryThumb;
    }

    public String getStrCategoryDescription() {
        return strCategoryDescription;
    }

    private String idCategory;
    private String strCategory;
    private String strCategoryThumb;
    private String strCategoryDescription;

    @Override
    public int getType() {
       return ListItemType.TYPE_CATEGORY;
    }
}