package com.example.javabtvn_8;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listViewDrinks;
    private Button btnOrder;
    private TextView tvTotal;
    private List<Drink> drinkList;
    private List<Integer> quantities;
    private DrinkAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        listViewDrinks = findViewById(R.id.listViewDrinks);
        btnOrder = findViewById(R.id.btnOrder);
        tvTotal = findViewById(R.id.tvTotal);

        drinkList = new ArrayList<>();
        drinkList.add(new Drink("Trà chanh", 15000, R.drawable.imagestc));
        drinkList.add(new Drink("Trà đào", 20000, R.drawable.imagestradao));
        drinkList.add(new Drink("Sinh tố xoài", 30000, R.drawable.imagessinhtoxoai));
        drinkList.add(new Drink("Cà phê đen", 12000, R.drawable.imagescapheden));
        drinkList.add(new Drink("Sữa chua dâu", 25000, R.drawable.imagessuachuadau));
        drinkList.add(new Drink("Nước cam ép", 22000, R.drawable.imagesnuoccam));

        quantities = new ArrayList<>();
        for (int i = 0; i < drinkList.size(); i++) {
            quantities.add(0);
        }

        // Set adapter
        adapter = new DrinkAdapter(this, drinkList, quantities);
        listViewDrinks.setAdapter(adapter);

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateTotal();
            }
        });
    }

    private void calculateTotal() {
        int total = 0;
        for (int i = 0; i < drinkList.size(); i++) {
            int quantity = quantities.get(i);
            int price = drinkList.get(i).getPrice();
            total += quantity * price;
        }

        tvTotal.setText(String.format("Tổng tiền: %,d VNĐ", total));

        if (total == 0) {
            tvTotal.setText("Tổng tiền: 0 VNĐ (Bạn chưa chọn đồ uống nào)");
        }
    }
}