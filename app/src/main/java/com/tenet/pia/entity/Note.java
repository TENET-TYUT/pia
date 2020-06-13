package com.tenet.pia.entity;

public class Note {

    /**
     *  记事本 id 唯一标识
     */
    private long id;

    /**
     *  记事本标题
     */
    private String noteTitle;

    /**
     *  记事本内容
     */
    private String noteContent;

    /**
     *  记事本创建时间
     */
    private long createTime;

    public Note(long id, String noteTitle, String noteContent, long createTime) {
        this.id = id;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
