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

    List<ProductImage> productImageList = new ArrayList<>();
    List<ProductType> productTypes = new ArrayList<>();
    private Uri photoURI;

    AppDatabase appDatabase;
    TextInputLayout nameInputlayout;
    TextInputLayout priceInputLayout;
    TextInputLayout amountInputLayout;
    TextInputLayout originInputLayout;
    Spinner spinner;
    TextInputLayout descriptionInputLayout;
    ProductImageAdapter productImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        appDatabase = AppDatabase.getAppDatabase(this);
        nameInputlayout = findViewById(R.id.add_product_name_layout);
        priceInputLayout = findViewById(R.id.add_product_price_layout);
        amountInputLayout = findViewById(R.id.add_product_amount_layout);
        originInputLayout = findViewById(R.id.add_product_origin_layout);
        spinner = findViewById(R.id.spinner);
        descriptionInputLayout = findViewById(R.id.add_product_description_layout);

        // Load data into spinner
        productTypes = appDatabase.productTypeDAO().getAllProductType();
        ArrayAdapter<ProductType> adapter = new ArrayAdapter<>(this, R.layout.category_item, productTypes);
        spinner.setAdapter(adapter);

        // Set up product image list
        productImageAdapter = new ProductImageAdapter(productImageList);
        RecyclerView recyclerView = findViewById(R.id.add_product_image_list);
        recyclerView.setAdapter(productImageAdapter);
    }

    public void back(View view) {
        onBackPressed();
    }

    public void addProduct(View view) {
        if (isFilledAndCorrect()) {
            // Create new product and insert to DB
            int storehouseID = WarehouseDetail.WAREHOUSE_ID;
            int typeID = productTypes.get(spinner.getSelectedItemPosition()).productTypeID;
            String name = nameInputlayout.getEditText().getText().toString();
            int amount = Integer.parseInt(amountInputLayout.getEditText().getText().toString());
            double price = Double.parseDouble(priceInputLayout.getEditText().getText().toString());
            String origin = originInputLayout.getEditText().getText().toString();
            double currentPrice = price;
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
            Toast.makeText(this, "New product has been insert successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, WarehouseDetail.class);
            intent.putExtra(Warehouse.WAREHOUSE_ID, WarehouseDetail.WAREHOUSE_ID);
            startActivity(intent);
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
        if (nameInputlayout.getEditText().getText().toString().isEmpty()) {
            nameInputlayout.setError(getText(R.string.warehouse_empty));
            isFilledAndCorrect = false;
        }
        // check price input
        if (priceInputLayout.getEditText().getText().toString().isEmpty()) {
            priceInputLayout.setError(getText(R.string.warehouse_empty));
            isFilledAndCorrect = false;
        } else if (Integer.parseInt(priceInputLayout.getEditText().getText().toString()) < 0) {
            priceInputLayout.setError(getString(R.string.add_product_price_less_than_0));
            isFilledAndCorrect = false;
        }
        // check amount input
        if (amountInputLayout.getEditText().getText().toString().isEmpty()) {
            amountInputLayout.setError(getText(R.string.warehouse_empty));
            isFilledAndCorrect = false;
        } else if (Integer.parseInt(amountInputLayout.getEditText().getText().toString()) < 0) {
            amountInputLayout.setError(getString(R.string.add_product_amount_less_than_0));
            isFilledAndCorrect = false;
        }
        // check origin input
        if (originInputLayout.getEditText().getText().toString().isEmpty()) {
            originInputLayout.setError(getText(R.string.warehouse_empty));
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