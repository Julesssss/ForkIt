package info.forkit;

public class Recipe {

    private String name;
    private int id;

    public Recipe() {
    }

    public Recipe(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
