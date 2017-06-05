package info.forkit;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity {

    private final String TAG = ListActivity.this.getClass().getSimpleName();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.fab_add_recipe)
    FloatingActionButton fabAdd;

    private RecipeListAdapter recipeListAdapter;
    private DatabaseReference recipesDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        setUpView();

        // todo - presenter
        recipesDatabase = ForkIt.getInstance().getDatabase().getReference("list").child("recipes");
//
        // todo - presenter
        recipesDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.VISIBLE);

                Log.d(TAG, "Value is: " + dataSnapshot.toString());
                recipeListAdapter.clearRecipes();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Recipe recipe = ds.getValue(Recipe.class);
                    recipeListAdapter.addRecipe(recipe);
                }
                recipeListAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    private void setUpView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ListActivity.this, "Create new Recipe", Toast.LENGTH_SHORT).show();
            }
        });
        recipeListAdapter = new RecipeListAdapter();
        recyclerView.setAdapter(recipeListAdapter);
    }

}
