package com.example.d308vacationplanner.UI;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.Database.Repository;
import com.example.d308vacationplanner.Entities.Excursion;
import com.example.d308vacationplanner.R;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class Reports extends AppCompatActivity {
    private TextView vTitle;
    private TextView vHotel;
    private TextView vStartDate;
    private TextView vEndDate;
    private TextView reportTimeStamp;
    private Long vacationId;
    private String vacationTitle;
    private String vacationHotel;
    private String vacationStDAte;
    private String vacationEndDate;
    private Repository repository;

    private  SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reports);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        reportTimeStamp = findViewById(R.id.textView17);

        vTitle = findViewById(R.id.textView11);
        vHotel = findViewById(R.id.textView12);
        vStartDate = findViewById(R.id.textView13);
        vEndDate = findViewById(R.id.textView14);

        vacationId = getIntent().getLongExtra("vacationId", -1);
        vacationTitle = getIntent().getStringExtra("title");
        vacationHotel = getIntent().getStringExtra("hotel");
        vacationStDAte = getIntent().getStringExtra("startDate");
        vacationEndDate = getIntent().getStringExtra("endDate");

        vTitle.setText(vacationTitle);
        vHotel.setText(vacationHotel);
        vStartDate.setText(vacationStDAte);
        vEndDate.setText(vacationEndDate);

        RecyclerView recyclerView = findViewById(R.id.reportExcursionRecyclerview);
        repository = new Repository(getApplication());
        List<Excursion> allExcursions;
        try {
            allExcursions = repository.getmListAssociatedExcursions(vacationId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        final ReportsAdapter reportsAdapter = new ReportsAdapter(this);
        recyclerView.setAdapter(reportsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reportsAdapter.setmExcursions(allExcursions);

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, u   h:mm a");
        String rtsString = "Created:  " + dateTime.format(formatter);

        reportTimeStamp.setText(rtsString);

    }
}