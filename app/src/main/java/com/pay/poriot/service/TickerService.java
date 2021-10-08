package com.pay.poriot.service;


import com.pay.poriot.entity.Ticker;

import io.reactivex.Observable;

public interface TickerService {
    Observable<Ticker> fetchTickerPrice(String symbols, String currency);
}
