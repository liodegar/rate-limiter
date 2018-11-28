package com.elinvar.exercise;

/**
 * Scenario: There is a producer (Producer) of price updates for stocks.
 * This producer will generate constant price updates for a fix number of stocks.
 * The producer should not block, every price update should be consumed as quickly as possible.
 * <p>
 * Furthermore there is a load handler (LoadHandler) which will consume the price updates of the producer.
 * In the current implementation the load handler will just pass on the update to a consumer. This should be changed.
 * <p>
 * The consumer (Consumer) will receive the price updates from the load handler.
 * (The current implementation will just print out all price updates for convenience sake.)
 * The consumer should represent a legacy system that cannot consumer more than a certain number of price updates per second.
 * Otherwise it will fall over.
 * <p>
 * The task of this exercise is to extend the LoadHandler to limit the updates per second to the consumer to a certain given number (MAX_PRICE_UPDATES).
 * In order to achieve this, it is a allowed to drop price updates, since otherwise the application will run out of memory, if the application will keep all of them.
 * It is important that, if a price update will be send to the consumer, it has to be the most recent price.
 * <p>
 * Example:
 * <p>
 * Updates arrive in this order from the consumer:
 * <p>
 * Apple - 97.85
 * Google - 160.71
 * Facebook - 91.66
 * Google - 160.73
 * <p>
 * The load balancer has received all updates and is going to send them out to the consumer like this now:
 * <p>
 * Apple - 97.85
 * Google - 160.73
 * Facebook - 91.66
 * <p>
 * So the first Google update (160.71) has been dropped.
 * <p>
 * In order to limit the number of updates per second to the consumer,
 * it will be necessary to write some sort of scheduler/timer.
 * It is acceptable to send the updates as bulk once per second.
 * Ideally the load should be spread out into smaller chunks during that second.
 * <p>
 * Please consider that the number of stocks might be bigger than the number of allowed updates per second to the consumer.
 * Make sure that the application will not run out of memory, even if the number of stocks or updates per second might be bigger than MAX_PRICE_UPDATES.
 * <p>
 * Please implement the <b>hashCode</b> and <b>equals</b> in PriceUpdate,
 * since those methods might be relevant for the task.
 * <p>
 * It is fine to create additional classes and tests.
 * <p>
 * You can use all features of Java 8 as well as any additional library as long as it is open source and will be provided with the solution.
 */
public class Exercise {

    public static void main(String[] args) {
        Consumer consumer = new Consumer();
        LoadHandler loadHandler = new LoadHandler(consumer);
        Producer producer = new Producer(loadHandler);
        producer.run();
    }

}
