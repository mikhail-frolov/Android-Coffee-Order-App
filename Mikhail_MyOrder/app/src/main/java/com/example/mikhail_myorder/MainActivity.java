// Name: Mikhail Frolov
// ID: 164788184
package com.example.mikhail_myorder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mikhail_myorder.adapters.OrderAdapter;
import com.example.mikhail_myorder.databinding.ActivityMainBinding;
import com.example.mikhail_myorder.db.AppDatabase;
import com.example.mikhail_myorder.db.Coffee;
import com.example.mikhail_myorder.db.CoffeeDAO;
import com.example.mikhail_myorder.models.Order;
import com.example.mikhail_myorder.viewmodels.CoffeeViewModel;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener{

    private final String TAG = this.getClass().getCanonicalName();
    ActivityMainBinding binding;
    Spinner spinner;
    String text;
    private CoffeeViewModel coffeeViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.activity_main);
        this.binding = ActivityMainBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);




        this.coffeeViewModel = new ViewModelProvider(this).get(CoffeeViewModel.class);

        // Code for coffee type spinner
        this.spinner = this.binding.spinner1;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this
        , R.array.coffeeTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(adapter);
        this.spinner.setOnItemSelectedListener(this);

        this.binding.button.setOnClickListener(this);

    }



    private void saveNewCoffeeOrder(String type, String size, String quantity){

        AppDatabase db = AppDatabase.getDatabase(this.getApplicationContext());

        Coffee coffee = new Coffee();

        coffee.setCoffeeId(coffee.coffeeId);
        coffee.setCoffeeType(type);
        coffee.setCoffeeSize(size);
        coffee.setCoffeeQuantity(quantity);

        this.coffeeViewModel.insertCoffee(coffee);


        Log.d(TAG, "saveNewCoffeeOrder: Coffees saved: " + coffee.toString() + "QTY: " + coffee.getCoffeeQuantity().toString());

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v != null){
            switch (v.getId()){
                case R.id.button:{
                    //do something
                    RadioGroup radioGroup = (RadioGroup)findViewById(R.id.rdg_size);

// get selected radioButton from radioGroup
                    int selectedId = radioGroup.getCheckedRadioButtonId();

// find the radioButton by returned id
                    RadioButton radioButton = (RadioButton) findViewById(selectedId);

// radioButton text
                    String radiovalue = radioButton.getText().toString();
                    this.saveNewCoffeeOrder(text,radiovalue , this.binding.editTextNumber.getText().toString());
                    Toast.makeText(getApplicationContext(),
                            "An order for: " + text + " has been placed!",
                            Toast.LENGTH_LONG).show();
                    break;
                }
            }
        }
    }



    private void goToOrders(){
        Intent orderIntent = new Intent(this, OrderScreen.class);
        startActivity(orderIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_view_order:{
                this.goToOrders();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

}