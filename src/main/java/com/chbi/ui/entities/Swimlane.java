package com.chbi.ui.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Class is representing a swimlane on the view.
 */
public class Swimlane {

    private String headline;
    private Collection<BuildBox> buildBoxes = new ArrayList<>();

    public Swimlane withHeadline(String headline) {
        this.headline = headline;
        return this;
    }

    public Swimlane withBuildBoxes(BuildBox... boxes) {
        this.buildBoxes.addAll(Arrays.asList(boxes));
        return this;
    }

    public String getHeadline() {
        return headline;
    }

    public boolean hasBuildBoxes() {
        return !getBuildBoxes().isEmpty();
    }

    public Collection<BuildBox> getBuildBoxes() {
        return buildBoxes;
    }
}
