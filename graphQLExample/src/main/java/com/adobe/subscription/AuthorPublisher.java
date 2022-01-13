package com.adobe.subscription;

import org.springframework.stereotype.Component;

import com.adobe.entity.Author;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.observables.ConnectableObservable;

@Component
public class AuthorPublisher {

    private final Flowable<Author> publisher;

    private ObservableEmitter<Author> emitter;
    
    public AuthorPublisher() {
        Observable<Author> authorObservable = Observable.create(emitter -> {
        	System.out.println("Emitter ==> " + emitter);
            this.emitter = emitter;
        });

//        ConnectableObservable<Author> connectableObservable = authorObservable.share().publish();
//        connectableObservable.connect();


//        publisher = connectableObservable.toFlowable(BackpressureStrategy.BUFFER);
        publisher = authorObservable.toFlowable(BackpressureStrategy.BUFFER);
    }

    public void publish(final Author author) {
        emitter.onNext(author);
    }


    public Flowable<Author> getPublisher() {
        return publisher;
    }
}
