package com.zbl.anju.model.request;

/**
 * Created by AMing on 16/1/29.
 * Company RongCloud
 */
public class DismissGroupRequest {

    private String groupId;

    public DismissGroupRequest(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
