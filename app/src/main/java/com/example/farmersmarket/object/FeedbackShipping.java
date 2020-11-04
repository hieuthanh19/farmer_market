package com.example.farmersmarket.object;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "feedback_shipping", primaryKeys = {"accountID", "shippingID"}, foreignKeys =
        {
                @ForeignKey(entity = Account.class, parentColumns = "accountID", childColumns = "accountID"),
                @ForeignKey(entity = ShippingUnit.class, parentColumns = "shippingID",
                        childColumns = "shippingID")
        }
)
public class FeedbackShipping {
    public int accountID;
    public int shippingID;
    public float rating;
    public Date time;
    public String description;
    public int status;
}
