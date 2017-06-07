package info.forkit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;

public class RecipeListActivity extends BaseActivity implements RecipeListView {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.fab_add_recipe)
    FloatingActionButton fabAdd;

    private RecipeListAdapter recipeListAdapter;
    private RecipeListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_list;
    }

    @Override
    protected void setUpView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecipeListActivity.this, "Create new Recipe", Toast.LENGTH_SHORT).show();
            }
        });
        recipeListAdapter = new RecipeListAdapter();
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


}
