package com.example.d308vacationplanner.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d308vacationplanner.Database.Repository;
import com.example.d308vacationplanner.Entities.Excursion;
import com.example.d308vacationplanner.Entities.Vacation;
import com.example.d308vacationplanner.R;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button button = findViewById(R.id.myVacaButton);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyVacations.class);
                startActivity(intent);

                //FIXME: Use following block to test data added to database. Delete after test.
//                repository = new Repository(getApplication());
//                Date testStartDate = new Date();
//                Date testEndDate = new Date();

//                Vacation testVaca1 = new Vacation(0, "Hawaii", "Hilton", testStartDate, testEndDate);
//                try {
//                    repository.addVacation(testVaca1);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                Date testExcurDate1 = new Date();
//                Excursion testExcur1 = new Excursion(0, "Snorkeling", testExcurDate1, 0);
//                try {
//                    repository.addExcursion(testExcur1);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }


//                repository = new Repository(getApplication());
//                Date testStartDate = new Date();
//                Date testEndDate = new Date();
//
//                Vacation testVaca2 = new Vacation(0, "Italy", "BonVoy", testStartDate, testEndDate);
//                try {
//                    repository.addVacation(testVaca2);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                Date testExcurDate2 = new Date();
//                Excursion testExcur2 = new Excursion(0, "Boat ride", testExcurDate2, 1);
//                try {
//                    repository.addExcursion(testExcur2);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }



            }

        });


    }
}