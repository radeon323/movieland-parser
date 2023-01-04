package com.olshevchenko.movielandparser.utils;

import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleksandr Shevchenko
 */
@Component
public class UrlFileReader {

    @SneakyThrows
    public List<String> read(String path) {
        List<String> rows = new ArrayList<>();
        System.out.println(path);
        URL fileUrl = new URL(path);
        BufferedReader in = new BufferedReader(new InputStreamReader(fileUrl.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            if (!inputLine.equals("")) {
                rows.add(inputLine);
            }
        }
        in.close();
        return rows;
    }
}
