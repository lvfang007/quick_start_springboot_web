package com.lvfang.mybatisplus.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LvFang
 * @Date: Created in 2018/7/24.
 * @Description:分页容器
 */
public class ListModel<T> implements Serializable {

    private int pageCount;//总页数

    private int page;//页签

    private int totalCount;//总数

    private long time;//预留查询时间

    private List<T> list;//数据容器

    public ListModel() {
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public ListModel<T> initNull() {
        ListModel listModel = new ListModel();
        listModel.setList(new ArrayList());
        listModel.setPage(1);
        listModel.setPageCount(1);
        listModel.setTotalCount(0);
        listModel.setTime(1);
        return listModel;
    }
}
