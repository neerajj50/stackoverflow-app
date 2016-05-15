package com.ormlitedbmodel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Neeraj on 15-05-2016.
 */

@DatabaseTable(tableName = "TagTableModel")
public class TagTableModel {

    @DatabaseField(uniqueIndex = true)
    int id;

    @DatabaseField(foreign = true, canBeNull = true,
            foreignAutoRefresh = true, foreignAutoCreate = true)
    public ItemTableModel itemTableModel;


    @DatabaseField
    private String tagText;

    public TagTableModel() {
        //TODO
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ItemTableModel getItemTableModel() {
        return itemTableModel;
    }

    public void setItemTableModel(ItemTableModel itemTableModel) {
        this.itemTableModel = itemTableModel;
    }

    public String getTagText() {
        return tagText;
    }

    public void setTagText(String tagText) {
        this.tagText = tagText;
    }
}
