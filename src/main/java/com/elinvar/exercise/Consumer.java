package com.elinvar.exercise;

import java.util.List;

/**
 * Please do not change the Consumer.
 */
public class Consumer {

    public void send(List<PriceUpdate> priceUpdates) {
        priceUpdates.forEach(System.out::println);
    }

}
