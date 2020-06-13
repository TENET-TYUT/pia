package com.tenet.pia.entity;

/**
 *  群组实体类
 */
public class Group {

    /**
     *  群组 id 唯一标识
     */
    private long id;

    /**
     *  群组名字
     */
    private String groupName;

    public Group(long id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
