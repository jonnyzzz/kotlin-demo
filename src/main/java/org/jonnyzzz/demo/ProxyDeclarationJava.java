package org.jonnyzzz.demo;

import java.lang.reflect.Method;

@SuppressWarnings({"unused", "UnnecessaryInterfaceModifier", "unused", "ConstantConditions", "UnusedReturnValue"})
public interface ProxyDeclarationJava {


  /**
   * Returns an instance of a proxy class for
   * the specified interfaces that dispatches
   * method invocations to the specified
   * invocation handler...
   */
  Object newProxyInstance(ClassLoader loader,
                          Class<?>[] interfaces,
                          InvocationHandler h)
          throws IllegalArgumentException;




  interface InvocationHandler {
    /**
     * Processes a method invocation on a proxy instance and returns
     * the result.  This method will be invoked on an invocation handler
     * when a method is invoked on a proxy instance that it is
     * associated with...
     */
    Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
  }


  public static void main(String[] args) {
    ProxyDeclarationJava p = null;
    p.newProxyInstance(null, null, null);
  }
}

/**
 * ...the method invocation is encoded and dispatched
 * to the {@code invoke} method of its invocation handler...
 */