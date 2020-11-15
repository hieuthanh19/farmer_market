package com.example.farmersmarket;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.Product;
import com.example.farmersmarket.object.ProductImage;
import com.example.farmersmarket.object.ProductType;
import com.example.farmersmarket.viewadapter.ProductImageAdapter;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddProduct extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
    private static int MODE;
    private static Product product;

    List<ProductImage> productImageList = new ArrayList<>();
    List<ProductType> productTypes = new ArrayList<>();
    private Uri photoURI;
    AppDatabase appDatabase;

    TextInputLayout nameInputLayout;
    TextInputLayout originalPriceInputLayout;
    TextInputLayout currentPriceInputLayout;
    TextInputLayout amountInputLayout;
    TextInputLayout originInputLayout;
    Spinner spinner;
    TextInputLayout descriptionInputLayout;
    ProductImageAdapter productImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Get mode from intent
        Intent intent = getIntent();
        MODE = intent.getIntExtra(WarehouseDetail.PRODUCT_MODE, -1);
        int productID = intent.getIntExtra(WarehouseDetail.PRODUCT_ID, -1);

        // Get views
        TextView title = findViewById(R.id.add_product_title);
        nameInputLayout = findViewById(R.id.add_product_name_layout);
        originalPriceInputLayout = findViewById(R.id.add_product_original_price_layout);
        currentPriceInputLayout = findViewById(R.id.add_product_current_price_layout);
        amountInputLayout = findViewById(R.id.add_product_amount_layout);
        originInputLayout = findViewById(R.id.add_product_origin_layout);
        spinner = findViewById(R.id.spinner);
        descriptionInputLayout = findViewById(R.id.add_product_description_layout);

        appDatabase = AppDatabase.getAppDatabase(this);
        // check mode
        if (MODE == WarehouseDetail.MODE_EDIT) {
            title.setText(R.string.add_product_title_mode_edit);
            if (productID != -1) {
                product = appDatabase.productDAO().getProduct(productID);
            } else {
                Toast.makeText(this, "Error: Cannot load product", Toast.LENGTH_SHORT).show();
            }
        } else {
            title.setText(R.string.add_product_title_mode_add);
        }
        loadData();
    }

    /**
     * Load data into views
     */
    private void loadData() {
        // Load data into spinner
        productTypes = appDatabase.productTypeDAO().getAllProductType();
        ArrayAdapter<ProductType> adapter = new ArrayAdapter<>(this, R.layout.category_item, productTypes);
        spinner.setAdapter(adapter);
        // In Edit mode -> set data for fields
        if (MODE == WarehouseDetail.MODE_EDIT) {
            nameInputLayout.getEditText().setText(product.name);
            originalPriceInputLayout.getEditText().setText(String.valueOf(product.price));
            currentPriceInputLayout.getEditText().setText(String.valueOf(product.currentPrice));
            amountInputLayout.getEditText().setText(String.valueOf(product.amount));
            originInputLayout.getEditText().setText(product.origin);
            // set category data
            int position = -1;
            for (int i = 0; i < productTypes.size(); i++) {
                if (productTypes.get(i).productTypeID == product.productTypeID)
                    position = i;
            }
            spinner.setSelection(position);
            // get product images
            productImageList = appDatabase.productImageDAO().getProductImageByProductID(product.productID);
        }

        // Set up product image list
        productImageAdapter = new ProductImageAdapter(productImageList);
        RecyclerView recyclerView = findViewById(R.id.add_product_image_list);
        recyclerView.setAdapter(productImageAdapter);

    }

    /**
     * Do Back Action
     *
     * @param view
     */
    public void back(View view) {
        onBackPressed();
    }

    /**
     * Actions when user is done
     *
     * @param view
     */
    public void done(View view) {
        if (isFilledAndCorrect()) {
            if (MODE == WarehouseDetail.MODE_ADD) {
                // Create new product and insert to DB
                int storehouseID = WarehouseDetail.WAREHOUSE_ID;
                int typeID = ((ProductType) spinner.getSelectedItem()).productTypeID;
                String name = nameInputLayout.getEditText().getText().toString();
                double amount = Double.parseDouble(amountInputLayout.getEditText().getText().toString());
                double price = Double.parseDouble(originalPriceInputLayout.getEditText().getText().toString());
                String origin = originInputLayout.getEditText().getText().toString();
                double currentPrice = Double.parseDouble(currentPriceInputLayout.getEditText().getText().toString());
                ;
                String description = descriptionInputLayout.getEditText().getText().toString();
                int status = 1;

                int productID = (int) appDatabase.productDAO().insertProduct(new Product(storehouseID, typeID, name,
                        amount, price, origin,
                        currentPrice, description, status));
                // insert product image to DB
                for (int i = 0; i < productImageList.size(); i++) {
                    // update productID because default value is -1
                    productImageList.get(i).productID = productID;
                }
                appDatabase.productImageDAO().insertProductImages(productImageList);
                Toast.makeText(this, "New product has been inserted successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, WarehouseDetail.class);
                intent.putExtra(Warehouse.WAREHOUSE_ID, WarehouseDetail.WAREHOUSE_ID);

                startActivity(intent);
            } else {
                product.productTypeID = ((ProductType) spinner.getSelectedItem()).productTypeID;
                product.name = nameInputLayout.getEditText().getText().toString();
                product.amount = Double.parseDouble(amountInputLayout.getEditText().getText().toString());
                product.price = Double.parseDouble(originalPriceInputLayout.getEditText().getText().toString());
                product.origin = originInputLayout.getEditText().getText().toString();
                product.currentPrice = Double.parseDouble(currentPriceInputLayout.getEditText().getText().toString());
                ;
                product.description = descriptionInputLayout.getEditText().getText().toString();

                // delete current images
                appDatabase.productImageDAO().deleteProductImages(appDatabase.productImageDAO().getProductImageByProductID(product.productID));
                // insert product image to DB
                for (int i = 0; i < productImageList.size(); i++) {
                    // update productID because default value is -1
                    productImageList.get(i).productID = product.productID;
                }
                appDatabase.productImageDAO().insertProductImages(productImageList);

                // update product
                appDatabase.productDAO().updateProduct(product);
                Toast.makeText(this, "Product has been updated successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, WarehouseDetail.class);
                intent.putExtra(Warehouse.WAREHOUSE_ID, WarehouseDetail.WAREHOUSE_ID);

                startActivity(intent);
            }
        }
    }

    /**
     * Check whether all input fields are filled and match condition. If not, display error
     *
     * @return true if all fields are filled and match condition. Otherwise, false
     */
    private boolean isFilledAndCorrect() {
        boolean isFilledAndCorrect = true;

        // check name input
        if (nameInputLayout.getEditText().getText().toString().isEmpty()) {
            nameInputLayout.setError(getString(R.string.error_empty_field));
            isFilledAndCorrect = false;
        }
        // check price input
        if (originalPriceInputLayout.getEditText().getText().toString().isEmpty()) {
            originalPriceInputLayout.setError(getText(R.string.error_empty_field));
            isFilledAndCorrect = false;
        } else if (Double.parseDouble(originalPriceInputLayout.getEditText().getText().toString()) < 0) {
            originalPriceInputLayout.setError(getString(R.string.add_product_price_less_than_0));
            isFilledAndCorrect = false;
        }
        // check amount input
        if (amountInputLayout.getEditText().getText().toString().isEmpty()) {
            amountInputLayout.setError(getText(R.string.error_empty_field));
            isFilledAndCorrect = false;
        } else if (Double.parseDouble(amountInputLayout.getEditText().getText().toString()) < 0) {
            amountInputLayout.setError(getString(R.string.add_product_amount_less_than_0));
            isFilledAndCorrect = false;
        }
        // check origin input
        if (originInputLayout.getEditText().getText().toString().isEmpty()) {
            originInputLayout.setError(getText(R.string.error_empty_field));
            isFilledAndCorrect = false;
        }
        return isFilledAndCorrect;
    }

    /**
     * onClick handler: Take photo
     *
     * @param view
     */
    public void takePhoto(View view) {
        askCameraPermission();
    }

    /**
     * Ask for Camera and read storage permission
     */
    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, getText(R.string.add_warehouse_permission_not_granted), Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * Create new implicit intent to capture photo
     */
    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create image file to save hi-res photo
            photoURI = createImageFile(this);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // If user has taken photo
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            // Add new Image to list and set default productID = -1
            productImageList.add(new ProductImage(-1, photoURI.toString(), 1));
            productImageAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Create new file to save high resolution photos. Default file name is current date_time
     *
     * @param context Application's Context
     * @return Uri of newly created file
     */
    private Uri createImageFile(Context context) {

        File imageFile = null;
        Uri imageUri = null;
        String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // If phone run on Android Q or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentResolver resolver = context.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, fileName);
            contentValues.put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/png");
            contentValues.put(MediaStore.Images.ImageColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_PICTURES + File.separator + "FarmerMarket");
            imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        } else {
            // Disable strict mode
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            // Get imageDir
            String imagesDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES).toString();
            // Create new file from imageDir
            imageFile = new File(imagesDir);
            // check whether imageFile exist
            if (!imageFile.exists()) {
                imageFile.mkdir();
            }
            imageFile = new File(imagesDir, fileName + ".png");

            if (imageFile != null)  // pre Q
            {
                MediaScannerConnection.scanFile(context, new String[]{imageFile.toString()}, null, null);
                imageUri = Uri.fromFile(imageFile);
            }
        }
        return imageUri;
    }
}