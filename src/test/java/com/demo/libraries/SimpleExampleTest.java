package com.demo.libraries;

import com.code_intelligence.jazzer.junit.FuzzTest;
import com.code_intelligence.jazzer.mutation.annotation.NotNull;
import org.junit.jupiter.api.Test;

public class SimpleExampleTest {
    /**
     * Fuzz test function that checks the {@link SimpleExample#simpleExampleFunction(int, int, String)} function.
     * <p/>
     * Execute test with <code>cifuzz run com.demo.libraries.SimpleExampleTest::fuzzTestCreateUser</code> or
     * <code>cifuzz container run com.demo.libraries.SimpleExampleTest::fuzzTestCreateUser</code>.
     * Finds a finding in form of an SecurityException.
     * <p/>
     * @param a parameter filled in by the fuzzer.
     * @param b parameter filled in by the fuzzer.
     * @param c parameter filled in by the fuzzer.
     */
    @FuzzTest
    public void fuzzTestSimpleExampleFunction(int a, int b, @NotNull String c) {
        SimpleExample simpleExample = new SimpleExample();
        simpleExample.simpleExampleFunction(a, b, c);
    }

    @Test
    public void unitTestDeveloper() {
        SimpleExample simpleExample = new SimpleExample();
        simpleExample.simpleExampleFunction(0, 10, "Developer");
    }

    @Test
    public void unitTestMaintainer() {
        SimpleExample simpleExample = new SimpleExample();
        simpleExample.simpleExampleFunction(20, -10, "Maintainer");
    }
}
