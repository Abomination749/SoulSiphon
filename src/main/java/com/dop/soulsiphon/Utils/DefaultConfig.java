package com.dop.soulsiphon.Utils;

import com.dop.soulsiphon.Main;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class DefaultConfig {

    private static Main main;

    public DefaultConfig(Main main) {

        this.main = main;

    }

    public void copyDefaultConfig() {
        try {
            // Open a reader to the default config.yml resource
            Reader defaultConfigReader = new InputStreamReader(main.getResource("config.yml"), StandardCharsets.UTF_8);

            // Create a writer for the new configuration.yml file
            FileWriter configWriter = new FileWriter(new File(main.getDataFolder(), "configuration.yml"));

            // Read from the default config and write to the new config
            char[] buffer = new char[1024];
            int bytesRead;
            while ((bytesRead = defaultConfigReader.read(buffer)) != -1) {
                configWriter.write(buffer, 0, bytesRead);
            }

            // Close the reader and writer
            defaultConfigReader.close();
            configWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
