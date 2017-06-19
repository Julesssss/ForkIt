package info.forkit.model.database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.ArrayList;

import info.forkit.model.objects.Recipe;

public class FirebaseCallbacks {

    public interface LoadRecipesSuccess {
        void getRecipes(ArrayList<Recipe> recipes);
    }

    public interface LoadRecipesFail {
        void onFail(String message);
    }

    public interface SaveRecipe {
        void onCompleted();
    }

    public interface LoginSuccess {
        void onSuccess(Task<AuthResult> task);
    }

    public interface LoginFail {
        void onFailure(Exception exception);
    }

}
