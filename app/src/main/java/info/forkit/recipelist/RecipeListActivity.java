package info.forkit.recipelist;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import butterknife.BindView;
import info.forkit.ForkIt;
import info.forkit.R;
import info.forkit.addrecipe.AddRecipeActivity;
import info.forkit.base.BaseActivity;
import info.forkit.login.LoginActivity;
import info.forkit.model.database.FirebaseHelper;
import info.forkit.model.objects.Recipe;

public class RecipeListActivity extends BaseActivity implements RecipeListView, RecipeListAdapter.ListItemCallback {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.progress_view)
    ProgressBar progressBar;

    @BindView(R.id.fab_add_recipe)
    FloatingActionButton fabAdd;

    @BindView(R.id.empty_view)
    View emptyView;

    private RecipeListAdapter recipeListAdapter;
    private RecipeListPresenter presenter;

    @Override
    public int getLayout() {
        return R.layout.activity_recipe_list;
    }

    @Override
    protected void setUpView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recipeListAdapter = new RecipeListAdapter(this);
        recyclerView.setAdapter(recipeListAdapter);
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(RecipeListActivity.this, AddRecipeActivity.class);
            startActivity(intent);
        });

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
    public void setRecipes(ArrayList<Recipe> recipes) {
        recipeListAdapter.setRecipes(recipes);
    }

    @Override
    public void showEmptyMessage(boolean visible) {
        if (visible) emptyView.setVisibility(View.VISIBLE);
        else emptyView.setVisibility(View.GONE);
    }

    /**
     * Adapter methods
     */

    @Override
    public void listItemClicked(Recipe recipe) {
        presenter.onRecipeClicked(recipe);
    }

    /**
     * Settings methods
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            new FirebaseHelper().logoutUser();
            ForkIt.getInstance().clearUser();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
