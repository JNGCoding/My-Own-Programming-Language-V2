package org.jngcoding.language;

import org.jngcoding.language.LangFiles.InstructionExecutor;
import org.jngcoding.language.LangFiles.Logger.LogDispensor;

public class Main {
    private static final String ROOT_DIR = System.getProperty("user.dir");

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            LogDispensor.error("Invalid Argument size. Please fill all arguments.");
            return;
        }

        switch (args[0]) {
            case "-r":
                {
                    InstructionExecutor executor = new InstructionExecutor(ROOT_DIR + "\\" + args[1]);
                    executor.ExecuteFile();
                    break;
                }
            case "-a":
                {
                    InstructionExecutor executor = new InstructionExecutor(args[1]);
                    executor.ExecuteFile();
                    break;
                }
            default:
                LogDispensor.error("Invalid flag. Flag can be either -r or -a");
                break;
        }
    }
}