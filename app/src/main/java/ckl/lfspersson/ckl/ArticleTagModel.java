package ckl.lfspersson.ckl;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by LFSPersson on 10/11/16.
 */

public class ArticleTagModel extends RealmObject {
    @SerializedName("id")
    private int id;
    @SerializedName("label")
    private String label;

    public ArticleTagModel(){}

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
