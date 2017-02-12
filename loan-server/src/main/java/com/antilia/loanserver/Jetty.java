package com.antilia.loanserver;

/**
 * Jetty
 */
public class Jetty {
    public static boolean isJetty() {
        return System.getProperty("jetty") != null;
    }
}
