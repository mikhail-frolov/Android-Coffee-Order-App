// Name: Mikhail Frolov
// ID: 164788184
package com.example.mikhail_myorder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mikhail_myorder.databinding.ActivityOrderScreenBinding;
import com.example.mikhail_myorder.db.Coffee;
import com.example.mikhail_myorder.models.Order;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private final String TAG = "OrderAdapter";
    private final Context context;
    private final ArrayList<Order> orderArrayList;
    private final OnOrderItemClickListener itemClickListener;

    public OrderAdapter(Context context, ArrayList<Order> orderArrayList, OnOrderItemClickListener itemClickListener) {
        this.context = context;
        this.orderArrayList = orderArrayList;
        this.itemClickListener = itemClickListener;
    }


    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(ActivityOrderScreenBinding.inflate(LayoutInflater.from(context)));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        final Order currentOrder = this.orderArrayList.get(position);
        holder.bind(context, currentOrder, itemClickListener);
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder{

        ActivityOrderScreenBinding binding;

        public OrderViewHolder(ActivityOrderScreenBinding b){
            super(b.getRoot());
            this.binding = b;
        }

        public void bind(Context context, final Order currentOrder, OnOrderItemClickListener itemClickListener){

            binding.tvType.setText("Type: " + currentOrder.getChosenCoffeeType());
            binding.tvSize.setText("Size: " + currentOrder.getChosenCoffeeSize());
            binding.tvQty.setText("Quantity: " + currentOrder.getChosenCoffeeQuantity());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onCoffeeItemClicked(currentOrder);


                }
            });
        }

    }
}
