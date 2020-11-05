package com.example.farmersmarket.object;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "shipping_unit", foreignKeys = @ForeignKey(entity = Account.class, parentColumns = "accountID",
        childColumns = "accountID"))
public class ShippingUnit {
    @PrimaryKey
    public int shippingID;

    public int accountID;
    public String name;
    public String phoneNumber;
    public double transportFee;
    public String image;
    public int status;
}
