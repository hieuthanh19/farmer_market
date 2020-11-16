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
import com.example.farmersmarket.object.Utils;

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

                    // Create pseudo data
                    appDatabase.accountTypeDAO().insertAccountType(new AccountType("Admin", 1));

                    appDatabase.productTypeDAO().insertProductType(new ProductType("Fruit", 1));
                    appDatabase.productTypeDAO().insertProductType(new ProductType("Vegetable", 1));
                    appDatabase.productTypeDAO().insertProductType(new ProductType("Fish & Seafood", 1));
                    appDatabase.productTypeDAO().insertProductType(new ProductType("Meat & Poultry", 1));

                    // Account 1
                    appDatabase.accountDAO().insertAccount(new Account(1, "0348204069", Utils.encryptPassword("admin"),
                            "Admin", 1, "Vĩnh Long",
                            "123@gmail.com", "", 1));
                    appDatabase.storeHouseDAO().insertStoreHouse(new StoreHouse(1, "FPTU CT", "Ninh Kiều, Cần Thơ",
                            10.0120585,
                            105.7294269,
                            "FPTU CT Campus", 1));
                    appDatabase.shippingUnitDAO().insertShippingUnit(new ShippingUnit(1, "Ship Nhanh Cần Thơ",
                            "0123456789",
                            10000, "", 1));

                    appDatabase.productDAO().insertProduct(new Product(1, 1, "Thanh Long", 12000, 23000, "Bình Thuận"
                            , 12000,
                            "Thanh Long nổi tiếng của Bình Thuận", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(1, "@drawable/dragon_fruit_1",
                            1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(1, "@drawable/dragon_fruit_2",
                            1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(1, "@drawable/dragon_fruit_3",
                            1));

                    appDatabase.productDAO().insertProduct(new Product(1, 1, "Sầu Riêng", 100, 70000, "Sóc Trăng",
                            69000,
                            "Sầu riêng đặc sản Sóc Trăng", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(2, "@drawable/durian_1", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(2, "@drawable/durian_2", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(2, "@drawable/durian_3", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(2, "@drawable/durian_4", 1));

                    appDatabase.productDAO().insertProduct(new Product(1, 1, "Chuối", 100, 7000, "Trà Vinh", 7000,
                            "Chuối cao sản Trà Vinh", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(3, "@drawable/banana_1", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(3, "@drawable/banana_2", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(3, "@drawable/banana_3", 1));

                    appDatabase.productDAO().insertProduct(new Product(1, 1, "Dừa", 5000, 5000, "Bến Tre", 5000,
                            "Dừa Xiêm nổi tiếng Bến Tre", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(4, "@drawable/coconut_1", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(4, "@drawable/coconut_2", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(4, "@drawable/coconut_3", 1));

                    appDatabase.productDAO().insertProduct(new Product(1, 1, "Cam sành", 3050, 10000, "Vĩnh Long", 9000,
                            "Cam sành nổi tiếng Vĩnh Long", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(5, "@drawable/orange_1", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(5, "@drawable/orange_2", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(5, "@drawable/orange_3", 1));


                    // Account 2
                    appDatabase.accountDAO().insertAccount(new Account(1, "0589003303", Utils.encryptPassword(
                            "joselongxuyen"),
                            "Quang Long Xuyên", 1, "Long Xuyen, An Giang",
                            "123@gmail.com", "", 1));
                    appDatabase.storeHouseDAO().insertStoreHouse(new StoreHouse(2, "Kho Long Xuyên", "Long Xuyên, An " +
                            "Giang",
                            10.0120585, 105.7294269,
                            "Tổng kho Long Xuyên của Quang", 1));
                    appDatabase.shippingUnitDAO().insertShippingUnit(new ShippingUnit(2, "Shipping Nhanh 3s",
                            "9876543210",
                            11000, "", 1));

                    appDatabase.productDAO().insertProduct(new Product(2, 2, "Khổ qua", 230, 52000, "Hậu Giang", 52000,
                            "Khổ qua đắng như cuộc đời của bạn", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(6, "@drawable/bitter_melon_1",
                            1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(6, "@drawable/bitten_melon_2",
                            1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(6, "@drawable/bitten_melon_3",
                            1));

                    appDatabase.productDAO().insertProduct(new Product(2, 2, "Bắp Cải", 110, 7000, "Đà Lạt", 6900,
                            "Bắp cải được trồng theo tiêu chuẩn VietGAP", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(7, "@drawable/cabbage_1", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(7, "@drawable/cabbage_2", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(7, "@drawable/cabbage_3", 1));

                    appDatabase.productDAO().insertProduct(new Product(2, 2, "Cà rốt Đà Lạt", 3400, 8000, "Đà Lạt",
                            7000,
                            "Cà rốt chính gốc Đà Lạt", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(8, "@drawable/carrot_1", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(8, "@drawable/carrot_2", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(8, "@drawable/carrot_3", 1));

                    appDatabase.productDAO().insertProduct(new Product(2, 2, "Cần tây", 150, 5000, "An Giang", 5000,
                            "Thảo mộc thêm hương vị cho cuộc sống", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(9, "@drawable/celery_1", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(9, "@drawable/celery_2", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(9, "@drawable/celery_3", 1));

                    appDatabase.productDAO().insertProduct(new Product(2, 2, "Dưa leo VietGAP", 2300, 23000, "Vĩnh " +
                            "Long", 20000,
                            "Dưa leo được trồng theo tiêu chuẩn VietGAP", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(10, "@drawable/cucumber_1", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(10, "@drawable/cucumber_2", 1));
                    appDatabase.productImageDAO().insertProductImage(new ProductImage(10, "@drawable/cucumber_3", 1));

                }
            }
        }
        return appDatabase;
    }
}
