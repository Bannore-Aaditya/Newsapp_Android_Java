package com.example.newsapp;

public class CategoryRVmodal {
    private String Category;
    private String CategoryImageUrl ;

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getCategoryImageUrl() {
        return CategoryImageUrl;
    }

    public void setCategoryImageUrl(String categoryImageUrl) {
        CategoryImageUrl = categoryImageUrl;
    }

    public CategoryRVmodal(String category, String categoryImageUrl) {
        Category = category;
        CategoryImageUrl = categoryImageUrl;
    }
}
