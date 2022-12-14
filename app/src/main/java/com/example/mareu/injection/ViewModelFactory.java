package com.example.mareu.injection;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mareu.viewModel.ItemViewModel;
import com.example.mareu.repository.ItemDataRepository;

import java.util.concurrent.Executor;


public class ViewModelFactory implements ViewModelProvider.Factory {

    private final ItemDataRepository itemDataSource;

    private final Executor executor;

    public ViewModelFactory(ItemDataRepository itemDataSource, Executor executor) {
        this.itemDataSource = itemDataSource;

        this.executor = executor;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ItemViewModel.class)) {
            return (T) new ItemViewModel(itemDataSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}