package com.lukas8219.io.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputReaderUtil {

    public static String toString(InputStream inputStream) {
        try (var reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            var sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            return "";
        }
    }

}
