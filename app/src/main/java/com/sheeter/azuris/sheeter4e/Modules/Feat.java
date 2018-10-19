package com.sheeter.azuris.sheeter4e.Modules;

/**
 * Created by Eric on 6/5/2017.
 */

public class Feat {
    private String name;
    private String description;
    private Feat optional;

    public Feat() { }

    public Feat(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Feat(String name, String description, Feat optional) {
        this.name = name;
        this.description = description;
        this.optional = optional;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
