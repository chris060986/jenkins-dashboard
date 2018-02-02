package com.chbi.ui;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileFilter;

@Service
public class GifProvider {

    private static final String STATIC_IMG_FOLDER = "static/img/";
    private static final String FAIL_STRING = "fail";
    private static final String WIN_STRING = "win";
    private static final String HTML_PATH_PREFIX = "/img/fail/";
    private static final String DEFAULT_FILENAME = "/fail01.gif";

    public String getRandomGif(boolean isFail) {
        String dirPath = STATIC_IMG_FOLDER + WIN_STRING;
        if (isFail) {
            dirPath = STATIC_IMG_FOLDER + FAIL_STRING;
        }

        ClassLoader classLoader = getClass().getClassLoader();
        File dir = new File(classLoader.getResource(dirPath).getFile());
        // Get all files in /tmp
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".gif");
            }
        });

        if (files != null && files.length > 0) {
            int random = getRandomInt(files.length);
            String path = HTML_PATH_PREFIX + files[random].getName();
            return path;
        }

        return HTML_PATH_PREFIX + DEFAULT_FILENAME;
    }

    int getRandomInt(int max) {
        return (int) (Math.random() * (max - 0.01));
    }

}
