package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.maintainuser.entity.User;

public class ScheduledProgramScreen extends AppCompatActivity {

    // Tag for logging
    private static final String TAG = ScheduledProgramScreen.class.getName();

    private EditText radioPSName;
    private EditText radioPSDateofPr;
    private EditText radioPSDuration;
    private EditText radioPSStartTime;
    private Button btnSelectProducer;
    private Button btnSelectPresenter;
    private ProgramSlot ps2edit = null;
    private User selectedUser = null;
    KeyListener mRPNameEditTextKeyListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_program_screen);
        // Find all relevant views that we will need to read user input from
        radioPSName = (EditText) findViewById(R.id.maintain_schedule_name_text_view);
        radioPSDateofPr = (EditText) findViewById(R.id.maintain_schedule_dateOfPr_text_view);
        radioPSDuration = (EditText) findViewById(R.id.maintain_schedule_duration_text_view);
        radioPSStartTime = (EditText) findViewById(R.id.maintain_schedule_starttime_text_view);
        btnSelectProducer = (Button) findViewById(R.id.maintain_schedule_producer_button);
        btnSelectPresenter = (Button) findViewById(R.id.maintain_schedule_presenter_button);

        btnSelectProducer.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                ControlFactory.getReviewSelectPresenterProducerController().startUseCase("producer", (ScheduledProgramScreen)view.getContext());
            }
        });
        btnSelectPresenter.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                ControlFactory.getReviewSelectPresenterProducerController().startUseCase("presenter", (ScheduledProgramScreen)view.getContext());
            }
        });
        // Keep the KeyListener for name EditText so as to enable editing after disabling it.
        mRPNameEditTextKeyListener = radioPSName.getKeyListener();

    }

    public void createSchedule() {
        this.ps2edit = null;
        radioPSName.setText("", TextView.BufferType.EDITABLE);
        radioPSDateofPr.setText("", TextView.BufferType.EDITABLE);
        radioPSDuration.setText("", TextView.BufferType.EDITABLE);
        radioPSStartTime.setText("", TextView.BufferType.EDITABLE);
        radioPSName.setKeyListener(mRPNameEditTextKeyListener);
    }

    public void presenterRetrieved(User user) {
        selectedUser = user;
        btnSelectPresenter.setText(user.getUserName() + "(presenter)");
    }

    public void producerRetrieved(User user) {
        selectedUser = user;
        btnSelectProducer.setText(user.getUserName() + "(producer)");
    }
}
