package info.forkit.addrecipe;

import android.text.TextUtils;

import java.util.List;

import info.forkit.ForkIt;
import info.forkit.model.database.FirebaseHelper;
import info.forkit.R;
import info.forkit.model.objects.Ingredient;
import info.forkit.model.objects.Recipe;

class AddRecipePresenter {

    private static final String TAG = AddRecipePresenter.class.getSimpleName();

    private AddRecipeView view;

    AddRecipePresenter(AddRecipeView view) {
        this.view = view;
    }

    void reattachView(AddRecipeView recipeListActivity) {
        this.view = recipeListActivity;
    }

    void detachView() {
        this.view = null;
    }

    void saveNewRecipe(String name) {
        if (TextUtils.isEmpty(name)) {
            view.showMessage(R.string.add_recipe_message_name_empty);
            return;
        }
        view.showProgressBar(true);

        Recipe recipe = createRecipe(name);

        FirebaseHelper firebaseHelper = new FirebaseHelper(); // TODO: 20/06/2017 DepInj!
        firebaseHelper.saveNewRecipe(recipe, ForkIt.getInstance().getUser().getUid(), () -> {
            view.showMessage(R.string.add_recipe_message_completed);
            view.showProgressBar(false);
        });
    }

    private Recipe createRecipe(String name) {
        Recipe recipe = new Recipe(name);
        List<Ingredient> ingredients = view.getIngredients();
        recipe.setIngredients(ingredients);
        return recipe;
    }

    public void addIngredientPressed(String ingredientName) {
        if (TextUtils.isEmpty(ingredientName)) {
            view.noRecipeEntered();
            return;
        }
        Ingredient ingredient = new Ingredient(ingredientName);
        view.addIngredient(ingredient);
    }
}
