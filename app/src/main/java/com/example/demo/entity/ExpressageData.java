package com.example.demo.entity;

/*
 *  项目名：    Demo
 *  包名：      com.example.demo.entity
 *  文件名：    Expressage
 *  描述：      快递实体类
 */
public class ExpressageData {
    //时间
    private String datetime;
    //状态
    private String remark;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
