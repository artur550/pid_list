package com.patterns.proxy;

import java.security.Security;
import java.util.UUID;

class ClassicInstantiable extends Object {
    String uuid ;

    public ClassicInstantiable(){}
    public String doSomething(){
        return "from instantiable "+ uuid;
    }
}

public class ProxyObject {

    private static Classic classicObject;

    private static class Classic{
        static String uuid;
        public Classic(String uu){
            this.uuid=uu;
        }

        public String doSomething(){
            return "from proxy "+ uuid;
        }
    }
    public ProxyObject(Classic proxied){
        this.classicObject =proxied;
    }

    public void printShow(){
        System.out.println(classicObject.doSomething());
    }

    public static void main (String... args){
        ProxyObject proxyObject = new ProxyObject(new Classic("some one"));
        proxyObject.printShow();
        ProxyObject nextProxyObject = new ProxyObject(new Classic("some two"));
        nextProxyObject.printShow();

        Classic c= new Classic("ten");
        System.out.println(c.doSomething());

        Classic a= new Classic("one");
        System.out.println(a.doSomething());

        Classic e= new Classic("two");
        System.out.println(e.doSomething());

        ClassicInstantiable d= new ClassicInstantiable();
        e.uuid="three";
        System.out.println(e.doSomething());
    }
}