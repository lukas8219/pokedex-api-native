package com.lukas8219.io.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;
import java.util.function.Function;

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

    public static void readAndApplyToEachLine(InputStream inputStream, Consumer<String> consumer) {
        try (var reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                consumer.accept(line);
            }
        } catch (IOException e) {
            return;
        }
    }

    public static <T> T readAndApplyToEntireFile(InputStream stream, Function<String, T> consumer){
        var entireFile = toString(stream);
        return consumer.apply(entireFile);
    }

}
