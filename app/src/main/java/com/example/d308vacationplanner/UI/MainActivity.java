package com.example.d308vacationplanner.UI;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.Database.Repository;
import com.example.d308vacationplanner.Entities.Excursion;
import com.example.d308vacationplanner.Entities.Vacation;
import com.example.d308vacationplanner.R;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;
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

        LinearLayout linearLayout = findViewById(R.id.searchButton);
        linearLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchReport.class);
                startActivity(intent);

            }
        });

        Button button = findViewById(R.id.myVacaButton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyVacations.class);
                startActivity(intent);

            }

        });
//            SearchView searchView = findViewById(R.id.searchView);
//        //searchView.setSubmitButtonEnabled(true);
//        RecyclerView searchRecyclerView = findViewById(R.id.searchRecyclerview);
//
//        Repository repository = new Repository(getApplication());
//        List<Vacation> allVacations;
//        try {
//            allVacations = repository.getmListVacations();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        SearchAdapter searchAdapter = new SearchAdapter(allVacations);
//        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        searchRecyclerView.setAdapter(searchAdapter);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                searchAdapter.searchThis(newText);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                searchAdapter.searchThis(query);
//                return true;
//            }
//        });

//        RecyclerView recyclerView = findViewById(R.id.searchRecyclerview);
//        Repository repository = new Repository(getApplication());
//        List<Vacation> allVacations;
//        try {
//            allVacations = repository.getmListVacations();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        final VacationAdapter vacationAdapter = new VacationAdapter(this);
//        recyclerView.setAdapter(vacationAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        vacationAdapter.setVacations(allVacations);
    }
}