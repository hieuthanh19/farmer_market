package com.example.farmersmarket.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.farmersmarket.R;
import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.Product;
import com.example.farmersmarket.viewadapter.ProductHorizontalViewAdapter;
import com.example.farmersmarket.viewadapter.ProductVerticalViewAdapter;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    CarouselView carouselView;
    int[] bannerImages = {R.drawable.peticide, R.drawable.autumn_sale, R.drawable.autumn_sale_70};

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {

        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        // get views
        carouselView = view.findViewById(R.id.home_banner_carousel);
        RecyclerView recyclerViewProductVertical = view.findViewById(R.id.home_vertical_list_suggestion);
        RecyclerView recyclerViewProductHorizontalTrending = view.findViewById(R.id.product_horizontal_list_trending);
        RecyclerView recyclerViewProductHorizontalSale = view.findViewById(R.id.product_horizontal_list_sale);

        // set up carousel banner
        carouselView.setPageCount(bannerImages.length);
        carouselView.setImageListener(imageListener());

        // get data from DB
        AppDatabase appDatabase = AppDatabase.getAppDatabase(view.getContext());
        List<Product> products = appDatabase.productDAO().getAllActiveProduct();
        List<Product> saleProducts = appDatabase.productDAO().getActiveProductSaleRateDesc();

        // Set data to lists
        ProductHorizontalViewAdapter productHorizontalSaleViewAdapter = new ProductHorizontalViewAdapter(saleProducts);
        recyclerViewProductHorizontalSale.setAdapter(productHorizontalSaleViewAdapter);
        ProductHorizontalViewAdapter productHorizontalTrendingViewAdapter = new ProductHorizontalViewAdapter(products);
        recyclerViewProductHorizontalTrending.setAdapter(productHorizontalTrendingViewAdapter);
        ProductVerticalViewAdapter productVerticalViewAdapter = new ProductVerticalViewAdapter(products);
        recyclerViewProductVertical.setAdapter(productVerticalViewAdapter);

        return view;
    }

    /**
     * ImageListener for Banner section
     *
     * @return a {@link ImageListener}
     */
    private ImageListener imageListener() {
        return new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                //Picasso.with(getView().getContext()).load(bannerImages[position]).centerInside().into(imageView);
                Glide.with(getView().getContext()).load(bannerImages[position]).fitCenter().into(imageView);
            }
        };
    }
}