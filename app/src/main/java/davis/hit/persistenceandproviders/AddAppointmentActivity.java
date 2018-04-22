package davis.hit.persistenceandproviders;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddAppointmentActivity extends AppCompatActivity {

    EditText patient_name;
    EditText time;
    Button button;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                .build();

        patient_name = (EditText) findViewById(R.id.name);
        time = (EditText) findViewById(R.id.time);
        button = (Button) findViewById(R.id.button);


        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                .build();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!patient_name.getText().toString().equals("") && !time.getText().toString().equals("")) {

                    final AppointmentListItem appointmentListItem = new AppointmentListItem(patient_name.getText().toString(), time.getText().toString());
                    //save the item before leaving the activity


                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            db.databaseInterface().insertAll(appointmentListItem);
                        }
                    });

                    db.close();

                    Intent i = new Intent(AddAppointmentActivity.this, ViewAppointmentsActivity.class);
                    startActivity(i);

                    finish();
                }
            }
        });
    }
}