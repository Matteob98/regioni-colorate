package com.italianappsw.regionicolorate;

import java.util.ArrayList;
import java.util.List;

class YellowRules {

    static List<Rule>
            commercial_activities = new ArrayList<>(),
            sportive_activities = new ArrayList<>(),
            events = new ArrayList<>(),
            instruction = new ArrayList<>(),
            displacements = new ArrayList<>();

    public static void addCommercial_activity(String name, int value) {
        commercial_activities.add(
                new Rule(
                        stringFormat(name),
                        getValueByInt(value)
                ));
    }

    public static void addSportive_activity(String name, int value) {
        sportive_activities.add(
                new Rule(
                        stringFormat(name),
                        getValueByInt(value)
                ));
    }

    public static void addEvent(String name, int value) {
        events.add(
                new Rule(
                        stringFormat(name),
                        getValueByInt(value)
                ));
    }

    public static void addInstruction(String name, int value) {
        instruction.add(
                new Rule(
                        stringFormat(name),
                        getValueByInt(value)
                ));
    }

    public static void addDisplacements(String name, int value) {
        displacements.add(
                new Rule(
                        stringFormat(name),
                        getValueByInt(value)
                ));
    }

    private static VALUE getValueByInt(int value) {
        switch (value) {
            case 0:
                return VALUE.OPEN;
            case 2:
                return VALUE.LIMITED;
            case 3:
                return VALUE.CLOSED;
            default:
                return VALUE.CLOSED;
        }
    }

    private static String stringFormat(String string) {
        string = string.replaceAll("_", " ");
        string = string.substring(0,1).toUpperCase() + string.substring(1).toLowerCase();
        return string;
    }

    public static void eraseDisplacements() {
        displacements.clear();
    }

    public static void eraseInstruction() {
        instruction.clear();
    }

    public static void eraseEvents() {
        events.clear();
    }

    public static void eraseSportive_activities() {
        sportive_activities.clear();
    }

    public static void eraseCommercial_activities() {
        commercial_activities.clear();
    }
}
