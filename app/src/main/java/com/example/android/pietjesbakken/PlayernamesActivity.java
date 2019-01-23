package com.example.android.pietjesbakken;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PlayernamesActivity extends AppCompatActivity {

    private Button btn;
    EditText et1;
    String st1;
    EditText et2;
    String st2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playernames_activity);

        // onclicklistener toevoegen aan button om spel te starten
        btn = (Button) findViewById(R.id.button);
        et1 = (EditText) findViewById(R.id.namePlayer1);
        et2 = (EditText) findViewById(R.id.namePlayer2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame();
            }
        });
    }

    // Intent aanmaken om naar volgende activity te gaan
    public void openGame() {
        Intent intent = new Intent(this, HomeActivity.class);
        st1 = et1.getText().toString();
        st2 = et2.getText().toString();
        intent.putExtra("Value1", st1);
        intent.putExtra("Value2", st2);
        startActivity(intent);
        finish();
    }
}
