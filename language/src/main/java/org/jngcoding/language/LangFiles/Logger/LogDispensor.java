package org.jngcoding.language.LangFiles.Logger;

public class LogDispensor {
    public static final String RESET = "\u001B[0m";  // Resets the color
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\u001B[33m";
    public static final String ORANGE = "\u001B[38;5;214m";

    public static void log(String message, String color) {
        System.out.println(color + message + RESET);
    }

    public static void error(String message) {
        System.out.println(RED + "ERROR : " + message + RESET);
    }

    public static void pass(String message) {
        System.out.println(GREEN + "PASS : " + message + RESET);
    }

    public static void warning(String message) {
        System.out.println(YELLOW + "WARNING : " + message + RESET);
    }

    public static void message(String message) {
        System.out.println(ORANGE + "MESSAGE : " + message + RESET);
    }

    public static void event(String message) {
        System.out.println(BLUE + "EVENT : " + message + RESET);
    }
}
