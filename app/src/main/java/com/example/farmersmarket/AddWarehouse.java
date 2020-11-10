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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.StoreHouse;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddWarehouse extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int CAMERA_PERMISSION_REQUEST_CODE = 1;

    AppDatabase appDatabase;
    String currentPhotoPath;
    private Uri photoURI;

    TextInputLayout nameLayout;
    TextInputLayout addressLayout;
    ImageView warehouseImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_warehouse);

        nameLayout = findViewById(R.id.add_warehouse_name_layout);
        addressLayout = findViewById(R.id.add_warehouse_address_layout);
        warehouseImage = findViewById(R.id.add_warehouse_image_view);

        appDatabase = AppDatabase.getAppDatabase(this);
    }

    public void back(View view) {
        onBackPressed();
    }

    /**
     * onClick handler. Create new warehouse
     *
     * @param view Create Warehouse Button
     */
    public void createWarehouse(View view) {
        // Check whether all required fields are filled
        if (isFilled()) {
            // Insert new warehouse to DB
            appDatabase.storeHouseDAO().insertStoreHouse(new StoreHouse(App.ACCOUNT_ID,
                    nameLayout.getEditText().getText().toString(),
                    addressLayout.getEditText().getText().toString(), 1, 1, "", 1));
            Intent intent = new Intent(this, Warehouse.class);
            Toast.makeText(this, getText(R.string.add_warehouse_create_success), Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }

    /**
     * Check if all text fields have been filled
     *
     * @return true if text fields are filled, false if not
     */
    private boolean isFilled() {
        boolean isFilled = true;
        // Check name text field
        if (nameLayout.getEditText().getText().toString().trim().length() == 0) {
            nameLayout.setError("Field can not be empty!");
            isFilled = false;

        } else {
            nameLayout.setError(null);
        }
        // Check address text field
        if (addressLayout.getEditText().getText().toString().trim().length() == 0) {
            addressLayout.requestFocus();
            addressLayout.setError("Field can not be empty!");
            isFilled = false;
        } else {
            addressLayout.setError(null);
        }
        return isFilled;
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
            // Load photo using Glide to avoid exception
            Glide.with(this).load(photoURI).centerCrop().into(warehouseImage);
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