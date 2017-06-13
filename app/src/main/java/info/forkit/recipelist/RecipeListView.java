package info.forkit.recipelist;

import java.util.ArrayList;
import info.forkit.model.objects.Recipe;

public interface RecipeListView {

    void showProgressBar(boolean visible);

    void setRecipes(ArrayList<Recipe> recipes);

    void showMessage(String message);
}
