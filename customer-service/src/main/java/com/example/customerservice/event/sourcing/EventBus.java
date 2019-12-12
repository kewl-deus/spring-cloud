package com.example.customerservice.event.sourcing;

import com.example.customerservice.event.DomainEvent;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import java.util.LinkedList;
import java.util.function.Consumer;

public class EventBus {

    private EventSource eventSource;
    private Observable<DomainEvent> observable;
    private LinkedList<Disposable> subscriptions;

    public EventBus() {
        eventSource = new EventSource();
        observable = Observable.create(eventSource);
        LinkedList<Disposable> subscriptions = new LinkedList<>();
    }

    public Observable<DomainEvent> createObservable() {
        return Observable.create(eventSource);
    }

    public void send(DomainEvent event) {
        eventSource.send(event);
    }

    public <T extends DomainEvent> void register(Class<T> eventType, Consumer<T> consumer) {
        Disposable subscription = observable.subscribe(event -> {
            if (event != null && eventType.isAssignableFrom(event.getClass())) {
                //we are sure event is of type T, so we can cast
                consumer.accept((T) event);
            }
        });
        subscriptions.add(subscription);
    }

    //public void unregister()
}
