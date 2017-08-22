package com.chbi.ui;

import java.util.*;

public class Dictionary {
    private static Hashtable<String, String> culprits = new Hashtable<>();

    static {
        culprits.put("max.mustermann", "Max Mustermann");
    }

    public static String lookupCulprit(String culprit) {
        return (culprits.get(culprit) != null ? culprits.get(culprit) : culprit);
    }

    public static Set<String> lookupCulprits(String... culprits) {
        Set<String> newCulprits = new HashSet<>();

        for (String culprit: culprits) {
            newCulprits.add(lookupCulprit(culprit));
        }

        return newCulprits;
    }
}
