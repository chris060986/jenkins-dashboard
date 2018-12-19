package com.chbi.ui;

import com.chbi.ApplicationConfiguration;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;

@Service
public class GifProvider {

    private static final String STATIC_IMG_FOLDER = "static/img/";
    private static final String FAIL_STRING = "fail";
    private static final String WIN_STRING = "win";
    private final ApplicationConfiguration applicationConfig;

    @Autowired
    public GifProvider(ApplicationConfiguration config) {
        this.applicationConfig = config;
    }

    public File getRandomGifAsFile(boolean isFail) throws IOException {
        String dirPath = applicationConfig.getGifPath()+WIN_STRING;
        if (isFail) {
            dirPath = STATIC_IMG_FOLDER + FAIL_STRING;
        }

        File dir = new File(dirPath);
        if(dir.exists() && dir.isDirectory()) {
            List<File> files = Lists.newArrayList(dir.listFiles(file -> file.getName().endsWith(".gif")));
            if (files.size() > 0) {
                return files.get(getRandomInt(files.size() - 1));
            }
        }
        throw new IOException("No image could be read");
    }

    int getRandomInt(int max) {
        return (int) (Math.random() * (max - 0.01));
    }

}
