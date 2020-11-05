package com.example.farmersmarket.object;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "product",primaryKeys = {"productID","accountID"})
public class FeedbackProduct {
    public int productID ;
    public int accountID;
    public float rating;
    public Date time;
    public String description;
    public int status;
}
