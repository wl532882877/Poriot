package com.pay.poriot.repository;

import com.pay.poriot.entity.Transaction;

import io.reactivex.Maybe;
import io.reactivex.Observable;

public interface TransactionRepositoryType {
    Observable<Transaction[]> fetchTransaction(String walletAddr, String token);
    Maybe<Transaction> findTransaction(String walletAddr, String transactionHash);
}
