package com.example.cs301proje;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button runTest = findViewById(R.id.button);
    EditText editText = findViewById(R.id.edittext);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        editText.setText("");
        
       }
    }
