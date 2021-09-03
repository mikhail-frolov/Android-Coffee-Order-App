// Name: Mikhail Frolov
// ID: 164788184
package com.example.mikhail_myorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mikhail_myorder.adapters.OnOrderItemClickListener;
import com.example.mikhail_myorder.adapters.OrderAdapter;
import com.example.mikhail_myorder.databinding.ActivityOrderScreenBinding;
import com.example.mikhail_myorder.databinding.ActivityOrderViewBinding;
import com.example.mikhail_myorder.db.AppDatabase;
import com.example.mikhail_myorder.db.Coffee;
import com.example.mikhail_myorder.models.Order;
import com.example.mikhail_myorder.viewmodels.CoffeeViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrderScreen extends AppCompatActivity implements OnOrderItemClickListener {

    private final String TAG = this.getClass().getCanonicalName();
    private ArrayList<Order> coffeeArrayList;
    private ActivityOrderViewBinding binding;
    private OrderAdapter orderAdapter;
    private CoffeeViewModel coffeeViewModel;
    private ItemTouchHelper helper;
    private Order tempCoffeeItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_score_board);
        this.binding = ActivityOrderViewBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.helper = new ItemTouchHelper(simpleCallback);
        this.helper.attachToRecyclerView(this.binding.recyclerView);



        this.coffeeArrayList = new ArrayList<>();

        this.orderAdapter = new OrderAdapter(this, this.coffeeArrayList, this);
        this.binding.recyclerView.setAdapter(orderAdapter);
        this.binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        this.coffeeViewModel = new ViewModelProvider(this).get(CoffeeViewModel.class);

        this.coffeeViewModel.getAllOrders().observe(this, new Observer<List<Coffee>>() {
            @Override
            public void onChanged(List<Coffee> coffeeList) {
                if (!coffeeList.isEmpty()){
                    Log.e(TAG, "onChanged: coffees received from DB " + coffeeList.toString() );
                    coffeeArrayList.clear();

                    for(Coffee tempOrder: coffeeList){
                        tempCoffeeItem = new Order();
                        tempCoffeeItem.setChosenCoffeeId(tempOrder.getCoffeeId());
                        tempCoffeeItem.setChosenCoffeeType(tempOrder.getCoffeeType());
                        tempCoffeeItem.setChosenCoffeeSize(tempOrder.getCoffeeSize());
                        tempCoffeeItem.setChosenCoffeeQuantity(tempOrder.getCoffeeQuantity());

                        coffeeArrayList.add(tempCoffeeItem);
                    }
                    orderAdapter.notifyDataSetChanged();
                }else{
                    Log.e(TAG, "onChanged: empty coffee/order list");
                }
            }
        });

//        ArrayList<Order> extraScores = this.getIntent().getParcelableArrayListExtra("EXTRA_ORDER_LIST");
//        if (extraScores != null){
//            this.orderArrayList = extraScores;
//            Log.d(TAG, "onCreate: Order list received from MainActivity " + this.orderArrayList.toString());
//        }else{
//            Log.e(TAG, "onCreate: No data received from MainActivity");
//        }
//
//        this.orderAdapter = new OrderAdapter(this, this.orderArrayList);


    }

    @Override
    public void onCoffeeItemClicked (Order order) {
        Log.e(TAG, "onMealItemClicked: Update the coffee quantity");

        Coffee selected = new Coffee();
        selected.setCoffeeId((order.getChosenCoffeeId()));
        selected.setCoffeeType(order.getChosenCoffeeType());
        selected.setCoffeeSize(order.getChosenCoffeeSize());
        selected.setCoffeeQuantity(order.getChosenCoffeeQuantity());

        AlertDialog.Builder updateAlert = new AlertDialog.Builder(this);
        updateAlert.setTitle("Update");
        updateAlert.setMessage("Provide the updated quantity for the coffee.");

        final EditText inputName = new EditText(this);
        inputName.setText(selected.getCoffeeQuantity());
        updateAlert.setView(inputName);

        updateAlert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //update the coffee in DB

                selected.setCoffeeQuantity(inputName.getText().toString());
                coffeeViewModel.updateCoffee(selected);
                orderAdapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(),
                        selected.getCoffeeType() + " quantity has been updated to :" + selected.getCoffeeQuantity(),
                        Toast.LENGTH_LONG).show();
            }
        });

        updateAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        updateAlert.show();
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (direction == ItemTouchHelper.LEFT){
                //delete the coffee from DB
                deleteCoffee(viewHolder.getAdapterPosition());
            }
        }
    };

    private void deleteCoffee(int position){
        Order selectedOrder = coffeeArrayList.get(position);


        //ask for confirmation
        AlertDialog.Builder deleteAlert = new AlertDialog.Builder(this);
        deleteAlert.setTitle("Delete");
        deleteAlert.setMessage("Are you sure you want to remove "+ selectedOrder.getChosenCoffeeType() + " from the list?");

        deleteAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //delete the coffee in DB

                Coffee toDelete = new Coffee();
                toDelete.setCoffeeType(selectedOrder.getChosenCoffeeType());
                toDelete.setCoffeeQuantity(selectedOrder.getChosenCoffeeQuantity());
                toDelete.setCoffeeSize(selectedOrder.getChosenCoffeeSize());
                toDelete.setCoffeeId((selectedOrder.getChosenCoffeeId()));


                coffeeViewModel.deleteCoffee(toDelete);


                Toast.makeText(getApplicationContext(),
                        selectedOrder.getChosenCoffeeType() + " has been removed from list",
                        Toast.LENGTH_LONG).show();
            }
        });

        deleteAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                orderAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        deleteAlert.show();
    }
}