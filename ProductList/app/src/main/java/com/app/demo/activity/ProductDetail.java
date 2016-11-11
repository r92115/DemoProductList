package com.app.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.demo.R;
import com.app.demo.model.ProductListModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import static com.app.demo.utilities.Constant.PRODUCT_LIST_MODEL_INTENT;

/**
 * @author Rajeev
 *         <p>
 *         This class will show the product details like product title, image, description & price.
 */

public class ProductDetail extends AppCompatActivity {

    // declaring views items
    private TextView tvProductDetailTitle;

    private ImageView ivProductDetailImage;

    private TextView tvProductDetailSalePrice;
    private TextView tvProductDetailDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // adding back button on tool bar
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            initViews();
        }
    }

    /**
     * initializing views
     */
    private void initViews() {
        tvProductDetailTitle = (TextView) findViewById(R.id.tvProductDetailTitle);

        ivProductDetailImage = (ImageView) findViewById(R.id.ivProductDetailImage);

        tvProductDetailSalePrice = (TextView) findViewById(R.id.tvProductDetailSalePrice);
        tvProductDetailDescription = (TextView) findViewById(R.id.tvProductDetailDescription);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setViewsValues();
    }

    /**
     * this method will get the value from intent and set into the views.
     */
    private void setViewsValues() {
        Intent intent = getIntent();

        ProductListModel productListModel = (ProductListModel) intent.getSerializableExtra(PRODUCT_LIST_MODEL_INTENT);

        tvProductDetailTitle.setText(productListModel.getTitle());

        Glide.with(this)
                .load(productListModel.getLogoThumb())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivProductDetailImage);

        tvProductDetailSalePrice.setText(productListModel.getSalePrice());
        tvProductDetailDescription.setText(productListModel.getDescription());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = new Intent(this, Home.class);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.create(this).addNextIntent(upIntent)
                            .startActivities();
                    finish();
                } else {
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
