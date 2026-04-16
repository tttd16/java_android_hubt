package com.example.javabtvn_8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class DrinkAdapter extends ArrayAdapter<Drink> {
    private Context context;
    private List<Drink> drinkList;
    private List<Integer> quantities;

    public DrinkAdapter(Context context, List<Drink> drinkList, List<Integer> quantities) {
        super(context, 0, drinkList);
        this.context = context;
        this.drinkList = drinkList;
        this.quantities = quantities;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_drink, parent, false);
        }

        Drink drink = drinkList.get(position);

        ImageView imgDrink = convertView.findViewById(R.id.imgDrink);
        TextView tvDrinkName = convertView.findViewById(R.id.tvDrinkName);
        TextView tvDrinkPrice = convertView.findViewById(R.id.tvDrinkPrice);
        EditText etQuantity = convertView.findViewById(R.id.etQuantity);

        imgDrink.setImageResource(drink.getImageResId());
        tvDrinkName.setText(drink.getName());
        tvDrinkPrice.setText(String.format("%,d VNĐ", drink.getPrice()));
        etQuantity.setText(String.valueOf(quantities.get(position)));

        etQuantity.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                try {
                    int qty = Integer.parseInt(etQuantity.getText().toString());
                    if (qty < 0) qty = 0;
                    quantities.set(position, qty);
                } catch (NumberFormatException e) {
                    quantities.set(position, 0);
                    etQuantity.setText("0");
                }
            }
        });

        return convertView;
    }
}