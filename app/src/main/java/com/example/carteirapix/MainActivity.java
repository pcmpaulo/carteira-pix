package com.example.carteirapix;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class MainActivity extends AppCompatActivity {

    private Button register;
    private Button exit;
    private ListView listPix;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = findViewById(R.id.register);
        exit = findViewById(R.id.exit);
        listPix = findViewById(R.id.listPix);
    }

    @Override
    protected void onStart() {
        super.onStart();

        createPixList();
        setOnClickList();
    }

    public void createPixList() {
        DBController crud = new DBController(getBaseContext());
        cursor = crud.listData();

        String[] fieldNames = new String[] {DBCore.NAME, DBCore.PIX};
        int[] idViews = new int[] {R.id.columnName, R.id.columnPix};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getBaseContext(),
                R.layout.pix_table,cursor,fieldNames,idViews, 0);

        listPix.setAdapter(adapter);
    }

    public void setOnClickList() {
        listPix.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemClickListView(parent, view, position, id);
            }
        });
    }

    public void onItemClickListView(AdapterView<?> parent, View v, int position, long id) {
        Intent intent = new Intent(this, Detail.class);
        String selectedId;
        cursor.moveToPosition(position);
        selectedId = cursor.getString(cursor.getColumnIndexOrThrow(DBCore.ID));
        intent.putExtra("selectedId", selectedId);
        startActivity(intent);
    }

    public void onClickRegister(View v) {
        Intent intent = new Intent(this, Form.class);
        startActivity(intent);
    }

    public void onClickScanQRCode(View v) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(
                MainActivity.this
        );
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setCaptureActivity(Capture.class);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode, data
        );

        if (intentResult.getContents() != null) {
            String pixKey = intentResult.getContents();
            DBController crud = new DBController(getBaseContext());
            String resultado;

            resultado = crud.insertData(pixKey, pixKey);

            this.onStart();
            Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    MainActivity.this
            );

            builder.setTitle("Erro");
            builder.setMessage("Nenhuma chave pix encontrada");
            builder.show();
        }
    }

    public void onClickExit(View v) {
        System.exit(0);
    }
}