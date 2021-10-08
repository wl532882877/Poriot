package com.pay.poriot.service;

import com.pay.poriot.entity.Transaction;

import io.reactivex.Observable;

public interface BlockExplorerClientType {
    Observable<Transaction[]> fetchTransactions(String forAddress, String forToken);
}
