package com.example.triples.DataContainers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProductItemViewModel extends ViewModel {
    private final MutableLiveData<ProductsItem> item = new MutableLiveData<ProductsItem>();

    public void setItem(ProductsItem productItem){
        item.setValue(productItem);
    }

    public LiveData<ProductsItem> getItem(){
        return item;
    }
}
