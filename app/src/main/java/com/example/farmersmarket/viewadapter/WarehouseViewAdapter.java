package com.example.farmersmarket.viewadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersmarket.R;
import com.example.farmersmarket.object.StoreHouse;

import java.util.List;

public class WarehouseViewAdapter extends RecyclerView.Adapter<WarehouseViewAdapter.ViewHolder> {
    private final List<StoreHouse> storeHouses;
//    private Context mContext;

    public WarehouseViewAdapter(List<StoreHouse> storeHouses) {
        this.storeHouses = storeHouses;
//        this.mContext = context;
    }

    /**
     * Required method for creating the viewholder objects.
     *
     * @param parent   The ViewGroup into which the new View will be added
     *                 after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return The newly created ViewHolder.
     */
    @Override
    public WarehouseViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.warehouse_item, parent, false);
        return new WarehouseViewAdapter.ViewHolder(view);
    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder   The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(final WarehouseViewAdapter.ViewHolder holder, int position) {
        // Get current product
        StoreHouse storeHouse = storeHouses.get(position);
        //Populate the view with data
        holder.bindTo(storeHouse);

    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return storeHouses.size();
    }

    @Override
    public long getItemId(int position) {
        return ((StoreHouse) storeHouses.get(position)).storeHouseID;
    }

    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView warehouseName;
        public final TextView warehouseAddress;


        public ViewHolder(View view) {
            super(view);

            warehouseName = view.findViewById(R.id.warehouse_name);
            warehouseAddress = view.findViewById(R.id.warehouse_address);
        }

        void bindTo(StoreHouse storeHouse) {
            // Populate views with data
            warehouseName.setText(storeHouse.storeName);
            warehouseAddress.setText(storeHouse.address);
        }
    }
}
