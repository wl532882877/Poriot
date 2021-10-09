package com.pay.poriot.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.pay.poriot.base.BaseApplication;
import com.pay.poriot.interact.AddTokenInteract;
import com.pay.poriot.interact.FetchWalletInteract;
import com.pay.poriot.repository.RepositoryFactory;

public class AddTokenViewModelFactory implements ViewModelProvider.Factory {

    private final AddTokenInteract addTokenInteract;
    private final FetchWalletInteract findDefaultWalletInteract;

    public AddTokenViewModelFactory() {
        RepositoryFactory rf = BaseApplication.repositoryFactory();
        this.findDefaultWalletInteract = new FetchWalletInteract();
        this.addTokenInteract = new AddTokenInteract(findDefaultWalletInteract, rf.tokenRepository);;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddTokenViewModel(addTokenInteract, findDefaultWalletInteract);
    }
}
