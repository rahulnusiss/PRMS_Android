package sg.edu.nus.iss.phoenix.maintainuser.android.controller;

import android.content.Intent;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.maintainuser.android.delegate.*;
import sg.edu.nus.iss.phoenix.maintainuser.android.ui.*;
import sg.edu.nus.iss.phoenix.maintainuser.entity.User;
import sg.edu.nus.iss.phoenix.schedule.android.ui.ScheduledProgramScreen;

/**
 * Created by jackle on 27/9/17.
 */

public class ReviewSelectPresenterProducerController {
    private ReviewSelectPresenterProducerScreen m_pListScreen;
    private ScheduledProgramScreen m_maintainScheduleScreen;
    private User m_User;
    private String userType;

    public ReviewSelectPresenterProducerController(){

    }

    public void finalize() throws Throwable {

    }

    /**
     *
     * @param screen
     */
    public void onDisplayUserList(ReviewSelectPresenterProducerScreen screen){
        this.m_pListScreen = screen;
        if(this.userType == "producer")
        new RetrievePresentersProducersDelegate(this).execute("allProducers");

        if(this.userType == "presenter")
            new RetrievePresentersProducersDelegate(this).execute("allPresenters");
    }

    public void startUseCase(String userType, ScheduledProgramScreen m_maintainScheduleScreen){
        m_User = null;
        this.m_maintainScheduleScreen = m_maintainScheduleScreen;
        this.userType = userType;
        Intent intent = new Intent(MainController.getApp(), ReviewSelectPresenterProducerScreen.class);
        MainController.displayScreen(intent);
    }

    public void presenterProducerRetrieved(List<User> users) {
        m_pListScreen.showUsers(users);
    }

    public void userSelected(User user){
        if(userType == "presenter") {
            m_maintainScheduleScreen.presenterRetrieved(user);
        }

        if(userType == "producer") {
            m_maintainScheduleScreen.producerRetrieved(user);
        }

        m_pListScreen.finish();
    }
}
