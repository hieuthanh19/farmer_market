package com.example.farmersmarket.object;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "store_house",
        foreignKeys = @ForeignKey(entity = Account.class, parentColumns = "accountID", childColumns = "accountID"),
        indices = @Index("accountID"))
public class StoreHouse {
    @PrimaryKey(autoGenerate = true)
    public int storeHouseID;
    public int accountID;
    public String storeName;
    public String address;
    public double longitude;
    public double latitude;
    public String description;
    public int status;

    @Ignore
    public StoreHouse(int accountID, String storeName, String address, double longitude, double latitude,
                      String description, int status) {
        this.accountID = accountID;
        this.storeName = storeName;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
        this.status = status;
    }

    public StoreHouse() {
    }
}
