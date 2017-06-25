package info.forkit.addrecipe;

import java.util.ArrayList;

import info.forkit.base.BaseView;
import info.forkit.model.objects.Ingredient;

public interface AddRecipeView extends BaseView {

    ArrayList<Ingredient> getIngredients();

    void addIngredient(Ingredient ingredient);

    void noRecipeEntered();
}
