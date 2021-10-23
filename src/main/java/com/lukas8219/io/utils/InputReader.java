package com.lukas8219.io.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class InputReader {

    public static byte[] toByte(InputStream inputStream){
        try(var reader = new BufferedReader(
                new InputStreamReader(inputStream))) {
            String line;
            var sb = new StringBuilder();
            while( (line = reader.readLine()) != null){
                sb.append(line).append("\n");
            }
            return sb.toString().getBytes(StandardCharsets.UTF_8);
        } catch (IOException e){
            System.out.println("An exception occurred");
            return new byte[0];
        }
    }

    public static String toString(InputStream stream){
        return new String(toByte(stream));
    }

}
