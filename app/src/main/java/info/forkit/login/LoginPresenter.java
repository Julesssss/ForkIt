package info.forkit.login;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuthException;

import info.forkit.model.database.FirebaseHelper;

public class LoginPresenter {

    private static final String TAG = LoginPresenter.class.getSimpleName();

    private final String ERROR_WRONG_PASSWORD = "ERROR_WRONG_PASSWORD";

    private LoginView view;

    public LoginPresenter(LoginView view) {
        this.view = view;
    }

    public void reattachView(LoginView recipeListActivity) {
        this.view = recipeListActivity;
    }

    public void detachView() {
        this.view = null;
    }

    public void loginPressed(String email, String pass) {
        boolean validated = true;

        if (!isValidEmail(email)) {
            view.setInvalidEmail();
            validated = false;
        }
        if (!isValidPassword(pass)) {
            view.setInvalidPassword();
            validated = false;
        }
        if (validated) {
            view.setProgressVisibility(View.VISIBLE);
            new FirebaseHelper().loginUser(email, pass, task -> {
                if (task.isSuccessful()) {
                    view.openRecipePage();
                } else {
                    view.showMessage(task.getResult().toString());
                    view.setLoginViewVisibility(View.VISIBLE);
                    view.setProgressVisibility(View.GONE);
                }
            }, exception -> {
                view.setLoginViewVisibility(View.VISIBLE);
                view.setProgressVisibility(View.GONE);
                if (exception instanceof FirebaseAuthException) {
                    Log.i(TAG, "FirebaseAuthError: " + ((FirebaseAuthException) exception).getErrorCode());
                    if (((FirebaseAuthException) exception).getErrorCode().equals(ERROR_WRONG_PASSWORD)) {
                        view.setIncorrectPassword();
                    } else {
                        view.showMessage(exception.getLocalizedMessage());
                    }
                } else {
                    view.showMessage(exception.getLocalizedMessage());
                }
            });
        }
    }

    private boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) return false;
        if (!email.contains("@")) return false;
        return true;
    }

    private boolean isValidPassword(String password) {
        if (TextUtils.isEmpty(password)) return false;
        if (password.length() < 6) return false;
        return true;
    }

}
