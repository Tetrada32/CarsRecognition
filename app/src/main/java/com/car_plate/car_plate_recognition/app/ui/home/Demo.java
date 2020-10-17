package com.car_plate.car_plate_recognition.app.ui.home;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Demo {

    private void meow() throws IOException {
        URL url = null;
        try {
            url = new URL("http://url for your webpage");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection yc = url != null ? url.openConnection() : null;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        yc != null ? yc.getInputStream() : null));
        String inputLine;
        StringBuilder builder = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            builder.append(inputLine.trim());
        in.close();
        String htmlPage = builder.toString();

        String yourNumber = htmlPage.replaceAll("\\<.*?>", "");
    }
}
