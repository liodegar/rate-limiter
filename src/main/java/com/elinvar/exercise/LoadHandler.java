package com.elinvar.exercise;

import com.google.common.collect.MinMaxPriorityQueue;
import com.google.common.util.concurrent.RateLimiter;

import java.util.*;

public class LoadHandler {

    private static final int MAX_PRICE_UPDATES = 100;
    //Defines how many PriceUpdate instances will be sent
    public static final int CHUNK_SIZE = 9;
    private final Consumer consumer;

    //Guava Rate limiting to handle MAX_PRICE_UPDATES per second
    private RateLimiter limiter = RateLimiter.create(MAX_PRICE_UPDATES);

    //Queue to store the received PriceUpdate instances
    private MinMaxPriorityQueue<PriceUpdate> queue = MinMaxPriorityQueue
            .orderedBy(Comparator.comparing(PriceUpdate::getCompanyName))
            .maximumSize(MAX_PRICE_UPDATES)
            .create();

    public LoadHandler(Consumer consumer) {
        this.consumer = consumer;
    }

    public void receive(PriceUpdate priceUpdate) {
        limiter.acquire();
        queue.add(priceUpdate);

        if (queue.size() == CHUNK_SIZE) {

            Set<PriceUpdate> chunk = new HashSet<>();
            List<PriceUpdate> listFromQueue = new ArrayList<>(queue);
            //Iterates from the latest to oldest
            for (int i = listFromQueue.size(); i-- > 0; ) {
                //I add only the latest info per company. equals and hashCode were overridden accordingly
                chunk.add(listFromQueue.get(i));
            }

            consumer.send(new ArrayList<>(chunk));
            queue.clear();
        }
    }

}
