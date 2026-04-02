package com.example.javabtvn_1;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainAcitivity extends AppCompatActivity {

    private Button btnSub, btnAdd, btnMulti, btnDiv;
    private EditText edtA, edtB;

    private TextView tvKq;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEventBtn();
    }


    private void initView()  {
        btnSub = findViewById(R.id.btnSub);
        btnAdd = findViewById(R.id.btnAdd);
        btnMulti = findViewById(R.id.btnMulti);
        btnDiv = findViewById(R.id.btnDiv);
        edtA = findViewById(R.id.edtA);
        edtB = findViewById(R.id.edtB);
        tvKq = findViewById(R.id.tvKQ);

    }

    private void initEventBtn() {
        btnAdd.setOnClickListener(e -> operationFunc("+"));
        btnSub.setOnClickListener(e -> operationFunc("-"));
        btnMulti.setOnClickListener(e -> operationFunc("*"));
        btnDiv.setOnClickListener(e -> operationFunc("/"));
    }

    private void operationFunc(String p) {
        String strA = edtA.getText().toString();
        String strB = edtB.getText().toString();
        double a = Double.parseDouble(strA);
        double b = Double.parseDouble(strB);
        double kq = 0;
        switch (p) {
            case "+":
                kq = a + b;
                break;
            case "-":
                kq = a - b;
                break;
            case "*":
                kq = a * b;
                break;
            case "/":
                if (b != 0) {
                    kq = a / b;
                } else {
                    kq = 0;
                }
                break;
            default:
                kq = a + b;
                break;
        }
        if (kq == (long) kq) {
            tvKq.setText("Kết quả: " + (long) kq);
        } else {
            tvKq.setText("Kết quả: " + kq);
        }
    }
}
