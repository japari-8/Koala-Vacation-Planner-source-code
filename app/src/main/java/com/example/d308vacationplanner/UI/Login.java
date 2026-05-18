package com.example.d308vacationplanner.UI;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStructure;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.example.d308vacationplanner.R;

public class Login extends AppCompatActivity {
    //TextView errorMessage;
    EditText userName;
    EditText passWord;
    Button loginButton;
    TextView errorMessage;

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

        //This block changes the color of the action bar
        if(getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(
                    this, R.color.light_green)));
        }


        userName = findViewById(R.id.editTextText);
        passWord = findViewById(R.id.editTextText2);
        loginButton = findViewById(R.id.loginButton);
        errorMessage = findViewById(R.id.textView19);
        errorMessage.setVisibility(View.GONE);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String userNameSt = userName.getText().toString();
                String pwSt = passWord.getText().toString();

                validateUser(userNameSt, pwSt);
            }
        });
    }

    public  boolean validateUser(String user, String pw) {
        boolean v;
        if (user.equals("user") && pw.equals("Welcome") ) {
            errorMessage.setVisibility(View.GONE);
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
            v = true;
        }
        else{
            String errorString = "Wrong username or password";
            errorMessage.setText(errorString);
            errorMessage.setVisibility(View.VISIBLE);
            v = false;
        }
        return v;
    }

}