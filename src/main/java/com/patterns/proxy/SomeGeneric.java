package com.patterns.proxy;

public class SomeGeneric <T> {
    private T type;
    public SomeGeneric(T type){
        this.type=type;
    }
    public T getT(){
        return type;
    }
}
