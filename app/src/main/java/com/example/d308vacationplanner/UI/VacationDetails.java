package com.example.d308vacationplanner.UI;

import static android.content.ContentValues.TAG;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.Converter.DateString;
import com.example.d308vacationplanner.Database.Repository;
import com.example.d308vacationplanner.Entities.Excursion;
import com.example.d308vacationplanner.Entities.Vacation;
import com.example.d308vacationplanner.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class VacationDetails extends AppCompatActivity {
    EditText editTitle;
    EditText editHotel;
    EditText editStartDate;
    EditText editEndDate;
    Long vacationId;
    String vacationTitle;
    String vacationHotel;
    String vacationStDAte;
    String vacationEndDate;
    Repository repository;
    DatePickerDialog.OnDateSetListener pickStDate;
    DatePickerDialog.OnDateSetListener pickEndDate;
    final Calendar mCalender = Calendar.getInstance();
    DateString dateString1;
    DateString dateString2;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if(getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(
                    this, R.color.light_green)));
        }

        editTitle = findViewById(R.id.vacationtitle);
        editHotel = findViewById(R.id.vacationhotel);
        editStartDate = findViewById(R.id.vacationstartdate);
        editEndDate = findViewById(R.id.vacationenddate);

        vacationId = getIntent().getLongExtra("vacationId", -1);
        vacationTitle = getIntent().getStringExtra("title");
        vacationHotel = getIntent().getStringExtra("hotel");
        vacationStDAte = getIntent().getStringExtra("startDate");
        vacationEndDate = getIntent().getStringExtra("endDate");

        editTitle.setText(vacationTitle);
        editHotel.setText(vacationHotel);
        editStartDate.setText(vacationStDAte);
        editStartDate.setFocusable(false);
        editStartDate.setClickable(true);
        editEndDate.setText(vacationEndDate);
        editEndDate.setFocusable(false);
        editEndDate.setClickable(true);

        // RecyclerView with a list of Excursions
        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerview);
        repository = new Repository(getApplication());
        List<Excursion> allExcursions;
        try {
            allExcursions = repository.getmListAssociatedExcursions(vacationId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        excursionAdapter.setmExcursions(allExcursions);


        FloatingActionButton fab = findViewById(R.id.excursionFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("associatedVacationId", vacationId);
                startActivity(intent);
            }
        });

        if (vacationId == -1) {
            String currentDate = sdf.format(new Date());
            editStartDate.setText(currentDate);
        }

        // This method creates a datePicker for a vacation start date.
        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = editStartDate.getText().toString();

                try {
                    mCalender.setTime(Objects.requireNonNull(sdf.parse(info)));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                new DatePickerDialog(VacationDetails.this, pickStDate, mCalender.get(Calendar.YEAR),
                        mCalender.get(Calendar.MONTH), mCalender.get(Calendar.DAY_OF_MONTH)).show();
            }

        });

        pickStDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalender.set(Calendar.YEAR, year);
                mCalender.set(Calendar.MONTH, month);
                mCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateDate(editStartDate);

            }
        };

        if (vacationId == -1) {
            String currentDate = sdf.format(new Date());
            editEndDate.setText(currentDate);
        }

        // This method creates a datePicker for a vacation End date.
        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = editEndDate.getText().toString();

                try {
                    mCalender.setTime(Objects.requireNonNull(sdf.parse(info)));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                new DatePickerDialog(VacationDetails.this, pickEndDate, mCalender.get(Calendar.YEAR),
                        mCalender.get(Calendar.MONTH), mCalender.get(Calendar.DAY_OF_MONTH)).show();

            }

        });

        pickEndDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalender.set(Calendar.YEAR, year);
                mCalender.set(Calendar.MONTH, month);
                mCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateDate(editEndDate);

            }
        };

        // This method is called when the Save button is clicked. This method adds or updates a vacation.
        // It also checks if the end date is after the start date.
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vacation vacation;
                Date dateSt;
                Date dateEd;

                String flag = convertCtryToFlag(editTitle.getText().toString());

                try {
                    dateString1 = new DateString();
                    dateSt = dateString1.stringToDate(editStartDate.getText().toString());
                    dateString2 = new DateString();
                    dateEd = dateString2.stringToDate(editEndDate.getText().toString());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                if(dateSt.after(dateEd)) {
                    Toast.makeText(VacationDetails.this, "The End date cannot be before the " +
                            "Start date", Toast.LENGTH_LONG).show();
                }
                else {
                    if (vacationId == -1) {
                        try {
                            if (repository.getmListVacations().isEmpty()) {
                                vacationId = (long) 1;
                            } else {
                                vacationId = repository.getmListVacations().get(repository.getmListVacations().size() - 1).getVacationId() + 1;
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        vacation = new Vacation(vacationId, editTitle.getText().toString() + " " + flag, editHotel.getText().toString(), dateSt, dateEd);
                        try {
                            repository.addVacation(vacation);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        finish();
                    }
                    else {
                        vacation = new Vacation(vacationId, editTitle.getText().toString(), editHotel.getText().toString(), dateSt, dateEd);
                        try {
                            repository.updateVacation(vacation);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        finish();
                    }
                }
            }

        });

    }

    public String convertCtryToFlag(String ctryName) {
        String ctryCode = "";
        for (String isoCode : java.util.Locale.getISOCountries()) {
            Locale locale = new Locale("",isoCode);
            if (locale.getDisplayCountry().equalsIgnoreCase(ctryName)) {
                ctryCode = isoCode;
                break;
            }
        }
        if (ctryName == null || ctryCode.length() != 2) {
            return "";
        }
        else {
            int char1 = Character.codePointAt(ctryCode, 0) - 0x41 + 0x1F1E6;
            int char2 = Character.codePointAt(ctryCode, 1) - 0x41 + 0x1F1E6;
            String flagStringChar1 = new String(Character.toChars(char1));
            String flagStringChar2 = new String(Character.toChars(char2));
            return flagStringChar1 + flagStringChar2;
        }
    }

    // This method is used to set startDate and endDate on the screen from the datePicker.
    private void updateDate(EditText editText){
        editText.setText(sdf.format(mCalender.getTime()));
    }

    // This method creates an Action bar menu.
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_vacation_details, menu);
        return true;
    }

    // This method has menu items to delete and set Alerts.
    // It also handles the back button manually.
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if (item.getItemId() == android.R.id.home){
            finish();
            return  true;
        }


        Date dateSt;
        Date dateEd;
        try {
            dateString1 = new DateString();
            dateSt = dateString1.stringToDate(editStartDate.getText().toString());
            dateString2 = new DateString();
            dateEd = dateString2.stringToDate(editEndDate.getText().toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (item.getItemId() == R.id.setalertsV) {
            scheduleAlert(dateSt.getTime(), editTitle.getText().toString() + " vacation is starting");
            scheduleAlert(dateEd.getTime(), editTitle.getText().toString() + " Vacation is ending");
            return true;
        }


        if (item.getItemId() == R.id.deletevacation) {
            Vacation vacation;
            List<Excursion> allAssociatedExcursions;
            try {
                allAssociatedExcursions = repository.getmListAssociatedExcursions(vacationId);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if(allAssociatedExcursions.isEmpty()){
                vacation = new Vacation(vacationId, editTitle.getText().toString(), editHotel.getText().toString(), dateSt, dateEd);
                try {
                    repository.deleteVacation(vacation);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Toast.makeText(VacationDetails.this, editTitle.getText().toString() + " was deleted", Toast.LENGTH_LONG).show();
                this.finish();
            }
            else {
                Toast.makeText(VacationDetails.this, "Can't delete a Vacation with excursions", Toast.LENGTH_LONG).show();
            }
        }

        if (item.getItemId() == R.id.sharevacation) {
            repository = new Repository(getApplication());
            List<Excursion> allExcursions;
            try {
                allExcursions = repository.getmListAssociatedExcursions(vacationId);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            StringBuilder builder = new StringBuilder();
            String ts = "";
            String ds = "";
            for (Excursion e : allExcursions) {
                ts = e.getTitle();
                ds = sdf.format(e.getDate());
                builder.append(ts).append(" ");
                builder.append(ds).append(", ");
            }
            String allExcursionsString = builder.toString();
            String shareMessage = editTitle.getText().toString() + " vacation. " + "Staying at: " +
                    editHotel.getText().toString() + ". From: " + editStartDate.getText().toString() +
                    " to " + editEndDate.getText().toString() + ". " + "Excursions are: " + allExcursionsString;
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Vacation: " + editTitle.getText().toString());
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }

        if (item.getItemId() == R.id.returnHome) {
            Intent intent = new Intent(VacationDetails.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        return true;
    }


    // This method is called to schedule Alerts for start and end dates.
    private void scheduleAlert(Long trigger, String st) {
        Date dateSt;
        Date dateEd;
        try {
            dateString1 = new DateString();
            dateSt = dateString1.stringToDate(editStartDate.getText().toString());
            dateString2 = new DateString();
            dateEd = dateString2.stringToDate(editEndDate.getText().toString());
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

        if (dateSt.before(currDate) || dateEd.before(currDate) ) {
            Toast.makeText(VacationDetails.this, "Start or end date is in the past", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
        intent.putExtra("key", st);
        PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this,
                ++MainActivity.numAlert, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
    }


    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerview);
        repository = new Repository(getApplication());
        List<Excursion> allExcursions;
        try {
            allExcursions = repository.getmListAssociatedExcursions(vacationId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        excursionAdapter.setmExcursions(allExcursions);
    }

}