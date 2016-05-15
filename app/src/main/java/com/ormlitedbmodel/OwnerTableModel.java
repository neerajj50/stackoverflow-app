package com.ormlitedbmodel;

/**
 * Created by Neeraj on 15-05-2016.
 */
import com.example.Owner;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "OwnerTableModel")
public class OwnerTableModel {

    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField(columnName = "reputation")
    private Integer reputation;

    @DatabaseField(columnName = "user_id")
    private Integer userId;

    @DatabaseField(columnName = "user_type")
    private String userType;

    @DatabaseField(columnName = "profile_image")
    private String profileImage;

    @DatabaseField(columnName = "display_name")
    private String displayName;

    @DatabaseField(columnName = "link")
    private String link;

    @DatabaseField(foreign = true, canBeNull = true,
            foreignAutoRefresh = true, foreignAutoCreate = true)
    public ItemTableModel itemTableModel;

    public OwnerTableModel(){
        //TODO
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getReputation() {
        return reputation;
    }

    public void setReputation(Integer reputation) {
        this.reputation = reputation;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public OwnerTableModel getMappedOwnerTableObject(Owner ownerObject){
        if(ownerObject != null) {
            setLink(ownerObject.getLink());
            setDisplayName(ownerObject.getDisplayName());
            setReputation(ownerObject.getReputation());
            setProfileImage(ownerObject.getProfileImage());
            setUserId(ownerObject.getUserId());
            setUserType(ownerObject.getUserType());
        }
        return this;
    }

    public Owner getOwnerObject(){
        Owner obj = new Owner();
        obj.setLink(getLink());
        obj.setDisplayName(getDisplayName());
        obj.setReputation(getReputation());
        obj.setProfileImage(getProfileImage());
        obj.setUserId(getUserId());
        obj.setUserType(getUserType());
        return obj;
    }
}
