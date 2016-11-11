package com.app.demo.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.app.demo.R;
import com.app.demo.adapter.ProductListAdapter;
import com.app.demo.model.ProductListModel;
import com.app.demo.utilities.BookmarkCallBack;
import com.app.demo.utilities.DialogCallBack;
import com.app.demo.utilities.VolleyHelper;
import com.app.demo.utilities.VolleyResponseCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.app.demo.utilities.Constant.CLOTH_URL;
import static com.app.demo.utilities.Constant.FOOD_URL;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductListFragment.OnProductListFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ProductListFragment extends Fragment implements BookmarkCallBack{

    // on product list item click listener
    private OnProductListFragmentInteractionListener onProductListFragmentInteractionListener;

    // adapter where all items will be displayed.
    private ProductListAdapter productListAdapter;

    // list of items.
    private List<ProductListModel> productListModelList;

    // recycler view for displaying list of card view
    private RecyclerView recyclerView;

    // text for showing loading.
    private TextView tvLoading;

    /**
     * default constructor
     */
    public ProductListFragment() {
        // Required empty public constructor
    }

    /**
     * initializing product list fragment.
     *
     * @return product list fragment instance.
     */
    public static ProductListFragment newInstance() {
        return new ProductListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProductListFragmentInteractionListener) {
            onProductListFragmentInteractionListener = (OnProductListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productListModelList = new ArrayList<>();
        productListAdapter = new ProductListAdapter(
                new ArrayList<ProductListModel>(),
                this,
                onProductListFragmentInteractionListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        tvLoading = (TextView) view.findViewById(R.id.tvLoading);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setAdapter(productListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        // load cloths from server.
        loadClothsListFromServer();
        // load foods from server.
        loadFoodsListFromServer();
    }

    /**
     * get all cloths list from the server.
     */
    private void loadClothsListFromServer() {
        new VolleyHelper().doJsonObjectRequest(getActivity(),
                Request.Method.GET,
                CLOTH_URL,
                new VolleyResponseCallBack() {

                    @Override
                    public void onResponse(JSONObject response) {
                        super.onResponse(response);
                        parsingJson(response, "Cloths");
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        Toast.makeText(getActivity(),
                                volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, false, new DialogCallBack(){
                    @Override
                    protected void onNeutralBtnClick() {
                        super.onNeutralBtnClick();
                    }
                });
    }

    /**
     * get all cloths list from the server.
     */
    private void loadFoodsListFromServer() {
        new VolleyHelper().doJsonObjectRequest(getActivity(),
                Request.Method.GET,
                FOOD_URL,
                new VolleyResponseCallBack() {

                    @Override
                    public void onResponse(JSONObject response) {
                        super.onResponse(response);
                        parsingJson(response, "Foods");
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        Toast.makeText(getActivity(),
                                volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, false, new DialogCallBack(){
                    @Override
                    protected void onNeutralBtnClick() {
                        super.onNeutralBtnClick();
                    }
                });
    }

    /**
     * this method will parse the json
     * @param response passing json response.
     * @param productType passing string to check the product tyep
     */
    private void parsingJson(JSONObject response, String productType) {
        try {
            tvLoading.setVisibility(TextView.GONE);
            JSONArray productJsonArray = response.getJSONArray("products");
            for (int i=0; i<productJsonArray.length(); i++) {

                JSONObject productJsonObject = productJsonArray.getJSONObject(i);
                ProductListModel productListModel = new ProductListModel();

                productListModel.setLogoThumb(productJsonObject.getString("logo_thumb"));
                productListModel.setTitle(productJsonObject.getString("title"));
                productListModel.setBasePrice(productJsonObject.getString("base_price"));
                productListModel.setSalePrice(productJsonObject.getString("sale_price"));

                // adding product type
                if (productType.equalsIgnoreCase("Clothes")) {
                    productListModel.setProductType("Cloths");
                    productListModel.setSizeOrModel(productJsonObject.getString("size_list"));
                } else {
                    productListModel.setProductType("Foods");
                    productListModel.setSizeOrModel(productJsonObject.getString("tags"));
                }

                productListModel.setBookmarked(false);
                productListModel.setDescription(productJsonObject.getString("description"));

                productListModelList.add(productListModel);
            }

            productListAdapter.addApplications(productListModelList);
        } catch (JSONException e) {
            Log.getStackTraceString(e);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onProductListFragmentInteractionListener = null;
    }

    @Override
    public void onBookmarkButtonClick(ProductListModel productListModel, int position) {
        bookmarkConfirmation(productListModel, position);
    }

    /**
     * showing alert for bookmark
     * @param productListModel passing product model
     * @param position passing position
     */
    private void bookmarkConfirmation(final ProductListModel productListModel, final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        if (productListModel.isBookmarked()) {
            alertDialogBuilder.setMessage(getString(R.string.txt_hint_remove_bookmark)
                    + " "+productListModel.getTitle() +" from bookmark list? ");
        } else {
            alertDialogBuilder.setMessage(getString(R.string.txt_hint_add_bookmark)
                    + " "+productListModel.getTitle() +" to bookmark list? ");
        }


        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(R.string.txt_hint_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                if (productListModel.isBookmarked()) {
                    productListModelList.get(position).setBookmarked(false);
                } else {
                    productListModelList.get(position).setBookmarked(true);
                }

                refreshProductList();
            }
        });

        alertDialogBuilder.setNegativeButton(R.string.txt_hint_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * refreshing product list
     */
    private void refreshProductList() {
        try {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (productListAdapter != null) {
                        productListAdapter.notifyDataSetChanged();
                    }
                }
            });
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnProductListFragmentInteractionListener {
        void onProductListFragmentInteraction(ProductListModel productListModel, int position);
    }
}
