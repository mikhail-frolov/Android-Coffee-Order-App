// Name: Mikhail Frolov
// ID: 164788184
package com.example.mikhail_myorder.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CoffeeDAO {

    @Query("SELECT * FROM tbl_coffee_order ORDER BY coffee_type ASC")
    LiveData<List<Coffee>> getAllCoffees();

    @Query("SELECT * FROM tbl_coffee_order WHERE coffee_id = :id")
    Coffee getCoffeeById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Coffee coffee);

    @Update
    void update(Coffee coffee);

    @Delete
    void delete(Coffee coffee);

//    Clear Table
//    @Query("DELETE FROM tbl_coffee_order")
//    void delete();

}
