package com.example.d308vacationplanner.UI;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
    String excursionTitle;
    String excursionDate;
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

        editTitle = findViewById(R.id.excursiontitle);
        editDate = findViewById(R.id.excursiondate);

        excursionId = getIntent().getLongExtra("excursionId", -1);
        excursionTitle = getIntent().getStringExtra("title");
        excursionDate = getIntent().getStringExtra("date");
        vacationIdEx = getIntent().getLongExtra("vacationId", -1);

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

    }

    private void updateDate(EditText editText){
        editText.setText(sdf.format(mCalender.getTime()));
    }
}