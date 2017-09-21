package sg.edu.nus.iss.phoenix.authenticate.android.controller;

import android.content.Intent;
import android.os.Bundle;

import sg.edu.nus.iss.phoenix.authenticate.android.delegate.LoginDelegate;
import sg.edu.nus.iss.phoenix.authenticate.android.ui.LoginScreen;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.ui.MainScreen;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;

public class LoginController {
    // Tag for logging.
    private static final String TAG = LoginController.class.getName();

    private LoginScreen loginScreen;

    public void onDisplay(LoginScreen loginScreen) {
        this.loginScreen = loginScreen;
    }

    public void login(String userName, String password) {
        loginScreen.showLoadingIndicator();
        // Below comment should be removed after connection with backend
        //new LoginDelegate(this).execute(userName, password);
        // Below line should be removed after connection with backend
        loggedIn(true, userName);
    }

    public void loggedIn(boolean success, String username) {
        loginScreen.hideLoadingIndicator();
        if (!success) { loginScreen.showErrorMessage(); return; }

        ControlFactory.getMainController().startUseCase(username);
    }

    public void logout() {
        Intent intent = new Intent(MainController.getApp(), LoginScreen.class);
        MainController.displayScreen(intent);
    }
}
