package com.example.d308vacationplanner.UI;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.Database.Repository;
import com.example.d308vacationplanner.Entities.Vacation;
import com.example.d308vacationplanner.R;

import java.util.List;

public class SearchReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_report);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setIconified(false);
        searchView.requestFocus();
        searchView.postDelayed(() -> {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (im != null) {
                im.showSoftInput(searchView.findFocus(), InputMethodManager.SHOW_IMPLICIT);
            }
            }, 300);

        //searchView.setSubmitButtonEnabled(true);

        RecyclerView searchRecyclerView = findViewById(R.id.searchRecyclerview);
        Repository repository = new Repository(getApplication());
        List<Vacation> allVacations;
        try {
            allVacations = repository.getmListVacations();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        final SearchAdapter searchAdapter = new SearchAdapter(allVacations, this);
        searchRecyclerView.setAdapter(searchAdapter);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                searchAdapter.searchThis(newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchAdapter.searchThis(query);
                return true;
            }
        });
    }
}