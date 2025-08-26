package org.jngcoding.language.LangFiles;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import org.jngcoding.language.LangFiles.Logger.LogDispensor;

public class InstructionExecutor {
    private Tokenizer tokenizer;
    private final HashMap<String, Variable> Variables;
    private final HashMap<String, Function> Functions;

    private final Scanner scanner = new Scanner(System.in);

    public InstructionExecutor(String path) {        
        try {
            tokenizer = new Tokenizer(path);
        } catch (IOException exception) {
            tokenizer = null;
        }

        Variables = new HashMap<>();
        Functions = new HashMap<>();
    }

    private boolean CheckForVariable(String name) {
        return this.Variables.containsKey(name);
    }

    public void ExecuteFile() throws IOException {
        boolean run_flag = true;

        while (run_flag) {
            run_flag = tokenizer.NullLine();
            tokenizer.NextLine();

            String[] tokens = tokenizer.TokenizeLine();
            if (tokens != null && !tokenizer.EmptyLine()) 
            {
                for (int i = 0; i < tokens.length; i++) {
                    if (tokens[i].contains("$")) {
                        String name = tokens[i].substring(1);
                        if (CheckForVariable(name)) {
                            tokens[i] = this.Variables.get(name).data;
                        } else {
                            LogDispensor.error("Variable with name " + name + " not found.");
                        }
                    }
                }

                String instruction_type = tokens[0];
                if (instruction_type.equals("call")) {
                    String function_name = tokens[1];
                    String attribute = tokens[2];

                    if (function_name.equals("PRINTLN")) {
                        System.out.println(attribute);
                    } else if (function_name.equals("PRINT")) {
                        System.out.print(attribute);
                    } else if (function_name.equals("SYSTEM")) {
                        ProcessBuilder builder = new ProcessBuilder(attribute);
                        builder.inheritIO();
                        builder.start();
                    } else {

                    }
                } else if (instruction_type.equals("make")) {
                    String type_name = tokens[1];
                    String var_name = tokens[2];
                    this.Variables.put(var_name, new Variable(type_name.equals("number") ? Variable.DataType.NUMBER : Variable.DataType.STRING));
                } else if (instruction_type.equals("change")) {
                    String var_name = tokens[1];
                    if (CheckForVariable(var_name)) {
                        this.Variables.get(var_name).ChangeData(tokens[2]);
                    } else {
                        LogDispensor.error("Variable with name " + var_name + " not found.");
                    }
                } else if (instruction_type.equals("let")) {
                    String type_name = tokens[1];
                    String var_name = tokens[2];
                    String __data = tokens[3];
                    Variable var = new Variable(type_name.equals("number") ? Variable.DataType.NUMBER : Variable.DataType.STRING);
                    var.ChangeData(__data);
                    this.Variables.put(var_name, var);
                } else if (instruction_type.equals("store")) {
                    String var_name = tokens[1];
                    if (CheckForVariable(var_name)) {
                        String __data = scanner.next();
                        this.Variables.get(var_name).ChangeData(__data);
                    } else {
                        LogDispensor.error("Variable with name " + var_name + " not found.");
                    }
                } else if (instruction_type.equals("add")) {
                    String var_name = tokens[1];
                    if (CheckForVariable(var_name)) {
                        this.Variables.get(var_name).Add(tokens[2]);
                    } else {
                        LogDispensor.error("Variable with name " + var_name + " not found.");
                    }
                } else if (instruction_type.equals("sub")) {
                    String var_name = tokens[1];
                    if (CheckForVariable(var_name)) {
                        this.Variables.get(var_name).Subtract(tokens[2]);
                    } else {
                        LogDispensor.error("Variable with name " + var_name + " not found.");
                    }
                } else if (instruction_type.equals("mul")) {
                    String var_name = tokens[1];
                    if (CheckForVariable(var_name)) {
                        this.Variables.get(var_name).Multiply(tokens[2]);
                    } else {
                        LogDispensor.error("Variable with name " + var_name + " not found.");
                    }
                } else if (instruction_type.equals("div")) {
                    String var_name = tokens[1];
                    if (CheckForVariable(var_name)) {
                        this.Variables.get(var_name).Divide(tokens[2]);
                    } else {
                        LogDispensor.error("Variable with name " + var_name + " not found.");
                    }                    
                } else if (instruction_type.equals("comment")) {
                    // DO NOTHING
                } else if (instruction_type.equals("define")) {
                    String type_name = tokens[1];
                    String def_name = tokens[2];
                    if (type_name.equals("line_space")) {
                        this.Variables.put("line" + def_name, new Variable(Variable.DataType.NUMBER));
                        this.Variables.get("line" + def_name).ChangeData(String.valueOf(this.tokenizer.LineIndex));
                    } else if (type_name.equals("line_number")) {
                        this.Variables.put("line" + def_name, new Variable(Variable.DataType.NUMBER));
                        this.Variables.get("line" + def_name).ChangeData(tokens[3]);
                    }

                    // If we find an invalid define type
                    else {
                        LogDispensor.error("Invalid define statement.");
                    }
                } else if (instruction_type.equals("goto")) {
                    String var_name = "line" + tokens[1];
                    if (CheckForVariable(var_name)) {
                        this.tokenizer.GotoLine(Long.parseLong(this.Variables.get(var_name).data) - 1);
                    } else {
                        LogDispensor.error("Variable with name " + var_name + " not found.");                        
                    }
                } else if (instruction_type.equals("if")) {
                    String var1 = tokens[1];
                    String condition = tokens[2];  // gt, gte, lt, lte, e
                    String var2_value = tokens[3];
                    String line_number_1 = tokens[4];
                    if (condition.equals("gt")) {
                        if (CheckForVariable(var1)) {
                            if (this.Variables.get(var1).GreaterThan(var2_value)) {
                                this.tokenizer.GotoLine(Long.parseLong(line_number_1) - 1);
                            }
                        } else {
                            LogDispensor.error("Variable with name " + var1 + " not found.");
                        }
                    } else if (condition.equals("gte")) {
                        if (CheckForVariable(var1)) {
                            if (this.Variables.get(var1).GreaterThanEqualTo(var2_value)) {
                                this.tokenizer.GotoLine(Long.parseLong(line_number_1) - 1);
                            }
                        } else {
                            LogDispensor.error("Variable with name " + var1 + " not found.");
                        }
                    } else if (condition.equals("lt")) {
                        if (CheckForVariable(var1)) {
                            if (this.Variables.get(var1).LowerThan(var2_value)) {
                                this.tokenizer.GotoLine(Long.parseLong(line_number_1) - 1);
                            }
                        } else {
                            LogDispensor.error("Variable with name " + var1 + " not found.");
                        }
                    } else if (condition.equals("lte")) {
                        if (CheckForVariable(var1)) {
                            if (this.Variables.get(var1).LowerThanEqualTo(var2_value)) {
                                this.tokenizer.GotoLine(Long.parseLong(line_number_1) - 1);
                            }
                        } else {
                            LogDispensor.error("Variable with name " + var1 + " not found.");
                        }
                    } else if (condition.equals("e")) {
                        if (CheckForVariable(var1)) {
                            if (this.Variables.get(var1).EqualTo(var2_value)) {
                                this.tokenizer.GotoLine(Long.parseLong(line_number_1) - 1);
                            }
                        } else {
                            LogDispensor.error("Variable with name " + var1 + " not found.");
                        }
                    } else {
                        LogDispensor.error("Invalid if statement.");
                    }
                } else if (instruction_type.equals("end_program")) {
                    tokenizer.CloseTokenizer();
                    break;
                } else if (instruction_type.equals("inc")) {
                    String var_name = tokens[1];
                    if (CheckForVariable(var_name)) {
                        this.Variables.get(var_name).ChangeData( String.valueOf(Long.parseLong(this.Variables.get(var_name).data) + 1) );
                    } else {
                        LogDispensor.error("Variable with name " + var_name + " not found.");
                    }
                } else if (instruction_type.equals("dec")) {
                    String var_name = tokens[1];
                    if (CheckForVariable(var_name)) {
                        this.Variables.get(var_name).ChangeData( String.valueOf(Long.parseLong(this.Variables.get(var_name).data) - 1) );
                    } else {
                        LogDispensor.error("Variable with name " + var_name + " not found.");
                    }
                }
                                
                // IF WE DONT FIND ANY SPECIFIC KEYWORD
                else {
                    LogDispensor.error("Invalid Instruction " + instruction_type + " found.");
                }
            }
        }

        tokenizer.CloseTokenizer();
    }
}
