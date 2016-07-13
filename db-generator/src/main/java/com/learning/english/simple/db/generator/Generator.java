package com.learning.english.simple.db.generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Generator {

    private static final String PROJECT_DIR = System.getProperty("user.dir");

    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.learning.english.simple.db");
        schema.enableKeepSectionsByDefault();

        buildTables(schema);

        try {
            new DaoGenerator().generateAll(schema, PROJECT_DIR + "\\app\\src\\main\\java\\");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void buildTables(final Schema schema) {
        Entity card = schema.addEntity("Card");
        card.addIdProperty().primaryKey().autoincrement();
        card.addStringProperty("word").notNull();
        card.addStringProperty("definition").notNull();
    }
}
