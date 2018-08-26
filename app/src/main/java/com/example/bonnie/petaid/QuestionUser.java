package com.example.bonnie.petaid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class QuestionUser extends AppCompatActivity {
    private Button btnVol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_user);
        getSupportActionBar().hide();

        btnVol = findViewById(R.id.btnVol);
        btnVol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuestionUser.this, LoginVolActivity.class);
                startActivity(i);

            }
        });


    }
}
