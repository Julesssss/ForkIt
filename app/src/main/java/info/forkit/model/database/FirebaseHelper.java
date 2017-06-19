package info.forkit.model.database;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import info.forkit.ForkIt;
import info.forkit.model.objects.Recipe;

public class FirebaseHelper extends FirebaseCallbacks {

    private static String TAG = FirebaseHelper.class.getSimpleName();

    public void getRecipeList(final String userId, final LoadRecipesSuccess callbackSuccess, final LoadRecipesFail callbackFailure) {
        DatabaseReference recipesDatabase = ForkIt.getInstance().getDatabase().getReference(FirebaseKeys.TABLE_RECIPES).child(userId);
        recipesDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Recipe> recipes = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Recipe recipe = ds.getValue(Recipe.class);
                    recipes.add(recipe);
                }
                callbackSuccess.getRecipes(recipes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callbackFailure.onFail(databaseError.getMessage());
            }
        });
    }

    public void saveNewRecipe(final Recipe recipe, final String userId, final SaveRecipe callback) {
        DatabaseReference recipesDatabase = ForkIt.getInstance().getDatabase().getReference(FirebaseKeys.TABLE_RECIPES).child(userId).push();
        recipe.setId(recipesDatabase.getKey());
        recipesDatabase.setValue(recipe, (databaseError, databaseReference) -> callback.onCompleted());
    }

    public void loginFirebaseUser(String email, String password, final LoginSuccess callbackSuccess, final LoginFail callbackFail) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
            if (task.isSuccessful()) {
                callbackSuccess.onSuccess(task);
            } else {
                callbackFail.onFailure(task.getException());
            }
        });
    }
}
