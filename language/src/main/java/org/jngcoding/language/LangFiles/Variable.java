package org.jngcoding.language.LangFiles;

import org.jngcoding.language.LangFiles.Logger.LogDispensor;

public class Variable {
    public enum DataType {
        STRING,
        NUMBER
    }
    public String data;
    public final DataType type;

    public Variable(DataType __type) { type = __type; }
    public Variable(DataType __type, String __data) { type = __type; data = __data; }

    private boolean is_number(String data) {
        boolean first_dot_found = false;
        boolean first_minus_found = false;
        
        for (char c : data.toCharArray()) {
            if (c == '.' && !first_dot_found) {
                first_dot_found = true;
            } else if (c == '-' && !first_minus_found) {
                first_minus_found = true;
            } else if (c < '0' || c > '9') {
                return false;
            }
        }

        return true;
    }

    public void ChangeData(String __data) {
        if (this.type == DataType.NUMBER && !is_number(__data)) {
            LogDispensor.error("Type Error Encountered - Assigning STRING value for NUMBER variable.");
            return;
        }
        this.data = __data;
    }

    public void Add(String __data) {
        if (this.type == DataType.NUMBER && is_number(__data)) {
            Double result = Double.valueOf(data);
            result += Double.parseDouble(__data);
            this.data = String.valueOf(result);
        } else {
            this.data += __data;
        }
    }

    public void Subtract(String __data) {
        if (this.type == DataType.NUMBER && is_number(__data)) {
            Double result = Double.valueOf(data);
            result -= Double.parseDouble(__data);
            this.data = String.valueOf(result);
        }
    }

    public void Multiply(String __data) {
        if (this.type == DataType.NUMBER && is_number(__data)) {
            Double result = Double.valueOf(data);
            result *= Double.parseDouble(__data);
            this.data = String.valueOf(result);
        }
    }

    public void Divide(String __data) {
        if (this.type == DataType.NUMBER && is_number(__data)) {
            Double result = Double.valueOf(data);
            result /= Double.parseDouble(__data);
            this.data = String.valueOf(result);
        }
    }

    public boolean EqualTo(String __data) {
        if (this.type == DataType.STRING) {
            return this.data.equals(__data);
        } else if (this.type == DataType.NUMBER && is_number(__data)) {
            return Long.parseLong(this.data) == Long.parseLong(__data);
        } return false;
    }

    public boolean GreaterThan(String __data) {
        if (this.type == DataType.NUMBER && is_number(__data)) {
            return Long.parseLong(this.data) > Long.parseLong(__data);
        } return false;
    }

    public boolean GreaterThanEqualTo(String __data) {
        if (this.type == DataType.NUMBER && is_number(__data)) {
            return Long.parseLong(this.data) >= Long.parseLong(__data);
        } return false;
    }

    public boolean LowerThan(String __data) {
        if (this.type == DataType.NUMBER && is_number(__data)) {
            return Long.parseLong(this.data) < Long.parseLong(__data);
        } return false;
    }

    public boolean LowerThanEqualTo(String __data) {
        if (this.type == DataType.NUMBER && is_number(__data)) {
            return Long.parseLong(this.data) <= Long.parseLong(__data);
        } return false;
    }
}
