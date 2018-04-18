package com.example.zedcrest.medmanager;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddMedicationActivity extends AppCompatActivity {

    int time;
    TextView textName;
    EditText addName_et, s_date;
    EditText alarmVal, desc;
    Spinner category;
    Button addBtn;
    String description,interval,start_date,end_date;
    DatePicker pickerDate;
    TimePicker pickerTime;
    Button setAlarm;
    TextView info;

   public DatabaseReference medicationDatabase;
    private static final String TAG = MainActivity.class.getSimpleName();
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        medicationDatabase = FirebaseDatabase.getInstance().getReference("med");
        info = (TextView)findViewById(R.id.info);
      //  description = "3";
        interval ="2";
        start_date="1";
        end_date = "44";

       // EditText  description_et= (EditText)findViewById(R.id.med_description);
      //  description = description_et.getText().toString().trim();




        pickerDate = (DatePicker)findViewById(R.id.pickerdate);
        pickerTime = (TimePicker)findViewById(R.id.pickertime);
        setAlarm = (Button)findViewById(R.id.setalarm) ;

        Calendar now = Calendar.getInstance();

        pickerDate.init(
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH),
                null);

        pickerTime.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
        pickerTime.setCurrentMinute(now.get(Calendar.MINUTE));


        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar current = Calendar.getInstance();

                Calendar cal = Calendar.getInstance();
                cal.set(pickerDate.getYear(),
                        pickerDate.getMonth(),
                        pickerDate.getDayOfMonth(),
                        pickerTime.getCurrentHour(),
                        pickerTime.getCurrentMinute(),
                        00);

                if(cal.compareTo(current) <= 0){
                    //The set Date/Time already passed
                    Toast.makeText(getApplicationContext(),
                            "Invalid Date/Time",
                            Toast.LENGTH_LONG).show();
                }else{
                    setAlarm(cal);
                }

            }
            }
        );



        // alarm

   /*     AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        long currentTime = System.currentTimeMillis();
        long oneMinute = 60 * 1000;
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                currentTime + oneMinute,
                oneMinute,
                pendingIntent);*/



        alarmVal = (EditText) findViewById(R.id.etVal);
        desc = (EditText) findViewById(R.id.enter_desc) ;
        s_date = (EditText)findViewById(R.id.start_date) ;

        textName = findViewById(R.id.textName);
        addName_et = findViewById(R.id.enter_name);
        category = findViewById(R.id.category);
        addBtn = findViewById(R.id.addBtn);

    /*    s_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getApplicationContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    }
                }, hour, minute, false);

                timePickerDialog.show();
            }
        });*/






        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMedication();


                time = Integer.parseInt(alarmVal.getText().toString());
                Intent intent=new Intent(AddMedicationActivity.this, AlarmReceiver.class);
                PendingIntent p1=PendingIntent.getBroadcast(getApplicationContext(),0, intent,0);
                AlarmManager a=(AlarmManager)getSystemService(ALARM_SERVICE);
                a.set(AlarmManager.RTC,System.currentTimeMillis() + time*1000,p1);
            }
        });

    }

    private void setAlarm(Calendar targetCal){

        info.setText("\n\n***\n"
                + "Alarm is set@ " + targetCal.getTime() + "\n"
                + "***\n");

        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(),0, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
    }









    public void addMedication(){
        String name = addName_et.getText().toString().trim();
        String categories = "categories";
        String d = desc.getText().toString().trim();
        if(!TextUtils.isEmpty(name)){
                String id  = medicationDatabase.push().getKey();

                Medication medication = new Medication(id,name,categories,d,interval,start_date,end_date);
                medicationDatabase.child(id).setValue(medication);
            Toast.makeText(this, "Medication added", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Enter a name", Toast.LENGTH_SHORT).show();
        }
    }
}
