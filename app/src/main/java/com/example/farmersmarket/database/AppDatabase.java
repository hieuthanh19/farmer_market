package com.example.farmersmarket.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.farmersmarket.dao.AccountDAO;
import com.example.farmersmarket.dao.AccountTypeDAO;
import com.example.farmersmarket.dao.CurrentAccountDAO;
import com.example.farmersmarket.dao.FeedbackProductDAO;
import com.example.farmersmarket.dao.FeedbackShippingDAO;
import com.example.farmersmarket.dao.NotificationDAO;
import com.example.farmersmarket.dao.OrderDetailDAO;
import com.example.farmersmarket.dao.OrdersDAO;
import com.example.farmersmarket.dao.ProductDAO;
import com.example.farmersmarket.dao.ProductImageDAO;
import com.example.farmersmarket.dao.ProductTypeDAO;
import com.example.farmersmarket.dao.ShippingUnitDAO;
import com.example.farmersmarket.dao.StoreHouseDAO;
import com.example.farmersmarket.object.Account;
import com.example.farmersmarket.object.AccountType;
import com.example.farmersmarket.object.CurrentAccount;
import com.example.farmersmarket.object.FeedbackProduct;
import com.example.farmersmarket.object.FeedbackShipping;
import com.example.farmersmarket.object.Notification;
import com.example.farmersmarket.object.OrderDetail;
import com.example.farmersmarket.object.Orders;
import com.example.farmersmarket.object.Product;
import com.example.farmersmarket.object.ProductImage;
import com.example.farmersmarket.object.ProductType;
import com.example.farmersmarket.object.ShippingUnit;
import com.example.farmersmarket.object.StoreHouse;

@Database(entities = {
        Account.class,
        AccountType.class,
        CurrentAccount.class,
        FeedbackProduct.class,
        FeedbackShipping.class,
        Notification.class,
        OrderDetail.class,
        Orders.class,
        Product.class,
        ProductImage.class,
        ProductType.class,
        ShippingUnit.class,
        StoreHouse.class,
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;
    public static final String DATABASE_NAME = "farmer_market";

    public abstract AccountDAO accountDAO();

    public abstract AccountTypeDAO accountTypeDAO();

    public abstract CurrentAccountDAO currentAccountDAO();

    public abstract FeedbackProductDAO feedBackProductDAO();

    public abstract FeedbackShippingDAO feedbackShippingDAO();

    public abstract NotificationDAO notificationDAO();

    public abstract OrderDetailDAO orderDetailDAO();

    public abstract OrdersDAO ordersDAO();

    public abstract ProductDAO productDAO();

    public abstract ProductImageDAO productImageDAO();

    public abstract ProductTypeDAO productTypeDAO();

    public abstract ShippingUnitDAO shippingUnitDAO();

    public abstract StoreHouseDAO storeHouseDAO();

    public static AppDatabase getAppDatabase(final Context context) {
        if (appDatabase == null) {
            synchronized (AppDatabase.class) {
                if (appDatabase == null) {
                    appDatabase = Room.databaseBuilder(context, AppDatabase.class,
                            DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return appDatabase;
    }
}
