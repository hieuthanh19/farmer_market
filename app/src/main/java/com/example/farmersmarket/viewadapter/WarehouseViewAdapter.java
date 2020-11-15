package com.example.farmersmarket.viewadapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersmarket.AddWarehouse;
import com.example.farmersmarket.R;
import com.example.farmersmarket.Warehouse;
import com.example.farmersmarket.WarehouseDetail;
import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.StoreHouse;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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


    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public final TextView warehouseName;
        public final TextView warehouseAddress;
        public final TextView warehouseEdit;
        public final TextView warehouseDelete;

        public ViewHolder(View view) {
            super(view);

            warehouseName = view.findViewById(R.id.warehouse_name);
            warehouseAddress = view.findViewById(R.id.warehouse_address);
            warehouseEdit = view.findViewById(R.id.warehouse_edit);
            warehouseDelete = view.findViewById(R.id.warehouse_delete);

            view.setOnClickListener(this);

        }

        void bindTo(StoreHouse storeHouse) {
            // Populate views with data
            warehouseName.setText(storeHouse.storeName);
            warehouseAddress.setText(storeHouse.address);

            warehouseEdit.setOnClickListener(onClickEdit(storeHouse.storeHouseID));
            warehouseDelete.setOnClickListener(onClickDelete(storeHouse));
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), WarehouseDetail.class);
            int storeHouseID = storeHouses.get(getLayoutPosition()).storeHouseID;
            intent.putExtra(Warehouse.WAREHOUSE_ID, storeHouseID);
            intent.putExtra(Warehouse.WAREHOUSE_MODE, Warehouse.MODE_ADD);
            v.getContext().startActivity(intent);
        }

        /**
         * Set action for Edit button. This will open {@link WarehouseDetail} activity with {@code Warehouse.MODE_EDIT}
         *
         * @param storeHouseID ID of storehouse to edit
         * @return a {@link View.OnClickListener}
         */
        private View.OnClickListener onClickEdit(int storeHouseID) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), AddWarehouse.class);
                    intent.putExtra(Warehouse.WAREHOUSE_ID, storeHouseID);
                    intent.putExtra(Warehouse.WAREHOUSE_MODE, Warehouse.MODE_EDIT);
                    v.getContext().startActivity(intent);
                }
            };
        }

        /**
         * Set action for Delete button. This will display a {@link MaterialAlertDialogBuilder} with 2 options:
         * Cancel and Delete
         *
         * @param storeHouse Storehouse to delete
         * @return a {@link View.OnClickListener}
         */
        private View.OnClickListener onClickDelete(StoreHouse storeHouse) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create a dialog to confirm
                    new MaterialAlertDialogBuilder(itemView.getContext())
                            .setTitle(v.getResources().getString(R.string.dialog_title_delete, storeHouse.storeName))
                            .setMessage(R.string.dialog_delete_message)
                            .setNegativeButton(R.string.dialog_option_cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setPositiveButton(R.string.dialog_option_delete, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // soft delete store house
                                    storeHouse.status = 0;
                                    AppDatabase.getAppDatabase(itemView.getContext()).storeHouseDAO().updateStoreHouse(storeHouse);
                                    storeHouses.remove(storeHouse);
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                    // Display message
                                    Toast.makeText(v.getContext(),
                                            v.getContext().getResources().getString(R.string.toast_delete_success,
                                                    storeHouse.storeName), Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                }
            };
        }
    }
}
