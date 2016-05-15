package com.ormlitedbmodel;

import com.example.Item;
import com.example.Owner;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Neeraj on 15-05-2016.
 */
@DatabaseTable(tableName = "ItemTableModel")
public class ItemTableModel {

    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField(columnName = "is_answered")
    private boolean is_answered; //": false,

    @DatabaseField(columnName = "view_count")
    private int view_count; // ": 12,

    @DatabaseField(columnName = "answer_count")
    private int answer_count; // ": 1yes

    @DatabaseField(columnName = "score")
    private int score; // ": 0,

    @DatabaseField(columnName = "last_activity_date")
    private Date last_activity_date; // ": 1463288993,

    @DatabaseField(columnName = "creation_date")
    private Date creation_date; // ": 1463284832,

    @DatabaseField(columnName = "question_id")
    private int question_id; //": 37234417,

    @DatabaseField(columnName = "link")
    private String link; // ": "http://stackoverflow.com/questions/37234417/how-can-i-bring-view-to-top-in-linearlayout-without-change-index-of-the-view",

    @DatabaseField(columnName = "title")
    private String title; //": "How can I bring view to top in LinearLayout without change index of the view?"

    @DatabaseField(foreign = true, columnName = "ownerTableModel", canBeNull = true,
            foreignAutoRefresh = true, foreignAutoCreate = true)
    private OwnerTableModel ownerTableModel;

//    @DatabaseField(foreign = true, columnName = "tagList", foreignAutoCreate = true)
//    @ForeignCollectionField
//    private ForeignCollection<TagTableModel> tagList;

    public ItemTableModel(){
        //TODO
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean is_answered() {
        return is_answered;
    }

    public void setIs_answered(boolean is_answered) {
        this.is_answered = is_answered;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public int getAnswer_count() {
        return answer_count;
    }

    public void setAnswer_count(int answer_count) {
        this.answer_count = answer_count;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getLast_activity_date() {
        return last_activity_date;
    }

    public void setLast_activity_date(Date last_activity_date) {
        this.last_activity_date = last_activity_date;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OwnerTableModel getOwnerTableModel() {
        return ownerTableModel;
    }

    public void setOwnerTableModel(OwnerTableModel ownerTableModel) {
        this.ownerTableModel = ownerTableModel;
    }

//    public Collection<TagTableModel> getTagList() {
//        return tagList;
//    }
//
//    public void setTagList(ForeignCollection<TagTableModel> tagList) {
//        this.tagList = tagList;
//    }

    public ItemTableModel getMappedItemTableObject(Item itemObject){
        if(itemObject != null){
            //Missing or Extra Field
            itemObject.getAcceptedAnswerId();
            itemObject.getLastEditDate();

            //Stupe filed informatrion
            setTitle(itemObject.getTitle());
            setCreation_date(new Date(itemObject.getCreationDate()));
            setAnswer_count(itemObject.getAnswerCount());
            setIs_answered(itemObject.getIsAnswered());
            setLast_activity_date(new Date(itemObject.getLastActivityDate()));
            setLink(itemObject.getLink());
            setQuestion_id(itemObject.getQuestionId());
            setScore(itemObject.getScore());
            setView_count(itemObject.getViewCount());

            // Owner Object mapping
            Owner ownerObj = itemObject.getOwner();
            if(ownerObj != null){
                this.ownerTableModel = new OwnerTableModel();
                setOwnerTableModel(ownerTableModel.getMappedOwnerTableObject(ownerObj));
            }

            //Setup Tag values
            /*List<String> tagStrList =  itemObject.getTags();
            if(tagStrList != null && tagStrList.size() > 0){
                this.tagList = new <TagTableModel>() {
                };
                for(String strTag : tagStrList){
                    TagTableModel tagTableModel = new TagTableModel();
                    tagTableModel.setTagText(strTag);
                }
            }*/
        }
        return this;
    }

    public Item getItemObject() {
        Item itemObj = new Item();
        itemObj.setLink(getLink());
        //itemObj.setAcceptedAnswerId(getAcceptedAnswerId());
        itemObj.setAnswerCount(getAnswer_count());
        if(getCreation_date() != null){
            itemObj.setCreationDate(Integer.parseInt(String.valueOf(getCreation_date().getTime())));
        }

        if(getLast_activity_date() != null){
            itemObj.setLastActivityDate(Integer.parseInt(String.valueOf(getLast_activity_date().getTime())));
        }

        itemObj.setIsAnswered(is_answered());;
        itemObj.setQuestionId(getQuestion_id());
        itemObj.setScore(getScore());
        itemObj.setTitle(getTitle());
        itemObj.setViewCount(getView_count());

        OwnerTableModel ownerObj = getOwnerTableModel();
        if(ownerObj != null)
            itemObj.setOwner(ownerObj.getOwnerObject());
        return itemObj;
    }
}
