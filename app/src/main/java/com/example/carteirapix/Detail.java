package com.example.carteirapix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Detail extends AppCompatActivity {

    private EditText inputNameDetail;
    private EditText inputPixDetail;
    private Button copyPix;
    private Button update;
    private Button delete;
    private Button returnDetail;
    private String selectedId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        inputNameDetail = findViewById(R.id.inputNameDetail);
        inputPixDetail = findViewById(R.id.inputPixDetail);
        copyPix = findViewById(R.id.copyPix);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        returnDetail = findViewById(R.id.returnDetail);

        selectedId = this.getIntent().getStringExtra("selectedId");
        DBController crud = new DBController(getBaseContext());
        Cursor cursor = crud.requestData(Integer.parseInt(selectedId));
        inputNameDetail.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBCore.NAME)));
        inputPixDetail.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBCore.PIX)));
    }

    public void onClickCopy(View v) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("pix", inputPixDetail.getText().toString());
        clipboard.setPrimaryClip(clip);
    }

    public void onClickUpdate(View v) {
        DBController crud = new DBController(getBaseContext());

        crud.updateData(
                Integer.parseInt(selectedId),
                inputNameDetail.getText().toString(),
                inputPixDetail.getText().toString()
        );
        String feedBack = "Atualizado com sucesso";
        Toast.makeText(getApplicationContext(), feedBack, Toast.LENGTH_LONG).show();

    }

    public void onClickDelete(View v) {
        DBController crud = new DBController(getBaseContext());
        crud.deleteData(Integer.parseInt(selectedId));

        String feedBack = "Registro deletado";
        Toast.makeText(getApplicationContext(), feedBack, Toast.LENGTH_LONG).show();

        finish();
    }

    public void onClickReturn(View v) {
        finish();
    }

}