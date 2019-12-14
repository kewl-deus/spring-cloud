package com.example.customerservice.event.sourcing;

import com.example.customerservice.event.DomainEvent;
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
        subscriptions = new LinkedList<>();
    }

    public Observable<DomainEvent> createObservable() {
        return Observable.create(eventSource);
    }

    public void send(DomainEvent event) {
        eventSource.send(event);
    }

    public <T extends DomainEvent> void register(Class<T> eventType, Consumer<T> consumer) {
        Disposable subscription = observable.ofType(eventType).subscribe(consumer::accept);
        if (subscription != null) {
            subscriptions.add(subscription);
        }
    }

    //TODO public void unregister()
}
