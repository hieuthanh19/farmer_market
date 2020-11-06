package com.example.farmersmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.ProductType;

public class QuangTest extends AppCompatActivity {
    private EditText editType, editStatus;
    AppDatabase appDatabase = AppDatabase.getAppDatabase(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quang_test);

        editType = findViewById(R.id.editType);
        editStatus = findViewById(R.id.editStatus);

        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
            }
        });
    }

    private void saveTask() {
        final String type = editType.getText().toString().trim();
        final String status = editStatus.getText().toString().trim();
        ProductType pt = new ProductType();
        pt.productTypeID=1;
        pt.productTypeName=type;
        pt.status=Integer.valueOf(status);
        appDatabase.productTypeDAO().insertProductType(pt);

    }
}