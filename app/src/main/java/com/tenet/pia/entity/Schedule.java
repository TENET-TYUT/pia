package com.tenet.pia.entity;

/**
 *  日程实体类
 */
public class Schedule {

    /**
     * 日程 id 唯一标识
     */
    private long id;

    /**
     * 开始时间--时间戳
     */
    private long startTime;


    /**
     * 结束时间--时间戳
     */
    private long endTime;

    /**
     * 日程标题
     */
    private String scheduleTitle;

    /**
     *  日程描述
     */
    private String scheduleDes;

    /**
     *  日程地点
     */
    private String scheduleLocation;

    public Schedule(long id, long startTime, long endTime, String scheduleTitle, String scheduleDes, String scheduleLocation) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.scheduleTitle = scheduleTitle;
        this.scheduleDes = scheduleDes;
        this.scheduleLocation = scheduleLocation;
    }



    public Schedule(long startTime, long endTime, String scheduleTitle, String scheduleDes, String scheduleLocation) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.scheduleTitle = scheduleTitle;
        this.scheduleDes = scheduleDes;
        this.scheduleLocation = scheduleLocation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getScheduleTitle() {
        return scheduleTitle;
    }

    public void setScheduleTitle(String scheduleTitle) {
        this.scheduleTitle = scheduleTitle;
    }

    public String getScheduleDes() {
        return scheduleDes;
    }

    public void setScheduleDes(String scheduleDes) {
        this.scheduleDes = scheduleDes;
    }

    public String getScheduleLocation() {
        return scheduleLocation;
    }

    public void setScheduleLocation(String scheduleLocation) {
        this.scheduleLocation = scheduleLocation;
    }
}
