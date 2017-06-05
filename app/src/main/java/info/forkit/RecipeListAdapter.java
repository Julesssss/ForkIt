package info.forkit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private ArrayList<Recipe> list = new ArrayList<>();

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, final int position) {
        holder.setText(list.get(position).getName());
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ForkIt.getInstance(), list.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addRecipe(Recipe recipe) {
        list.add(recipe);
        notifyDataSetChanged();
    }

    public void clearRecipes() {
        list.clear();
        notifyDataSetChanged();
    }

}
