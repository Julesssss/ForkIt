package info.forkit.recipelist;

import java.util.ArrayList;

import info.forkit.base.BaseView;
import info.forkit.model.objects.Recipe;

public interface RecipeListView extends BaseView {

    void setRecipes(ArrayList<Recipe> recipes);

    void showEmptyMessage(boolean visible);
}
