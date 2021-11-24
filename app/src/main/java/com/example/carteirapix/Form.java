package com.example.carteirapix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Form extends AppCompatActivity {

    private Button save;
    private Button goToHome;
    private EditText inputName;
    private EditText inputPix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        save = findViewById(R.id.save);
        goToHome = findViewById(R.id.goToHome);
        inputName = findViewById(R.id.inputName);
        inputPix = findViewById(R.id.inputPix);
    }

    public void onClickSave(View v) {
        DBController crud = new DBController(getBaseContext());
        String nameString = inputName.getText().toString();
        String pixString = inputPix.getText().toString();
        String resultado;

        resultado = crud.insertData(nameString, pixString);

        Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
        finish();
    }

    public void onClickGoToHome(View v) {
        finish();
    }
}