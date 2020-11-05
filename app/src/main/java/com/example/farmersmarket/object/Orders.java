package com.example.farmersmarket.object;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.sql.Date;

@Entity(tableName = "orders", foreignKeys =  {
        @ForeignKey(entity = Account.class, parentColumns = "accountID", childColumns = "accountID"),
        @ForeignKey(entity = ShippingUnit.class, parentColumns = "shippingID", childColumns = "shippingID"),
        @ForeignKey(entity = StoreHouse.class, parentColumns = "storeHouseID", childColumns = "storeHouseID")
})
public class Orders {
    public int orderID;
    public int storeHouseID;
    public int accountID;
    public int shippingID;
    public Date orderedDate;
    public Date deliveredDate;
    public String address;
    public double total;
    public String description;
    public int status;
}
