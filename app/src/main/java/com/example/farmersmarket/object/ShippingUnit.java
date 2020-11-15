package com.example.farmersmarket.object;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "shipping_unit",
        foreignKeys = @ForeignKey(entity = Account.class, parentColumns = "accountID", childColumns = "accountID"),
        indices = @Index("accountID"))
public class ShippingUnit {
    @PrimaryKey(autoGenerate = true)
    public int shippingID;
    public int accountID;
    public String name;
    public String phoneNumber;
    public double transportFee;
    public String image;
    public int status;

    @Ignore
    public ShippingUnit(int shippingID, int accountID, String name, String phoneNumber, double transportFee,
                        String image, int status) {
        this.shippingID = shippingID;
        this.accountID = accountID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.transportFee = transportFee;
        this.image = image;
        this.status = status;
    }

    public ShippingUnit() {
    }
}
