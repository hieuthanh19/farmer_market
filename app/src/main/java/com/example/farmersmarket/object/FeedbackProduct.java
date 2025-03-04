package com.example.farmersmarket.object;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.TypeConverters;

import java.sql.Date;

@Entity(tableName = "feedback_product",
        primaryKeys = {"productID", "accountID"},
        foreignKeys = {
                @ForeignKey(entity = Account.class, parentColumns = "accountID", childColumns = "accountID"),
                @ForeignKey(entity = Product.class, parentColumns = "productID", childColumns = "productID")
        },
        indices = {
                @Index("accountID"),
                @Index("productID")
        })
@TypeConverters(ConvertDate.class)
public class FeedbackProduct {
    public int productID;
    public int accountID;
    public float rating;
    public Date time;
    public String description;
    public int status;

    @Ignore
    public FeedbackProduct(int productID, int accountID, float rating, Date time, String description, int status) {
        this.productID = productID;
        this.accountID = accountID;
        this.rating = rating;
        this.time = time;
        this.description = description;
        this.status = status;
    }

    public FeedbackProduct() {
    }
}
