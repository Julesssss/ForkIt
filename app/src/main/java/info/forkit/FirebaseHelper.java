package info.forkit;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {

    interface LoadRecipesCallback {
        void getRecipes(ArrayList<Recipe> recipes);
        void onCancelled(String message);
    }

    public void getRecipeList(final LoadRecipesCallback callback) {
        DatabaseReference recipesDatabase = ForkIt.getInstance().getDatabase().getReference(FirebaseKeys.TABLE_LIST).child(FirebaseKeys.TABLE_RECIPES);
        recipesDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Recipe> recipes = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Recipe recipe = ds.getValue(Recipe.class);
                    recipes.add(recipe);
                }
                callback.getRecipes(recipes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCancelled(databaseError.getMessage());
            }
        });
    }

    interface SaveRecipeCallback {
        void onCompleted();
    }

    public void saveNewRecipe(final Recipe recipe, final SaveRecipeCallback callback) {
        DatabaseReference recipesDatabase = ForkIt.getInstance().getDatabase().getReference(FirebaseKeys.TABLE_LIST).child(FirebaseKeys.TABLE_RECIPES).push();
        recipe.setId(recipesDatabase.getKey());
        recipesDatabase.setValue(recipe, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                callback.onCompleted();
            }
        });

    }
}
