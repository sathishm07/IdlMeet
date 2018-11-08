package com.app.server.models;

public class Community {
    public String getmanagerId() {
        return managerId;
    }

    public String getcommunityName() {
        return communityName;
    }

    public String getcommunityDescription() {
        return communityDescription;
    }

    public String getcreatedDate() {
        return createdDate;
    }

    String communityId=null;
    String managerId;
    String communityName;
    String communityDescription;
    String createdDate;

    public Community(String managerId, String communityName, String communityDescription, String createdDate) {
        this.managerId = managerId;
        this.communityName = communityName;
        this.communityDescription = communityDescription;
        this.createdDate = createdDate;
    }
    public void setId(String communityId) {
        this.communityId = communityId;
    }

}
