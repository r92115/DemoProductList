package com.app.demo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.demo.R;
import com.app.demo.fragment.ProductListFragment;
import com.app.demo.model.ProductListModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Rajeev on 9/11/16.
 *
 * Adapter for product list.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder>{

    // list of products
    private final List<ProductListModel> productListModelList;

    // product list fragment instance.
    private final ProductListFragment mProductListFragment;

    // product list fragment item click listener.
    private final ProductListFragment.OnProductListFragmentInteractionListener mListener;

    /**
     * parametrised constructor for getting values from fragment and initializing the views.
     *
     * @param productListModels passing delivery item details.
     * @param productListFragment  passing home fragment.
     */
    public ProductListAdapter(List<ProductListModel> productListModels,
                              ProductListFragment productListFragment,
                              ProductListFragment.OnProductListFragmentInteractionListener listener) {
        productListModelList = productListModels;
        mProductListFragment = productListFragment;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        setValueOnViewHolder(holder, position);
    }

    /**
     * this method will set values on card view's item.
     * @param holder passing view holder
     * @param position passing item's position
     */
    private void setValueOnViewHolder(final ViewHolder holder, final int position) {
        holder.productListModel = productListModelList.get(position);

        Glide.with(mProductListFragment)
                .load(holder.productListModel.getLogoThumb())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivThumbNail);

        holder.tvProductTitle.setText(holder.productListModel.getTitle());
        holder.tvBasePrice.setText(holder.productListModel.getBasePrice());
        holder.tvSalePrice.setText(holder.productListModel.getSalePrice());

        if (holder.productListModel.getProductType().equalsIgnoreCase("Cloths")) {
            holder.tvSpnrProductLabel.setText(R.string.txt_hint_size);
        } else {
            holder.tvSpnrProductLabel.setText(R.string.txt_hint_tags);
        }

        // adding values into the spinner
        String[] sizeOrModels = holder.productListModel.getSizeOrModel().split(",");

        List<String> stringList = new ArrayList<>();

        for (String sizeOrModel:
                sizeOrModels) {
            stringList.add(sizeOrModel.replaceAll("[^a-zA-Z0-9]",""));
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(mProductListFragment.getActivity(),
                android.R.layout.simple_spinner_item, stringList);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spnrProductTagOrSize.setAdapter(dataAdapter);

        // set bookmark button
        if (holder.productListModel.isBookmarked()) {
            holder.ivBookmark.setImageResource(R.drawable.ic_bookmark);
        } else {
            holder.ivBookmark.setImageResource(R.drawable.ic_bookmark_white);
        }

        // bookmark button click
        holder.ivBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProductListFragment.onBookmarkButtonClick(holder.productListModel, position);
            }
        });

        // view click
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onProductListFragmentInteraction(holder.productListModel, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productListModelList == null ? 0 : productListModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * view holder class which will get the view's id.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;

        private final ImageView ivThumbNail;
        private final TextView tvProductTitle;
        private final TextView tvBasePrice;
        private final TextView tvSalePrice;
        private final TextView tvSpnrProductLabel;

        private final Spinner spnrProductTagOrSize;

        private final ImageView ivBookmark;

        private ProductListModel productListModel;

        /**
         * parametrised constructor which will initialize the views.
         *
         * @param view passing view.
         */
        private ViewHolder(View view) {
            super(view);
            mView = view;

            ivThumbNail = (ImageView) view.findViewById(R.id.ivThumbNail);
            tvProductTitle = (TextView) view.findViewById(R.id.tvProductTitle);
            tvBasePrice = (TextView) view.findViewById(R.id.tvBasePrice);
            tvSalePrice = (TextView) view.findViewById(R.id.tvSalePrice);
            tvSpnrProductLabel = (TextView) view.findViewById(R.id.tvSpnrProductLabel);

            spnrProductTagOrSize = (Spinner) view.findViewById(R.id.spnrProductTagOrSize);

            ivBookmark = (ImageView) view.findViewById(R.id.ivBookmark);
        }
    }

    /**
     * adding views into list.
     *
     * @param userDetailList passing user's details.
     */
    public void addApplications(List<ProductListModel> userDetailList) {
        this.productListModelList.addAll(userDetailList);
        this.notifyItemRangeInserted(0, userDetailList.size() - 1);
    }

    /**
     * clearing the views.
     */
    public void clearApplications() {
        int size = productListModelList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                productListModelList.remove(0);
            }
            this.notifyItemRangeRemoved(0, size);
        }
    }
}
