package info.forkit.addrecipe;

public interface AddRecipeView {

    void showProgressBar(boolean visible);

    void showMessage(String message);

    void showMessage(int stringID);
}
