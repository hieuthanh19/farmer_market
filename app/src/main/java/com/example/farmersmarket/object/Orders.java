package com.example.farmersmarket.object;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Date;

@Entity(tableName = "orders", foreignKeys =  {
        @ForeignKey(entity = Account.class, parentColumns = "accountID", childColumns = "accountID"),
        @ForeignKey(entity = ShippingUnit.class, parentColumns = "shippingID", childColumns = "shippingID"),
        @ForeignKey(entity = StoreHouse.class, parentColumns = "storeHouseID", childColumns = "storeHouseID")
})
@TypeConverters(ConvertDate.class)
public class Orders {
    @PrimaryKey
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

    public Orders(int orderID, int storeHouseID, int accountID, int shippingID, Date orderedDate, Date deliveredDate, String address, double total, String description, int status) {
        this.orderID = orderID;
        this.storeHouseID = storeHouseID;
        this.accountID = accountID;
        this.shippingID = shippingID;
        this.orderedDate = orderedDate;
        this.deliveredDate = deliveredDate;
        this.address = address;
        this.total = total;
        this.description = description;
        this.status = status;
    }

    public Orders() {
    }
}
