package info.forkit.recipelist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import info.forkit.R;
import info.forkit.model.objects.Recipe;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListViewHolder> {

    final private ListItemCallback callback;
    private ArrayList<Recipe> list = new ArrayList<>();

    interface ListItemCallback {
        void listItemClicked(Recipe recipe);
    }

    public RecipeListAdapter(ListItemCallback callback) {
        this.callback = callback;
    }

    @Override
    public RecipeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_recipe, parent, false);
        return new RecipeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeListViewHolder holder, final int position) {
        holder.setText(list.get(position).getName());
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.listItemClicked(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        list = recipes;
        notifyDataSetChanged();
    }
}
