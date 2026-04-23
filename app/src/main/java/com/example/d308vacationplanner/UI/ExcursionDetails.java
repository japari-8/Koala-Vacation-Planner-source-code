package com.example.d308vacationplanner.UI;

import static android.content.ContentValues.TAG;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

import android.app.AlarmManager;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d308vacationplanner.Database.Repository;
import com.example.d308vacationplanner.Entities.Excursion;
import com.example.d308vacationplanner.Entities.Vacation;
import com.example.d308vacationplanner.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ExcursionDetails extends AppCompatActivity {
    EditText editTitle;
    EditText editDate;
    Long excursionId;
    Long vacationIdEx;
    Long assocVacationId;
    String excursionTitle;
    String excursionDate;
    Repository repository;
    DatePickerDialog.OnDateSetListener pickDate;
    final Calendar mCalender = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursion_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Excursion excursion;

        editTitle = findViewById(R.id.excursiontitle);
        editDate = findViewById(R.id.excursiondate);

        excursionId = getIntent().getLongExtra("excursionId", -1);
        excursionTitle = getIntent().getStringExtra("title");
        excursionDate = getIntent().getStringExtra("date");
        vacationIdEx = getIntent().getLongExtra("vacationId", -1);

        assocVacationId = getIntent().getLongExtra("associatedVacationId", -1);

        //Log.d(TAG, "assocVacaIdFromVcaDetFab " + assocVacationId);

        editTitle.setText(excursionTitle);
        editDate.setText(excursionDate);
        editDate.setFocusable(false);
        editDate.setClickable(true);

        if (excursionId == -1) {
            String currentDate = sdf.format(new Date());
            editDate.setText(currentDate);
        }

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = editDate.getText().toString();

                try {
                    mCalender.setTime(Objects.requireNonNull(sdf.parse(info)));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                new DatePickerDialog(ExcursionDetails.this, pickDate, mCalender.get(Calendar.YEAR),
                        mCalender.get(Calendar.MONTH), mCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        pickDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalender.set(Calendar.YEAR, year);
                mCalender.set(Calendar.MONTH, month);
                mCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateDate(editDate);
            }
        };


        Button saveButton = findViewById(R.id.saveButtonEx);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date exDate;
                try {
                    exDate = sdf.parse(editDate.getText().toString());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                Vacation vacation;
                if (excursionId == -1) {
                    try {
                        repository = new Repository(getApplication());
                        vacation = repository.getVacationById(assocVacationId);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    try {
                        repository = new Repository(getApplication());
                        vacation = repository.getVacationById(vacationIdEx);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                Date vacationStDate = vacation.getStartDate();
                Date vacationSEdDate = vacation.getEndDate();

                assert exDate != null;
                if (exDate.before(vacationStDate) || (exDate.after(vacationSEdDate))) {
                    Toast.makeText(ExcursionDetails.this, "This date must be during the vacation dates", Toast.LENGTH_LONG).show();
                }

//                Log.d(TAG, "MYvacationStDate " + vacationStDate);
//                Log.d(TAG, "MYvacationEdDate " + vacationSEdDate);
//                Log.d(TAG, "MYexcursionEdDate " + exDate);


                else {
                    if (excursionId == -1) {
                        Excursion excursion = new Excursion(0, editTitle.getText().toString(), exDate, assocVacationId);
                        try {
                            repository = new Repository(getApplication());
                            repository.addExcursion(excursion);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        finish();
                    } else {
                        Excursion excursion = new Excursion(excursionId, editTitle.getText().toString(), exDate, vacationIdEx);
                        try {
                            repository = new Repository(getApplication());
                            repository.updateExcursion(excursion);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        finish();
                    }
                }
            }

        });

    }

    private void updateDate(EditText editText){
        editText.setText(sdf.format(mCalender.getTime()));
    }

     //This method creates an Actions bar menu.
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Date excursionDate;
        try {
            excursionDate = sdf.parse(editDate.getText().toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (item.getItemId() == android.R.id.home){
            finish();
            return  true;
        }

        if (item.getItemId() == R.id.deleteExcursion) {
            Excursion excursion;
            excursion = new Excursion(excursionId, editTitle.getText().toString(), excursionDate, vacationIdEx);
            try {
                repository = new Repository(getApplication());
                repository.deleteExcursion(excursion);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Toast.makeText(ExcursionDetails.this, editTitle.getText().toString() + " was deleted", Toast.LENGTH_LONG).show();
            this.finish();
        }

        if (item.getItemId() == R.id.setExcursionAlert) {
            assert excursionDate != null;
            scheduleAlert(excursionDate.getTime(), editTitle.getText().toString() + " excursion is today!");
        }
    return true;
    }

    private  void scheduleAlert(Long trigger, String st) {
        Date exDate;
        try {
            exDate = sdf.parse(editDate.getText().toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Date currDate = new Date();
        String stCurrDate = sdf.format(currDate);
        try {
            currDate = sdf.parse(stCurrDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        assert exDate != null;
        if (exDate.before(currDate)) {
            Toast.makeText(ExcursionDetails.this, "This date is in the past", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(ExcursionDetails.this, MyReceiver.class);
        intent.putExtra("key", st);
        PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this,
                ++MainActivity.numAlert, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
    }
}