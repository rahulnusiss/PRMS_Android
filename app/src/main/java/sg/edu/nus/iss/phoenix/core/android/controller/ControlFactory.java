package sg.edu.nus.iss.phoenix.core.android.controller;

import sg.edu.nus.iss.phoenix.authenticate.android.controller.LoginController;
import sg.edu.nus.iss.phoenix.radioprogram.android.controller.ProgramController;
import sg.edu.nus.iss.phoenix.radioprogram.android.controller.ReviewSelectProgramController;
import sg.edu.nus.iss.phoenix.schedule.android.controller.ScheduleController;
import sg.edu.nus.iss.phoenix.maintainuser.android.controller.*;

public class ControlFactory {
    private static MainController mainController = null;
    private static LoginController loginController = null;
    private static UserController userController = null;

    // Program
    private static ProgramController programController = null;
    private static ReviewSelectProgramController reviewSelectProgramController = null;
    private static ReviewSelectPresenterProducerController reviewSelectProducerPresenterController = null;

    // Schedule
    private static ScheduleController scheduleController = null;

    public static MainController getMainController() {
        if (mainController == null) mainController = new MainController();
        return mainController;
    }

    public static LoginController getLoginController() {
        if (loginController == null) loginController = new LoginController();
        return loginController;
    }

    public static ProgramController getProgramController() {
        if (programController == null) programController = new ProgramController();
        return programController;
    }

    public static ReviewSelectProgramController getReviewSelectProgramController() {
        if (reviewSelectProgramController == null) reviewSelectProgramController = new ReviewSelectProgramController();
        return reviewSelectProgramController;
    }

    public static ReviewSelectPresenterProducerController getReviewSelectPresenterProducerController() {
        if (reviewSelectProducerPresenterController == null) reviewSelectProducerPresenterController = new ReviewSelectPresenterProducerController();
        return reviewSelectProducerPresenterController;
    }

    public static UserController getUserController(){
        if (userController == null) userController = new UserController();
        return userController;
    }

    // Schedule use case functions
    public static ScheduleController getScheduleController() {
        if (scheduleController == null) scheduleController = new ScheduleController();
        return scheduleController;
    }

}
