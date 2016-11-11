package com.app.demo.model;

import java.io.Serializable;

/**
 * @author Rajeev on 9/11/16.
 */

public class ProductListModel implements Serializable{

    // product id
    private String id;
    // product title
    private String title;
    // product description
    private String description;
    // product base price
    private String basePrice;
    // product sale price
    private String salePrice;
    // thumbnail of product
    private String logoThumb;
    // product type
    private String productType;
    // size or model
    private String sizeOrModel;
    // is bookmarked
    private boolean isBookmarked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getLogoThumb() {
        return logoThumb;
    }

    public void setLogoThumb(String logoThumb) {
        this.logoThumb = logoThumb;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getSizeOrModel() {
        return sizeOrModel;
    }

    public void setSizeOrModel(String sizeOrModel) {
        this.sizeOrModel = sizeOrModel;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }
}
