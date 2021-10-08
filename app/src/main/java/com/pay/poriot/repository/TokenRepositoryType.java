package com.pay.poriot.repository;

import com.pay.poriot.entity.Token;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface TokenRepositoryType {
    Observable<Token[]> fetch(String walletAddress);

    Completable addToken(String walletAddress, String address, String symbol, int decimals);
}
