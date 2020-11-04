package com.example.farmersmarket.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.farmersmarket.dao.AccountDAO;
import com.example.farmersmarket.dao.AccountTypeDAO;
import com.example.farmersmarket.dao.FeedbackShippingDAO;
import com.example.farmersmarket.dao.NotificationDAO;
import com.example.farmersmarket.dao.OrdersDAO;
import com.example.farmersmarket.dao.ShippingUnitDAO;
import com.example.farmersmarket.object.Account;
import com.example.farmersmarket.object.AccountType;
import com.example.farmersmarket.object.FeedbackShipping;
import com.example.farmersmarket.object.Notification;
import com.example.farmersmarket.object.Orders;
import com.example.farmersmarket.object.ShippingUnit;

@Database(entities = {
        Account.class,
        AccountType.class,
        FeedbackShipping.class,
        Notification.class,
        Orders.class,
        ShippingUnit.class
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "farmer_market";


    public abstract AccountDAO accountDAO();

    public abstract AccountTypeDAO accountTypeDAO();

    public abstract FeedbackShippingDAO feedbackShippingDAO();

    public abstract NotificationDAO notificationDAO();

    public abstract OrdersDAO ordersDAO();

    public abstract ShippingUnitDAO shippingUnitDAO();

    public static AppDatabase getAppDatabase(final Context context) {
        if (appDatabase == null) {
            synchronized (AppDatabase.class) {
                if (appDatabase == null) {
                    appDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                            DATABASE_NAME)
                            .fallbackToDestructiveMigration().build();
                }
            }
        }
        return appDatabase;
    }
}
