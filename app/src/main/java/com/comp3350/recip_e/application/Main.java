package com.comp3350.recip_e.application;

public class Main {
    private static String recipeDBName="SC";

    public static void setDBPathName(final String name) {
        try {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        recipeDBName = name;
    }

    public static String getDBPathName() {
        return recipeDBName;
    }
}
