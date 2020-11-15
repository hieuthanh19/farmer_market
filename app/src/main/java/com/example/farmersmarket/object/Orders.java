package com.example.farmersmarket.object;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Date;

@Entity(tableName = "orders",
        foreignKeys = {
                @ForeignKey(entity = Account.class, parentColumns = "accountID", childColumns = "accountID"),
                @ForeignKey(entity = ShippingUnit.class, parentColumns = "shippingID", childColumns = "shippingID"),
                @ForeignKey(entity = StoreHouse.class, parentColumns = "storeHouseID", childColumns = "storeHouseID")
        },
        indices = {
                @Index("shippingID"),
                @Index("accountID"),
                @Index("storeHouseID")
        })
@TypeConverters(ConvertDate.class)
public class Orders {
    @PrimaryKey(autoGenerate = true)
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

    @Ignore
    public Orders(int storeHouseID, int accountID, int shippingID, Date orderedDate, Date deliveredDate,
                  String address, double total, String description, int status) {
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
