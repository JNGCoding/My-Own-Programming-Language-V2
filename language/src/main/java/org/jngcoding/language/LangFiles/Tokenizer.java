package org.jngcoding.language.LangFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    RandomAccessFile CodeFile;
    long LineIndex;
    long CharIndex;
    long TotalCharacters;
    String CurrentLine;

    public Tokenizer(String path) throws FileNotFoundException, IOException {
        CodeFile = new RandomAccessFile(new File(path), "r");
        LineIndex = 0;
        CharIndex = 0;
        TotalCharacters = 0;
        CurrentLine = CodeFile.readLine();
        Initialize();
    }

    private void Initialize() throws IOException {
        while (true) {
            String line = CodeFile.readLine();
            if (line == null) {
                break;
            }
            this.TotalCharacters += line.length();
        }

        this.CodeFile.seek(0);
    }

    public void GotoLine(long _line_index_) throws IOException {
        this.LineIndex = 0;
        this.CharIndex = 0;
        this.CodeFile.seek(0);

        String line;
        long tempCharIndex = 0;

        for (long i = 0; i <= _line_index_; i++) {
            line = CodeFile.readLine();
            if (line == null) {
                this.CurrentLine = null;
                return;
            }

            if (i == _line_index_) {
                this.CurrentLine = line;
                this.LineIndex = i;
                this.CharIndex = tempCharIndex;
                return;
            }

            tempCharIndex += line.length() + 1;
        }
    }

    public void NextLine() throws IOException {
        this.CurrentLine = CodeFile.readLine();
        if (this.CurrentLine != null) {
            this.LineIndex++;
            this.CharIndex++;
        }
    }

    public String[] TokenizeLine() {
        if (this.CurrentLine == null || this.CurrentLine.trim().isEmpty()) return new String[0];

        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < CurrentLine.length(); i++) {
            char c = CurrentLine.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
                if (!inQuotes) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                }
            } else if (Character.isWhitespace(c) && !inQuotes) {
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                }
            } else {
                currentToken.append(c);
            }
        }

        // Add any remaining token
        if (currentToken.length() > 0) {
            tokens.add(currentToken.toString());
        }

        return tokens.toArray(new String[0]);
    }

    public boolean PrintLine() {
        if (this.CurrentLine == null) {
            return false;
        } else {
            System.out.println(this.CurrentLine); 
            return true;
        }
    }

    public boolean EmptyLine() {
        return this.CurrentLine == null || this.CurrentLine.length() == 0;
    }

    public boolean NullLine() {
        return this.CurrentLine != null;
    }

    public void CloseTokenizer() {
        try {
            this.CodeFile.close(); 
        } catch (IOException exception) {}
    }
}
