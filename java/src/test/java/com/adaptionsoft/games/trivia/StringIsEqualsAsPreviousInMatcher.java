package com.adaptionsoft.games.trivia;

/*
 *	LegacyUtils is a set of tools for dealing with legacy code
 *
 *	Copyright (C) 2017 G Maur (gmaur.com)
 *
 *	Subject to terms and condition provided in LICENSE
 *
 *  Acknowledgments: Rachel, my first apprentice craftswoman
 */

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.io.*;
import java.nio.file.Files;

public class StringIsEqualsAsPreviousInMatcher extends TypeSafeDiagnosingMatcher<String> {

    private static final String INSTEAD_OF = " <instead of> ";
    private static final String EMPTY_LINE = "-empty line-";
    private static final String DESCRIPTION_LINE_SEPARATOR = "\n\t\t\t";
    private String fileName;

    public StringIsEqualsAsPreviousInMatcher(String fileName) {
        this.fileName = fileName;
    }

    public static StringIsEqualsAsPreviousInMatcher isEqualsAsPreviousIn(String fileName) {
        return new StringIsEqualsAsPreviousInMatcher(fileName);
    }

    @Override
    protected boolean matchesSafely(String consoleOutput, Description description) {
        try {
            File previousFile = new File(fileName);
            File directoryOfPreviousFiles = new File(previousFile.getParent());
            if (!directoryOfPreviousFiles.exists()) {
                description.appendText("Create the directory: " + previousFile.getParent());
                return false;
            }
            if (previousFile.isFile()) {
                String previousFileContent = getStringFrom(previousFile);
                boolean equals = previousFileContent.equals(consoleOutput);
                if (!equals) {
                    searchAndInformAboutDifferences(consoleOutput, previousFileContent, description);
                }
                return equals;
            }
            writeConsoleOutputInFile(consoleOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a string with the same content as " + fileName);
        description.appendText(DESCRIPTION_LINE_SEPARATOR);
        description.appendText("(one execution is required)");
    }

    private String getStringFrom(File previousFile) throws IOException {
        return new String(Files.readAllBytes(previousFile.toPath()));
    }

    private void searchAndInformAboutDifferences(String consoleOutput, String fileContent, Description description) throws IOException {
        BufferedReader consoleOutputReader = new BufferedReader(new StringReader(consoleOutput));
        BufferedReader fileContentReader = new BufferedReader(new StringReader(fileContent));
        String consoleOutputLine, fileContentLine;
        while ((consoleOutputLine = consoleOutputReader.readLine()) != null) {
            fileContentLine = fileContentReader.readLine();
            if (!consoleOutputLine.equals(fileContentLine)) {
                informAboutDifferentLines(consoleOutputLine, fileContentLine, description);
            }
        }
        while ((fileContentLine = fileContentReader.readLine()) != null) {
            informAboutDifferentLines(EMPTY_LINE, fileContentLine, description);
        }
    }

    private void informAboutDifferentLines(String actual, String expected, Description description) {
        description.appendValue(actual);
        description.appendText(INSTEAD_OF);
        description.appendValue(expected);
        description.appendText(DESCRIPTION_LINE_SEPARATOR);
    }

    private void writeConsoleOutputInFile(String consoleOutput) throws IOException {
        try (FileWriter fw = new FileWriter(fileName, false);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(consoleOutput);
        }
    }
}