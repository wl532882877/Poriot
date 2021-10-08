package com.pay.poriot.viewmodel;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.gson.Gson;
import com.pay.poriot.base.BaseApplication;
import com.pay.poriot.dao.ETHWallet;
import com.pay.poriot.entity.NetworkInfo;
import com.pay.poriot.entity.Ticker;
import com.pay.poriot.entity.Token;
import com.pay.poriot.interact.FetchTokensInteract;
import com.pay.poriot.interact.FetchWalletInteract;
import com.pay.poriot.repository.EthereumNetworkRepository;
import com.pay.poriot.service.TickerService;
import com.pay.poriot.service.UpWalletTickerService;
import com.pay.poriot.util.WalletDaoUtils;
import io.reactivex.Single;


public class TokensViewModel extends BaseViewModel {
    private final MutableLiveData<NetworkInfo> defaultNetwork = new MutableLiveData<>();
    private final MutableLiveData<ETHWallet> defaultWallet = new MutableLiveData<>();
    private final MutableLiveData<Ticker> prices = new MutableLiveData<>();
    private final MutableLiveData<Token[]> tokens = new MutableLiveData<>();
    private final EthereumNetworkRepository ethereumNetworkRepository;
    private final FetchWalletInteract findDefaultWalletInteract;
    private final FetchTokensInteract fetchTokensInteract;
    private final TickerService tickerService;

    TokensViewModel(
            EthereumNetworkRepository ethereumNetworkRepository,
            FetchWalletInteract findDefaultWalletInteract,
            FetchTokensInteract fetchTokensInteract) {
        this.findDefaultWalletInteract = findDefaultWalletInteract;
        this.ethereumNetworkRepository  = ethereumNetworkRepository;
        this.fetchTokensInteract = fetchTokensInteract;
        tickerService = new UpWalletTickerService(BaseApplication.okHttpClient(), new Gson());
    }

    public void prepare() {
        progress.postValue(true);
        defaultNetwork.postValue(ethereumNetworkRepository.getDefaultNetwork());
        disposable = findDefaultWalletInteract.findDefault()
                .subscribe(this::onDefaultWallet, this::onError);
    }

    public void updateDefaultWallet(final long walletId) {
        Single.fromCallable(() -> {
            return WalletDaoUtils.updateCurrent(walletId);
        }).subscribe(this::onDefaultWallet);
    }

    private void onDefaultWallet(ETHWallet wallet) {
        defaultWallet.setValue(wallet);
        fetchTokens();
    }

    public LiveData<NetworkInfo> defaultNetwork() {
        return defaultNetwork;
    }

    public LiveData<ETHWallet> defaultWallet() {
        return defaultWallet;
    }

    public LiveData<Token[]> tokens() {
        return tokens;
    }

    public MutableLiveData<Ticker> prices() {
        return prices;
    }

    public void fetchTokens() {
        progress.postValue(true);
        disposable = fetchTokensInteract
                .fetch(defaultWallet.getValue().address)
                .subscribe(this::onTokens, this::onError);
    }

    private void onTokens(Token[] tokens) {
        progress.postValue(false);
        this.tokens.postValue(tokens);
        for (Token token : tokens ) {
            if (token.balance!=null && !token.balance.equals("0")) {
                getTicker(token.tokenInfo.symbol).subscribe(this::onPrice, this::onError);
            }
        }
    }

    public Single<Ticker> getTicker(String symbol) {
        return Single.fromObservable(tickerService
                .fetchTickerPrice(symbol, getCurrency()));   // getDefaultNetwork().symbol
    }

    public  String getCurrency() {
        return ethereumNetworkRepository.getCurrency();
    }

    private  void onPrice(Ticker ticker) {
        Log.e("wl", "price: " + ticker.symbol + "  " + ticker.price);
        this.prices.postValue(ticker);
    }
}


