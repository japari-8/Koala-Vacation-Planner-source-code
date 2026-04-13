package com.example.d308vacationplanner.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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

    DateString dateString;

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

        editTitle = findViewById(R.id.vacationtitle);
        editHotel = findViewById(R.id.vacationhotel);
        editStartDate = findViewById(R.id.vacationstartdate);
        editEndDate = findViewById(R.id.vacationenddate);



        vacationId = getIntent().getLongExtra("vacationId", -1);
        vacationTitle = getIntent().getStringExtra("title");
        vacationHotel = getIntent().getStringExtra("hotel");
        vacationStDAte = getIntent().getStringExtra("startDate");
        vacationEndDate = getIntent().getStringExtra("startDate");

        editTitle.setText(vacationTitle);
        editHotel.setText(vacationHotel);
        editStartDate.setText(vacationStDAte);
        editStartDate.setFocusable(false);
        editStartDate.setClickable(true);
        editEndDate.setText(vacationEndDate);
        editEndDate.setFocusable(false);
        editEndDate.setClickable(true);

        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerview);
        repository = new Repository(getApplication());
        List<Excursion> allExcursions;

        try {
            allExcursions = repository.getmListExcursions();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        excursionAdapter.setmExcursions(allExcursions);


        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        if(vacationId == -1) {
            String currentDate = sdf.format(new Date());
            editStartDate.setText(currentDate);
        }

        editStartDate.setOnClickListener(new View.OnClickListener(){

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

        pickStDate = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalender.set(Calendar.YEAR, year);
                mCalender.set(Calendar.MONTH, month);
                mCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateStDate(editStartDate);

            }
        };

        if(vacationId == -1) {
            String currentDate = sdf.format(new Date());
            editEndDate.setText(currentDate);
        }

        editEndDate.setOnClickListener(new View.OnClickListener(){

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

        pickEndDate = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalender.set(Calendar.YEAR, year);
                mCalender.set(Calendar.MONTH, month);
                mCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateStDate(editEndDate);

            }
        };


        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vacation vacation;
                Date dateSt;
                Date dateEd;
                try {
                    dateString = new DateString();
                    dateSt = dateString.stringToDate(editStartDate.getText().toString());
                    dateEd = dateString.stringToDate(editEndDate.getText().toString());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                if (vacationId == -1){

                    try {
                        if (repository.getmListVacations().isEmpty()) {
                            vacationId = (long) 1;
                        }
                        else {
                            vacationId = repository.getmListVacations().get(repository.getmListVacations().size() - 1).getVacationId() + 1;
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    vacation = new Vacation(vacationId, editTitle.getText().toString(), editHotel.getText().toString(), dateSt, dateEd);
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


//                try {
//                    dateSt = dateString.stringToDate(editStartDate.getText().toString());
//                } catch (ParseException e) {
//                    throw new RuntimeException(e);
//                }
//
//                try {
//                    dateEd = dateString.stringToDate(editEndDate.getText().toString());
//                } catch (ParseException e) {
//                    throw new RuntimeException(e);
//                }
//
//                if (vacationId == -1) {
//                    vacationId = (long) 0;
//
//                    vacation = new Vacation(vacationId, editTitle.getText().toString(), editHotel.getText().toString(), dateSt, dateEd);
//                    try {
//                        repository.addVacation(vacation);
//
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//                else {
//                    vacation = new Vacation(vacationId, editTitle.getText().toString(), editHotel.getText().toString(), dateSt, dateEd);
//                    try {
//                        repository.updateVacation(vacation);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//                Intent intent = new Intent(VacationDetails.this, MyVacations.class);
//                startActivity(intent);
            }

        });

    }

    private void updateStDate(EditText editText){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        editText.setText(sdf.format(mCalender.getTime()));
    }

}