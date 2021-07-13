package com.example.triples.ApisConnectionClasses;

public interface AbstractConnectionHandler<Type> {
    void onSuccessConnection(Type data);
    void onFailedConnection();
    void onFailureConnection();
}
