package com.pay.poriot.viewmodel;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.pay.poriot.base.BaseApplication;
import com.pay.poriot.interact.FetchTokensInteract;
import com.pay.poriot.interact.FetchWalletInteract;
import com.pay.poriot.repository.EthereumNetworkRepository;
import com.pay.poriot.repository.RepositoryFactory;


public class TokensViewModelFactory implements ViewModelProvider.Factory {
    private final FetchTokensInteract fetchTokensInteract;
    private final EthereumNetworkRepository ethereumNetworkRepository;

    public TokensViewModelFactory() {
        RepositoryFactory rf = BaseApplication.repositoryFactory();
        fetchTokensInteract = new FetchTokensInteract(rf.tokenRepository);
        ethereumNetworkRepository = rf.ethereumNetworkRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TokensViewModel(
                ethereumNetworkRepository,
                new FetchWalletInteract(),
                fetchTokensInteract
                );
    }
}
