package info.forkit.addrecipe;

import android.text.TextUtils;

import info.forkit.ForkIt;
import info.forkit.model.database.FirebaseHelper;
import info.forkit.R;
import info.forkit.model.objects.Recipe;

public class AddRecipePresenter {

    private static final String TAG = AddRecipePresenter.class.getSimpleName();

    private AddRecipeView view;

    public AddRecipePresenter(AddRecipeView view) {
        this.view = view;
    }

    public void reattachView(AddRecipeView recipeListActivity) {
        this.view = recipeListActivity;
    }

    public void detachView() {
        this.view = null;
    }

    public void saveNewRecipe(String name) {
        if (TextUtils.isEmpty(name)) {
            view.showMessage(R.string.add_recipe_message_name_empty);
            return;
        }
        view.showProgressBar(true);
        Recipe recipe = new Recipe(name);
        FirebaseHelper firebaseHelper = new FirebaseHelper();
        firebaseHelper.saveNewRecipe(recipe, ForkIt.getInstance().getUser().getUid(), new FirebaseHelper.SaveRecipeCallback() {
            @Override
            public void onCompleted() {
                view.showMessage(R.string.add_recipe_message_completed);
                view.showProgressBar(false);
            }
        });
    }
}
