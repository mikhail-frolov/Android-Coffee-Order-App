// Name: Mikhail Frolov
// ID: 164788184
package com.example.mikhail_myorder.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Order implements Parcelable {

    private int chosenCoffeeId;
    private String chosenCoffeeType;
    private String chosenCoffeeSize;
    private String chosenCoffeeQuantity;

    public Order() {}

    public Order(Integer id, String type, String size, String qty) {

        this.chosenCoffeeQuantity = qty;
        this.chosenCoffeeType = type;
        this.chosenCoffeeSize = size;
    }

    protected Order(Parcel in) {
        chosenCoffeeId =in.readInt();
        chosenCoffeeType = in.readString();
        chosenCoffeeSize = in.readString();
        chosenCoffeeQuantity = in.readString();
    }

    public int getChosenCoffeeId() {
        return chosenCoffeeId;
    }

    public void setChosenCoffeeId(int chosenCoffeeId) {
        this.chosenCoffeeId = chosenCoffeeId;
    }
    public String getChosenCoffeeType() {
        return chosenCoffeeType;
    }

    public void setChosenCoffeeType(String type) {
        this.chosenCoffeeType = type;
    }

    public String getChosenCoffeeSize() {
        return chosenCoffeeSize;
    }

    public void setChosenCoffeeSize(String size) { this.chosenCoffeeSize = size; }

    public String getChosenCoffeeQuantity() {
        return chosenCoffeeQuantity;
    }

    public void setChosenCoffeeQuantity(String qty) {
        this.chosenCoffeeQuantity = qty;
    }

    @Override
    public String toString() {
        return "Order{" +
                "type='" + chosenCoffeeType + '\'' +
                ", size='" + chosenCoffeeSize + '\'' +
                ", quantity='" + chosenCoffeeQuantity + '\'' +
                '}';
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(chosenCoffeeType);
        dest.writeString(chosenCoffeeSize);
        dest.writeString(chosenCoffeeQuantity);
    }


}
