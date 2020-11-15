package com.example.farmersmarket.object;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.TypeConverters;

import java.sql.Date;

@Entity(tableName = "feedback_shipping",
        primaryKeys = {"accountID", "shippingID"},
        foreignKeys = {
                @ForeignKey(entity = Account.class, parentColumns = "accountID", childColumns = "accountID"),
                @ForeignKey(entity = ShippingUnit.class, parentColumns = "shippingID", childColumns = "shippingID")
        },
        indices = {
                @Index("accountID"),
                @Index("shippingID")
        })
@TypeConverters(ConvertDate.class)
public class FeedbackShipping {
    public int accountID;
    public int shippingID;
    public float rating;
    public Date time;
    public String description;
    public int status;

    @Ignore
    public FeedbackShipping(int accountID, int shippingID, float rating, Date time, String description, int status) {
        this.accountID = accountID;
        this.shippingID = shippingID;
        this.rating = rating;
        this.time = time;
        this.description = description;
        this.status = status;
    }

    public FeedbackShipping() {
    }
}
