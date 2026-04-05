package com.example.javabtvn_3;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText edtHoTen, edtCMND;
    private RadioGroup radioGroupBangCap;
    private CheckBox chkDocBao, chkDocSach, chkDocCoding;
    private CheckBox chkSeniorProgrammer, chkSeniorSaler;
    private Button btnGuiThongTin;

    private TextView tvInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtHoTen = findViewById(R.id.edtHoTen);
        edtCMND = findViewById(R.id.edtCMND);
        radioGroupBangCap = findViewById(R.id.radioGroupBangCap);
        chkDocBao = findViewById(R.id.chkDocBao);
        chkDocSach = findViewById(R.id.chkDocSach);
        chkDocCoding = findViewById(R.id.chkDocCoding);
        chkSeniorProgrammer = findViewById(R.id.chkSeniorProgrammer);
        chkSeniorSaler = findViewById(R.id.chkSeniorSaler);
        btnGuiThongTin = findViewById(R.id.btnGuiThongTin);
        tvInfo = findViewById(R.id.tvInfo);

        RadioButton radioDaiHoc = findViewById(R.id.radioDaiHoc);
        radioDaiHoc.setChecked(true);

        btnGuiThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) {
                    showSummary();
                }
            }
        });
    }

    private boolean validateForm() {

        String hoTen = edtHoTen.getText().toString();

        if (isNullOrEmpty(hoTen)) {
            showError("Họ tên không được để trống!");
            edtHoTen.requestFocus();
            return false;
        }

        if (hoTen.trim().length() < 3) {
            showError("Họ tên phải có ít nhất 3 ký tự!");
            edtHoTen.requestFocus();
            return false;
        }

        String cmnd = edtCMND.getText().toString();

        if (isNullOrEmpty(cmnd)) {
            showError("CMND không được để trống!");
            edtCMND.requestFocus();
            return false;
        }

        if (!chkDocBao.isChecked() && !chkDocSach.isChecked() && !chkDocCoding.isChecked()) {
            showError("Vui lòng chọn ít nhất 1 sở thích!");
            return false;
        }

        return true;
    }

    private void showError(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void showSummary() {
        String hoTen = edtHoTen.getText().toString().trim();
        String cmnd = edtCMND.getText().toString().trim();

        int selectedBangCapId = radioGroupBangCap.getCheckedRadioButtonId();
        RadioButton selectedBangCap = findViewById(selectedBangCapId);
        String bangCap = selectedBangCap.getText().toString();

        StringBuilder soThich = new StringBuilder();
        if (chkDocBao.isChecked()) soThich.append("Đọc báo, ");
        if (chkDocSach.isChecked()) soThich.append("Đọc sách, ");
        if (chkDocCoding.isChecked()) soThich.append("Đọc coding, ");

        if (soThich.length() > 0) {
            soThich.setLength(soThich.length() - 2);
        }

        StringBuilder thongTinBoSung = new StringBuilder();
        if (chkSeniorProgrammer.isChecked()) thongTinBoSung.append("Senior Programmer, ");
        if (chkSeniorSaler.isChecked()) thongTinBoSung.append("Senior saler, ");

        if (thongTinBoSung.length() > 0) {
            thongTinBoSung.setLength(thongTinBoSung.length() - 2);
        }

        String message = "Họ tên: " + hoTen + "\n" +
                "CMND: " + cmnd + "\n" +
                "Bằng cấp: " + bangCap + "\n" +
                "Sở thích: " + soThich.toString() + "\n" +
                "Thông tin bổ sung: " + thongTinBoSung.toString();
        tvInfo.setText(message);
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}