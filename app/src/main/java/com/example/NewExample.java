
package com.example;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Neeraj on 15-05-2016.
 */
public class NewExample {

    @SerializedName("items")
    @Expose
    private List<NewItem> items = new ArrayList<NewItem>();
    @SerializedName("has_more")
    @Expose
    private Boolean hasMore;
    @SerializedName("quota_max")
    @Expose
    private Integer quotaMax;
    @SerializedName("quota_remaining")
    @Expose
    private Integer quotaRemaining;

    /**
     *
     * @return
     *     The items
     */
    public List<NewItem> getItems() {
        return items;
    }

    /**
     *
     * @param items
     *     The items
     */
    public void setItems(List<NewItem> items) {
        this.items = items;
    }

    /**
     *
     * @return
     *     The hasMore
     */
    public Boolean getHasMore() {
        return hasMore;
    }

    /**
     *
     * @param hasMore
     *     The has_more
     */
    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    /**
     *
     * @return
     *     The quotaMax
     */
    public Integer getQuotaMax() {
        return quotaMax;
    }

    /**
     *
     * @param quotaMax
     *     The quota_max
     */
    public void setQuotaMax(Integer quotaMax) {
        this.quotaMax = quotaMax;
    }

    /**
     *
     * @return
     *     The quotaRemaining
     */
    public Integer getQuotaRemaining() {
        return quotaRemaining;
    }

    /**
     *
     * @param quotaRemaining
     *     The quota_remaining
     */
    public void setQuotaRemaining(Integer quotaRemaining) {
        this.quotaRemaining = quotaRemaining;
    }

}
