package com.app.demo.utilities;

import com.app.demo.model.ProductListModel;

/**
 * @author Rajeev on 11/11/16.
 *
 * button call backs goes here
 */

public interface BookmarkCallBack {
    void onBookmarkButtonClick(ProductListModel productListModel, int position);
}
