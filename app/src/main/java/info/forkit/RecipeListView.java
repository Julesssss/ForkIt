package info.forkit;

import java.util.ArrayList;

public interface RecipeListView {

    void showProgressBar(boolean visible);

    void setRecipes(ArrayList<Recipe> recipes);

    void showMessage(String message);
}
