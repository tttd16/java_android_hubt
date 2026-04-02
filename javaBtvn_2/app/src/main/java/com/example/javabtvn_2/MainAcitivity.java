package com.example.javabtvn_2;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainAcitivity extends AppCompatActivity {
    private EditText edtSlMon1, edtSlMon2, edtSlMon3, edtSlMon4;
    private CheckBox cbMon1, cbMon2, cbMon3, cbMon4;

    private TextView tvTotal;
    private Button btnTotal;

    private final int GIA_MON1 = 40000;
    private final int GIA_MON2 = 90000;
    private final int GIA_MON3 = 140000;
    private final int GIA_MON4 = 30000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_main);
        initView();
        setupCheckboxListeners();
        setupQuantityListeners();

        btnTotal.setOnClickListener(v -> tinhTien());
    }

    private void initView() {
        edtSlMon1 = findViewById(R.id.edtSlMon1);
        edtSlMon2 = findViewById(R.id.edtSlMon2);
        edtSlMon3 = findViewById(R.id.edtSlMon3);
        edtSlMon4 = findViewById(R.id.edtSlMon4);
        cbMon1 = findViewById(R.id.cbMon1);
        cbMon2 = findViewById(R.id.cbMon2);
        cbMon3 = findViewById(R.id.cbMon3);
        cbMon4 = findViewById(R.id.cbMon4);
        btnTotal = findViewById(R.id.btnTotal);
        tvTotal = findViewById(R.id.tvTotal);

        edtSlMon1.setText("0");
        edtSlMon2.setText("0");
        edtSlMon3.setText("0");
        edtSlMon4.setText("0");
    }

    private void setupCheckboxListeners() {
        cbMon1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked && getSoLuong(edtSlMon1) == 0) {
                edtSlMon1.setText("1");
            }
//            tinhTien();
        });

        cbMon2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked && getSoLuong(edtSlMon2) == 0) {
                edtSlMon2.setText("1");
            }
//            tinhTien();
        });

        cbMon3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked && getSoLuong(edtSlMon3) == 0) {
                edtSlMon3.setText("1");
            }
//            tinhTien();
        });

        cbMon4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked && getSoLuong(edtSlMon4) == 0) {
                edtSlMon4.setText("1");
            }
//            tinhTien();
        });
    }

    private void setupQuantityListeners() {
        View.OnFocusChangeListener focusListener = (v, hasFocus) -> {
            if (!hasFocus) {
                kiemTraVaCapNhatSoLuong((EditText) v);
//                tinhTien();
            }
        };

        edtSlMon1.setOnFocusChangeListener(focusListener);
        edtSlMon2.setOnFocusChangeListener(focusListener);
        edtSlMon3.setOnFocusChangeListener(focusListener);
        edtSlMon4.setOnFocusChangeListener(focusListener);
    }

    private void kiemTraVaCapNhatSoLuong(EditText edt) {
        int soLuong = getSoLuong(edt);

        if (soLuong < 0) {
            edt.setText("0");
            soLuong = 0;
        }

        if (edt.getId() == R.id.edtSlMon1 && soLuong == 0) {
            cbMon1.setChecked(false);
        } else if (edt.getId() == R.id.edtSlMon2 && soLuong == 0) {
            cbMon2.setChecked(false);
        } else if (edt.getId() == R.id.edtSlMon3 && soLuong == 0) {
            cbMon3.setChecked(false);
        } else if (edt.getId() == R.id.edtSlMon4 && soLuong == 0) {
            cbMon4.setChecked(false);
        }
    }

    private int getSoLuong(EditText edt) {
        String str = edt.getText().toString();
        if (TextUtils.isEmpty(str)) return 0;
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void tinhTien() {
        int total = 0;

        if (cbMon1.isChecked()) {
            total += GIA_MON1 * getSoLuong(edtSlMon1);
        }

        if (cbMon2.isChecked()) {
            total += GIA_MON2 * getSoLuong(edtSlMon2);
        }

        if (cbMon3.isChecked()) {
            total += GIA_MON3 * getSoLuong(edtSlMon3);
        }

        if (cbMon4.isChecked()) {
            total += GIA_MON4 * getSoLuong(edtSlMon4);
        }

        tvTotal.setText(String.format("%,d VNĐ", total));
    }
}
