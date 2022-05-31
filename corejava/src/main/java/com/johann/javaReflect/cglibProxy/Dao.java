package com.johann.javaReflect.cglibProxy;

import net.sf.cglib.proxy.Callback;

public interface Dao extends Callback {
    void select();
    void update();
}
