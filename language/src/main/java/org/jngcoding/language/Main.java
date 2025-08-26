package org.jngcoding.language;

import org.jngcoding.language.LangFiles.InstructionExecutor;

public class Main {
    private static final String ROOT_DIR = System.getProperty("user.dir");

    public static void main(String[] args) throws Exception {
        InstructionExecutor executor = new InstructionExecutor(ROOT_DIR + "\\test4.acd");
        executor.ExecuteFile();
    }
}