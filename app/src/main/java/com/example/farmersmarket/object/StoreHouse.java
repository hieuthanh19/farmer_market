package com.example.farmersmarket.object;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "store_house", foreignKeys = @ForeignKey(entity = Account.class, parentColumns = "accountID", childColumns = "accountID"))
public class StoreHouse {
    @PrimaryKey
    public int storeHouseID;

    public int accountID;
    public String storeName;
    public String address;
    public float longitude;
    public float latiue;
    public String description;
    public int status;
}
