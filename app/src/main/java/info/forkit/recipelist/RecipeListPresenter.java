package info.forkit.recipelist;

import java.util.ArrayList;

import info.forkit.model.database.FirebaseHelper;
import info.forkit.model.objects.Recipe;

public class RecipeListPresenter {

    private static final String TAG = RecipeListPresenter.class.getSimpleName();

    private RecipeListView view;

    public RecipeListPresenter(RecipeListView view) {
        this.view = view;
        getDataSet();
    }

    public void reattachView(RecipeListView recipeListActivity) {
        this.view = recipeListActivity;
        getDataSet();
    }

    public void detachView() {
        this.view = null;
    }

    public void onActivityRestarted() {
        getDataSet();
    }

    private void getDataSet() {
        if (view != null) {
            view.showProgressBar(true);
            getRecipes();
        }
    }

    private void getRecipes() {
        view.showProgressBar(true);
        FirebaseHelper firebaseHelper = new FirebaseHelper(); // todo: dependency injection
        firebaseHelper.getRecipeList(new FirebaseHelper.LoadRecipesCallback() { // todo: use retrolamda
            @Override
            public void getRecipes(ArrayList<Recipe> recipes) {
                view.setRecipes(recipes);
                view.showProgressBar(false);
            }

            @Override
            public void onCancelled(String message) {
                view.showMessage(message);
            }
        });
    }

    public void onRecipeClicked(Recipe recipe) {
        view.showMessage("Selected: " + recipe.getName());
    }
}
