package com.comp3350.recip_e.tests.utils;

import com.comp3350.recip_e.application.Main;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

public class TestUtils {
    private static final File DB_SRC = new File("src/main/asset/SC.script");

    public static File copyDB() throws IOException {
        final File target = File.createTempFile("temp-db", ".script");
        Files.copy(DB_SRC, target);
        Main.setDBPathName(target.getAbsolutePath().replace(".script", ""));
        return target;
    }
}
