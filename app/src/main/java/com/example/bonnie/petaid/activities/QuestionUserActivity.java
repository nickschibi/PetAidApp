package com.example.bonnie.petaid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.bonnie.petaid.PetAidApplication;
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
                ((PetAidApplication) QuestionUserActivity.this.getApplication()).setTypeUser("vol");
                Intent i = new Intent(QuestionUserActivity.this, CadastroVolActivity.class);
                startActivity(i);

            }
        });
        btnOng = findViewById(R.id.btnOng);
        btnOng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PetAidApplication) QuestionUserActivity.this.getApplication()).setTypeUser("ong");
                Intent i = new Intent(QuestionUserActivity.this, CadastroOngActivity.class);
                i.putExtra("cadastro", true);
                startActivity(i);
            }
        });

    }
}
