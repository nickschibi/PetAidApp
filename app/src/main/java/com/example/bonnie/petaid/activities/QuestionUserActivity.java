package com.example.bonnie.petaid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.bonnie.petaid.R;

public class QuestionUserActivity extends AppCompatActivity {
    private Button btnVol;
    private Button btnOng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_user);
        getSupportActionBar().hide();

        btnVol = findViewById(R.id.btnVol);
        btnVol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuestionUserActivity.this, LoginVolActivity.class);
                startActivity(i);

            }
        });
        btnOng= findViewById(R.id.btnOng);
        btnOng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuestionUserActivity.this, CadastroOngActivity.class);
                startActivity(i);
            }
        });

    }
}
