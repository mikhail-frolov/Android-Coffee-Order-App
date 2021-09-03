// Name: Mikhail Frolov
// ID: 164788184
package com.example.mikhail_myorder.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mikhail_myorder.db.AppDatabase;
import com.example.mikhail_myorder.db.Coffee;
import com.example.mikhail_myorder.db.CoffeeDAO;
import com.example.mikhail_myorder.models.Order;

import java.util.List;

public class CoffeeRepo {
    private AppDatabase db;
    private CoffeeDAO coffeeDAO;
    public LiveData<List<Coffee>> allCoffee;

    public CoffeeRepo(Application application){
        this.db = AppDatabase.getDatabase(application);
        this.coffeeDAO = this.db.coffeeDAO();
        this.allCoffee = this.coffeeDAO.getAllCoffees();
    }

    public void insertCoffee(Coffee newCoffee){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            coffeeDAO.insert(newCoffee);
        });
    }

    public LiveData<List<Coffee>> getAllCoffees() {
        return this.allCoffee;
    }

    public Coffee getCoffeeById(int id){
        return this.coffeeDAO.getCoffeeById(id);
    }

    public void updateCoffee(Coffee coffee){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            this.coffeeDAO.update(coffee);
        });
    }

    public void deleteCoffee(Coffee coffee){

        AppDatabase.databaseWriteExecutor.execute(() -> {
            this.coffeeDAO.delete(coffee);
            // Clear Table
            //  this.coffeeDAO.delete();
        });
    }
}
