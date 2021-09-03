// Name: Mikhail Frolov
// ID: 164788184
package com.example.mikhail_myorder.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_coffee_order")
public class Coffee {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "coffee_id")
    public int coffeeId;

    @NonNull
    @ColumnInfo(name = "coffee_type")
    public String coffeeType;

    @NonNull
    @ColumnInfo(name = "coffee_size")
    public String coffeeSize;

    @NonNull
    @ColumnInfo(name = "coffee_quantity")
    public String coffeeQuantity;

    public Coffee() {
    }

    public Coffee(int coffeeId, String coffeeType, String coffeeSize, String coffeeQuantity) {
        this.coffeeId = coffeeId;
        this.coffeeType = coffeeType;
        this.coffeeQuantity = coffeeQuantity;
        this.coffeeSize = coffeeSize;
    }

    public int getCoffeeId() {
        return coffeeId;
    }

    public void setCoffeeId(int id) {
        this.coffeeId = id;
    }

    public String getCoffeeType() {
        return coffeeType;
    }

    public void setCoffeeType(String type) {
        this.coffeeType = type;
    }

    public String getCoffeeSize() {
        return coffeeSize;
    }

    public void setCoffeeSize(String size) {
        this.coffeeSize = size;
    }

    public String getCoffeeQuantity() {
        return coffeeQuantity;
    }

    public void setCoffeeQuantity(String qty) {
        this.coffeeQuantity = qty;
    }


    @Override
    public String toString() {
        return "Coffee{" +
                "coffeeID=" + coffeeId +
                ", type='" + coffeeType + '\'' +
                ", size='" + coffeeSize + '\'' +
                '}';
    }
}
