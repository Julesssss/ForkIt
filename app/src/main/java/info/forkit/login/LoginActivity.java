package info.forkit.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import info.forkit.R;
import info.forkit.base.BaseActivity;
import info.forkit.recipelist.RecipeListActivity;

public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener {

    @BindView(R.id.email)
    EditText editTextEmail;

    @BindView(R.id.password)
    EditText editTextPassword;

    @BindView(R.id.button_login)
    Button buttonLogin;

    @BindView(R.id.login_form)
    View loginView;

    @BindView(R.id.progress_view)
    View progressView;

    private LoginPresenter presenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void setUpView() {
        buttonLogin.setOnClickListener(this);
        attachPresenter();
    }

    @Override
    public void showProgressBar(boolean visible) {
        // TODO: 17/06/2017
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                hideKeyboard();
                String email = editTextEmail.getText().toString();
                String pass = editTextPassword.getText().toString();
                presenter.loginPressed(email, pass);
                break;
        }
    }

    @Override
    public void setInvalidEmail() {
        editTextEmail.setError(getString(R.string.error_invalid_email));
    }

    @Override
    public void setInvalidPassword() {
        editTextPassword.setError(getString(R.string.error_invalid_password));
    }

    @Override
    public void setIncorrectPassword() {
        editTextPassword.findFocus();
        editTextPassword.setError(getString(R.string.error_incorrect_password));
    }

    @Override
    public void openRecipePage() {
        startActivity(new Intent(this, RecipeListActivity.class));
        finish();
    }

    @Override
    public void setLoginViewVisibility(int visibility) {
        loginView.setVisibility(visibility);
    }

    @Override
    public void setProgressVisibility(int visibility) {
        progressView.setVisibility(visibility);
    }

    /*
     * Presenter Lifecycle
     */

    private void attachPresenter() {
        presenter = (LoginPresenter) getLastCustomNonConfigurationInstance();
        if (presenter == null) {
            presenter = new LoginPresenter(this);
        } else {
            presenter.reattachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }
}

