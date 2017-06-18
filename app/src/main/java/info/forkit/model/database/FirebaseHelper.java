package info.forkit.model.database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import info.forkit.ForkIt;
import info.forkit.R;
import info.forkit.model.objects.Recipe;

public class FirebaseHelper {

    private static String TAG = FirebaseHelper.class.getSimpleName();

    public interface LoadRecipesCallback {
        void getRecipes(ArrayList<Recipe> recipes);
        void onCancelled(String message);
    }

    public void getRecipeList(final String userId, final LoadRecipesCallback callback) {
        DatabaseReference recipesDatabase = ForkIt.getInstance().getDatabase().getReference(FirebaseKeys.TABLE_RECIPES).child(userId);
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

    public interface SaveRecipeCallback {
        void onCompleted();
    }

    public void saveNewRecipe(final Recipe recipe, final String userId, final SaveRecipeCallback callback) {
        DatabaseReference recipesDatabase = ForkIt.getInstance().getDatabase().getReference(FirebaseKeys.TABLE_RECIPES).child(userId).push();
        recipe.setId(recipesDatabase.getKey());
        recipesDatabase.setValue(recipe, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                callback.onCompleted();
            }
        });
    }

    public interface LoginUserUserCallback {
        void onComplete(Task<AuthResult> task);
        void onFailure(Exception exception);
    }

    public void loginFirebaseUser(String email, String password, final LoginUserUserCallback callback) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                if (task.isSuccessful()) {
                    callback.onComplete(task);
                } else {
                    callback.onFailure(task.getException());
                }
            }
        });
    }
}
