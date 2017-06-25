package info.forkit.model.objects;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private String name;
    private String id;
    private List<Ingredient> ingredients;

    public Recipe() {
    }

    public Recipe(String name) {
        this.name = name;
        ingredients = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
