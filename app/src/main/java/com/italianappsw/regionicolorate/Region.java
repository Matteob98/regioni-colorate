package com.italianappsw.regionicolorate;

public class Region {
    public String code;
    public String label;
    public String value;

    public Region(String code, String label, String level) {
        this.code = code;
        this.label = label;
        this.value = level;
    }

    public Region() {
        this.code = "";
        this.label = "";
        this.value = "";
    }
}
