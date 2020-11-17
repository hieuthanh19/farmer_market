package com.example.farmersmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.fragment.CategoryFragment;
import com.example.farmersmarket.object.ProductType;

import java.util.List;

public class CategoryDetail extends AppCompatActivity {

    private int categoryID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);

        Intent intent = getIntent();
        categoryID = intent.getIntExtra(CategoryFragment.CATEGORY, -1);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_category_detail, container, false);
        // get views
        RecyclerView recyclerViewProductVertical = view.findViewById(R.id.category_detail_fruit);

        // set up carousel banner
//        carouselView.setPageCount(bannerImages.length);
//        carouselView.setImageListener(imageListener());

        // get data from DB
        AppDatabase appDatabase = AppDatabase.getAppDatabase(view.getContext());
        List<ProductType> products = appDatabase.productTypeDAO().getAllProductType();
//        List<Product> saleProducts = appDatabase.productDAO().getActiveProductSaleRateDesc();

        // Set data to lists
//        ProductHorizontalViewAdapter productHorizontalSaleViewAdapter = new ProductHorizontalViewAdapter(saleProducts);
//        recyclerViewProductHorizontalSale.setAdapter(productHorizontalSaleViewAdapter);
//        ProductHorizontalViewAdapter productHorizontalTrendingViewAdapter = new ProductHorizontalViewAdapter(products);
//        recyclerViewProductHorizontalTrending.setAdapter(productHorizontalTrendingViewAdapter);

           // coi lại giùm anh đoạn này nha
//        ProductVerticalViewAdapter productVerticalViewAdapter = new ProductVerticalViewAdapter(products);
//        recyclerViewProductVertical.setAdapter(productVerticalViewAdapter);

        //Set Cart button event
//        home_cart = view.findViewById(R.id.home_cart);
//        home_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), Cart.class);
//                startActivity(intent);
//            }
//        });

        return view;
    }

}