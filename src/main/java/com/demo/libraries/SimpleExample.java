package com.demo.libraries;

/**
 * Simple Example class that holds a vulnerable function
 */
public class SimpleExample {

    /**
     * Simple vulnerable function that throws an exception if the input params a special condition.
     * @param a input variable, to trigger the exception it needs to be >= 1.900.000
     * @param b input variable, to trigger the exception it needs to be >= 2.000.000 and b - a < 100.000
     * @param c input variable, to trigger the exception it needs to be "Attacker"
     */
    public void simpleExampleFunction(int a, int b, String c) {
        if (a >= 20000) {
            if (b >= 2000000) {
                if (b - a < 100000) {
                    if (c.equals("Attacker")) {
                        throw new SecurityException();
                    }
                }
            }
        }
    }
}
