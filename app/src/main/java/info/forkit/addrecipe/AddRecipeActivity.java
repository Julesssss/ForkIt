package info.forkit.addrecipe;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import butterknife.BindView;
import info.forkit.R;
import info.forkit.base.BaseActivity;
import info.forkit.model.objects.Ingredient;

public class AddRecipeActivity extends BaseActivity implements AddRecipeView, View.OnClickListener, RecipeIngredientAdapter.AddIngredientCallback {

    @BindView(R.id.button_save)
    Button buttonSave;

    @BindView(R.id.edittext_recipe_name)
    EditText editRecipeName;

    @BindView(R.id.edittext_add_ingredient)
    EditText editAddIngredient;

    @BindView(R.id.recycler_view_ingredients)
    RecyclerView recyclerViewIngredients;

    @BindView(R.id.button_add_ingredient)
    Button buttonAddIngredient;

    @BindView(R.id.progress_view)
    View progressBar;

    private AddRecipePresenter presenter;
    private RecipeIngredientAdapter recipeIngredientAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_add_recipe;
    }

    @Override
    protected void setUpView() {
        buttonSave.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerViewIngredients.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recipeIngredientAdapter = new RecipeIngredientAdapter(this);
        recyclerViewIngredients.setAdapter(recipeIngredientAdapter);
        buttonAddIngredient.setOnClickListener(v -> {
            presenter.addIngredientPressed(editAddIngredient.getText().toString());
            editAddIngredient.setText("");
        });

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
        runOnUiThread(() -> {
            if (progressBar != null) {
                if (visible) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
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

    /**
     * Ingredient Adapter
     */

    @Override
    public void listItemClicked(Ingredient ingredient) {
        // sent to presenter -> FB
    }

    @Override
    public ArrayList<Ingredient> getIngredients() {
        return recipeIngredientAdapter.getIngredients();
    }

    @Override
    public void addIngredient(Ingredient ingredient) {
        recipeIngredientAdapter.addIngredient(ingredient);
    }

    @Override
    public void noRecipeEntered() {
        editAddIngredient.setError(getString(R.string.error_ingredient_empty));
    }
}
