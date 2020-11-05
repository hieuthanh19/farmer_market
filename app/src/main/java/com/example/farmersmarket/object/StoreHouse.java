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

    public StoreHouse(int storeHouseID, int accountID, String storeName, String address, float longitude, float latiue, String description, int status) {
        this.storeHouseID = storeHouseID;
        this.accountID = accountID;
        this.storeName = storeName;
        this.address = address;
        this.longitude = longitude;
        this.latiue = latiue;
        this.description = description;
        this.status = status;
    }

    public StoreHouse() {
    }
}
