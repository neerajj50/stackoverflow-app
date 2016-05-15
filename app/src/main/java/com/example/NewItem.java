
package com.example;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Neeraj on 15-05-2016.
 */
public class NewItem {
    @SerializedName("owner")
    @Expose
    private Owner owner;
    @SerializedName("is_accepted")
    @Expose
    private Boolean is_accepted;
    @SerializedName("score")
    @Expose
    @DatabaseField
    private Integer score;
    @SerializedName("last_activity_date")
    @Expose
    private Integer lastActivityDate;
    @SerializedName("last_edit_date")
    @Expose
    private Integer lastEditDate;
    @SerializedName("creation_date")
    @Expose
    private Integer creationDate;
    @SerializedName("question_id")
    @Expose
    @DatabaseField
    private Integer questionId;
    @SerializedName("answer_id")
    @Expose
    private Integer answer_id;

    public Owner getOwner() {
        return owner;
    }

    /**
     *
     * @param owner
     *     The owner
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     *
     * @return
     *     The isAnswered
     */
    public Boolean getIsaccepted() {
        return is_accepted;
    }

    /**
     *
     * @param isAnswered
     *     The is_answered
     */
    public void setIsaccepted(Boolean isAnswered) {
        this.is_accepted = is_accepted;
    }

    /**
     *
     * @return
     *     The score
     */
    public Integer getScore() {
        return score;
    }

    /**
     *
     * @param score
     *     The score
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     *
     * @return
     *     The lastActivityDate
     */
    public Integer getLastActivityDate() {
        return lastActivityDate;
    }

    /**
     *
     * @param lastActivityDate
     *     The last_activity_date
     */
    public void setLastActivityDate(Integer lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }
    /**
     *
     * @return
     *     The lastEditDate
     */
    public Integer getLastEditDate() {
        return lastEditDate;
    }

    /**
     *
     * @param lastEditDate
     *     The last_edit_date
     */
    public void setLastEditDate(Integer lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    /**
     *
     * @return
     *     The creationDate
     */
    public Integer getCreationDate() {
        return creationDate;
    }

    /**
     *
     * @param creationDate
     *     The creation_date
     */
    public void setCreationDate(Integer creationDate) {
        this.creationDate = creationDate;
    }


    /**
     *
     * @return
     *     The questionId
     */
    public Integer getQuestionId() {
        return questionId;
    }

    /**
     *
     * @param questionId
     *     The question_id
     */
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }


    /**
     *
     * @return
     *     The acceptedAnswerId
     */
    public Integer getAcceptedAnswerId() {
        return answer_id;
    }

    /**
     *
     * @param acceptedAnswerId
     *     The accepted_answer_id
     */
    public void setAcceptedAnswerId(Integer acceptedAnswerId) {
        this.answer_id = acceptedAnswerId;
    }

}
