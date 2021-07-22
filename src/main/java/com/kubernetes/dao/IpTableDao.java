package com.kubernetes.dao;

import com.wwx.entity.TIpTable;

import java.util.List;

public interface IpTableDao {
    List<TIpTable> getIpTable(String courseId);
    void save(TIpTable ipTable);
    void update(TIpTable ipTable);
}
