package info.forkit.recipelist;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import butterknife.BindView;
import info.forkit.base.BaseActivity;
import info.forkit.R;
import info.forkit.model.objects.Recipe;
import info.forkit.addrecipe.AddRecipeActivity;

public class RecipeListActivity extends BaseActivity implements RecipeListView, RecipeListAdapter.ListItemCallback {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.fab_add_recipe)
    FloatingActionButton fabAdd;

    private RecipeListAdapter recipeListAdapter;
    private RecipeListPresenter presenter;

    @Override
    public int getLayout() {
        return R.layout.activity_recipe_list;
    }

    @Override
    protected void setUpView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeListActivity.this, AddRecipeActivity.class);
                startActivity(intent);
            }
        });
        recipeListAdapter = new RecipeListAdapter(this);
        recyclerView.setAdapter(recipeListAdapter);

        attachPresenter();
    }

    private void attachPresenter() {
        presenter = (RecipeListPresenter) getLastCustomNonConfigurationInstance();
        if (presenter == null) {
            presenter = new RecipeListPresenter(this);
        } else {
            presenter.reattachView(this);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        presenter.onActivityRestarted();
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
    public void setRecipes(ArrayList<Recipe> recipes) {
        recipeListAdapter.setRecipes(recipes);
    }

    @Override
    public void showMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Adapter methods
     */

    @Override
    public void listItemClicked(Recipe recipe) {
        presenter.onRecipeClicked(recipe);
    }
}
