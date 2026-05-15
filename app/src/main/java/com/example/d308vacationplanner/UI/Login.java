package com.example.d308vacationplanner.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d308vacationplanner.R;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText userName;
        EditText passWord;
        Button loginButton;
        TextView errorMessage;

        userName = findViewById(R.id.editTextText);
        passWord = findViewById(R.id.editTextText2);
        loginButton = findViewById(R.id.loginButton);
        errorMessage = findViewById(R.id.textView19);

        errorMessage.setVisibility(View.GONE);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(userName.getText().toString().equals("user")  && passWord.getText().toString().equals("Welcome")) {
                    errorMessage.setVisibility(View.GONE);
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    String errorString = "Wrong username or password";
                    errorMessage.setText(errorString);
                    errorMessage.setVisibility(View.VISIBLE);

                }
            }
        });
    }
}