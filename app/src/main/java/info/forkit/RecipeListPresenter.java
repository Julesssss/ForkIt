package info.forkit;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecipeListPresenter {

    private static final String TAG = RecipeListPresenter.class.getSimpleName();

    private RecipeListView view;

    public RecipeListPresenter(RecipeListView view) {
        this.view = view;
        getDataSet();
    }

    public void reattachView(RecipeListActivity recipeListActivity) {
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
        // todo: refactor this to FB helper
        DatabaseReference recipesDatabase = ForkIt.getInstance().getDatabase().getReference("list").child("recipes");
        recipesDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Recipe> recipes = new ArrayList<>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Recipe recipe = ds.getValue(Recipe.class);
                    recipes.add(recipe);
                }
                view.setRecipes(recipes);
                view.showProgressBar(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Database error: ", databaseError.toException());
                view.showMessage(databaseError.getMessage());
            }
        });
    }

}
