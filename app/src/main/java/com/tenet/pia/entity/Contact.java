package com.tenet.pia.entity;

/**
 *  联系人实体类
 */
public class Contact {

    /**
     *  联系人 id 唯一标识
     */
    private long id;

    /**
     *  联系人姓名
     */
    private String name;

    /**
     *  联系人电话
     */
    private String phone;

    /**
     *  联系人邮箱
     */
    private String email;

    /**
     *  联系人性别
     */
    private String gender;

    /**
     *  所属群组 id
     */
    private long groupId;

    /**
     * 所属群组名字
     */
    private String groupName;

    public Contact(long id, String name, String phone, String email, String gender, long groupId, String groupName) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
