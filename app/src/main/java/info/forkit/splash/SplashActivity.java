package info.forkit.splash;

import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;

import info.forkit.R;
import info.forkit.base.BaseActivity;
import info.forkit.login.LoginActivity;
import info.forkit.recipelist.RecipeListActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void setUpView() {
        // not necessary
    }

    @Override
    public void showProgressBar(boolean visible) {
        // not necessary
    }

    @Override
    protected void setUpFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = firebaseAuth -> {
            firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                startActivity(new Intent(this, RecipeListActivity.class));
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
            finish();
        };
    }

}
