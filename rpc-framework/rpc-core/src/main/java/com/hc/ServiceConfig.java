package com.hc;

/**
 * @author 小盒
 * @verson 1.0
 */
public class ServiceConfig<T> {
    private Class<T> interfaceProvider;
    private Object ref;

    public void setInterface(Class<T> interfaceProvider) {
        this.interfaceProvider = interfaceProvider;
    }

    public Class<T> getInterface() {
        return interfaceProvider;
    }

    public void setRef(Object ref) {
        this.ref = ref;
    }

    public Object getRef() {
        return ref;
    }
}
