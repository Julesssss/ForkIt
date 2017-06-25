package info.forkit.model.objects;

public class Ingredient {

    private String name;
    private String id;

    public Ingredient() {
    }

    public Ingredient(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
