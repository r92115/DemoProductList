package com.app.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.app.demo.R;
import com.app.demo.adapter.TabsPagerAdapter;
import com.app.demo.fragment.ProductListFragment;
import com.app.demo.model.ProductListModel;

import static com.app.demo.utilities.Constant.PRODUCT_LIST_MODEL_INTENT;
import static com.app.demo.utilities.Constant.PRODUCT_LIST_POSITION;

/**
 * @author Rajeev on 09/11/16
 *         <p>
 *         This class will add tab layout, product list item click.
 */

public class Home extends AppCompatActivity implements ProductListFragment.OnProductListFragmentInteractionListener {

    // tab title
    private CharSequence[] actionBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTabLayout();
    }

    /**
     * this method will initialize the tab layout views.
     */
    private void setTabLayout() {
        // Getting tab name from property file.
        actionBarTitle = getResources().getStringArray(R.array.action_bar_tab_array);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * setting up the view pager on home screen.
     *
     * @param viewPager passing view pager
     */
    private void setupViewPager(ViewPager viewPager) {
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        int numOfTabs = 1;

        TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager(),
                actionBarTitle, numOfTabs);
        viewPager.setAdapter(tabsPagerAdapter);
    }

    @Override
    public void onProductListFragmentInteraction(ProductListModel productListModel, int position) {

        // starting product detail activity
        Intent intentProductDetail = new Intent(this, ProductDetail.class);

        intentProductDetail.putExtra(PRODUCT_LIST_MODEL_INTENT, productListModel);
        intentProductDetail.putExtra(PRODUCT_LIST_POSITION, position);

        startActivity(intentProductDetail);
    }
}
