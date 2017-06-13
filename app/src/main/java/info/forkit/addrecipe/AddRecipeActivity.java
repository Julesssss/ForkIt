package info.forkit.addrecipe;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import butterknife.BindView;
import info.forkit.base.BaseActivity;
import info.forkit.R;

public class AddRecipeActivity extends BaseActivity implements AddRecipeView, View.OnClickListener {

    @BindView(R.id.button_save)
    Button buttonSave;

    @BindView(R.id.edittext_recipe_name)
    EditText editRecipeName;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private AddRecipePresenter presenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_add_recipe;
    }

    @Override
    protected void setUpView() {
        buttonSave.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        attachPresenter();
    }

    private void attachPresenter() {
        presenter = (AddRecipePresenter) getLastCustomNonConfigurationInstance();
        if (presenter == null) {
            presenter = new AddRecipePresenter(this);
        } else {
            presenter.reattachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    /*
     * This is how we maintain the Presenter state through rotation. Simple but fine for our usage
     */
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    /**
     * Interface methods
     */

    @Override
    public void showProgressBar(final boolean visible) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressBar != null) {
                    if (visible) {
                        progressBar.setVisibility(View.VISIBLE);
                    } else {
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    public void showMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(buttonSave, message, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showMessage(final int stringId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(buttonSave, getString(stringId), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_save:
                String name = editRecipeName.getText().toString();
                presenter.saveNewRecipe(name);
                break;
        }
    }
}
