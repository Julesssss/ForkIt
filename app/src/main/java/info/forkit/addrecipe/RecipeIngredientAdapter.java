package info.forkit.addrecipe;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import info.forkit.R;
import info.forkit.model.objects.Ingredient;

public class RecipeIngredientAdapter extends RecyclerView.Adapter<RecipeIngredientViewHolder> {

    final private AddIngredientCallback callback;
    private ArrayList<Ingredient> ingredients = new ArrayList<>();

    interface AddIngredientCallback {
        void listItemClicked(Ingredient ingredient);
    }

    public RecipeIngredientAdapter(AddIngredientCallback callback) {
        this.callback = callback;
    }

    @Override
    public RecipeIngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_ingredient, parent, false);
        return new RecipeIngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeIngredientViewHolder holder, final int position) {
        holder.setText(ingredients.get(position).getName());
        holder.setOnClickListener(v -> callback.listItemClicked(ingredients.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
        notifyDataSetChanged();
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }
}
