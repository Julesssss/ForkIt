package info.forkit.model.objects;

public class Recipe {

    private String name;
    private String id;

    public Recipe() {
    }

    public Recipe(String name) {
        this.name = name;
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
}
