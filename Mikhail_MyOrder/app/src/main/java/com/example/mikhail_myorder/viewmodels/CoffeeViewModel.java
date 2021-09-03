// Name: Mikhail Frolov
// ID: 164788184
package com.example.mikhail_myorder.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mikhail_myorder.db.Coffee;
import com.example.mikhail_myorder.models.Order;
import com.example.mikhail_myorder.repositories.CoffeeRepo;

import java.util.List;

public class CoffeeViewModel extends AndroidViewModel {
    private CoffeeRepo coffeeRepo;
    private LiveData<List<Coffee>> allDisplayCoffee;

    public CoffeeViewModel(Application application) {
        super(application);
        this.coffeeRepo = new CoffeeRepo(application);
        this.allDisplayCoffee = this.coffeeRepo.allCoffee;
    }

    public LiveData<List<Coffee>> getAllOrders() {
        return allDisplayCoffee;
    }

    public Coffee getCoffeeById(int id){
        return this.coffeeRepo.getCoffeeById(id);
    }

    public void insertCoffee(Coffee newCoffee){
        this.coffeeRepo.insertCoffee(newCoffee);
    }

    public void updateCoffee(Coffee coffee){
        this.coffeeRepo.updateCoffee(coffee);
    }

    public void deleteCoffee(Coffee coffee){
        this.coffeeRepo.deleteCoffee(coffee);
    }
}