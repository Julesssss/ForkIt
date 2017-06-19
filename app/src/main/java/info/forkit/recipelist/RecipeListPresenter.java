package info.forkit.recipelist;

import info.forkit.ForkIt;
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
        firebaseHelper.getRecipeList(ForkIt.getInstance().getUser().getUid(), recipes -> {
            view.showProgressBar(false);
            view.setRecipes(recipes);
            if (recipes.size() > 0) view.showEmptyMessage(false);
            else view.showEmptyMessage(true);
        }, message -> {
            view.showMessage(message);
            view.showProgressBar(false);
        });
    }

    public void onRecipeClicked(Recipe recipe) {
        view.showMessage("Selected: " + recipe.getName());
    }
}
