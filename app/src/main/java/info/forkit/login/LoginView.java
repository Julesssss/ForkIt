package info.forkit.login;

import info.forkit.base.BaseView;

public interface LoginView extends BaseView {

    void setInvalidEmail();

    void setInvalidPassword();

    void setIncorrectPassword();

    void openRecipePage();

    void setLoginViewVisibility(int visibility);

    void setProgressVisibility(int visibility);
}
