package com.pay.poriot.repository;

import com.pay.poriot.entity.NetworkInfo;
import com.pay.poriot.entity.TokenInfo;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface TokenLocalSource {
    Completable put(NetworkInfo networkInfo, String walletAddress, TokenInfo tokenInfo);
    Single<TokenInfo[]> fetch(NetworkInfo networkInfo, String walletAddress);
}
