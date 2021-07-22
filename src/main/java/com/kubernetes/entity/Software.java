package com.kubernetes.entity;

public class Software {
    private String displayName;
    private String installLocation;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getInstallLocation() {
        return installLocation;
    }

    public void setInstallLocation(String installLocation) {
        this.installLocation = installLocation;
    }

    @Override
    public String toString() {
        return "Software{" +
                "displayName='" + displayName + '\'' +
                ", installLocation='" + installLocation + '\'' +
                '}';
    }
}
