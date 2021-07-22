package com.kubernetes.dao.impl;

import com.kubernetes.dao.IpTableDao;
import com.kubernetes.util.DBUtil;
import com.wwx.entity.TImage;
import com.wwx.entity.TIpTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IpTableDaoImpl implements IpTableDao {
    private static final Logger LOGGER = LoggerFactory.getLogger("com.kubernetes.dao.impl.IpTableDaoImpl");
    private static final String GET_IP_TABLE=
            "select * from  t_ip_table where courseId=?";
    private static final String SAVE_IP_TABLE="insert into t_ip_table" +
            "(network,courseId,hostNum) values(?,?,?)";
    private static final String UPDATE_IP_TABLE=
            "update t_ip_table set hostNum=? where ipTableId=?";

    @Override
    public List<TIpTable> getIpTable(String courseId) {
        Connection conn= DBUtil.getInstance().getConn();
        PreparedStatement pstmt=null;
        ResultSet rset=null;
        List<TIpTable> ipTables=new ArrayList<>();

        try {
            pstmt=conn.prepareStatement(GET_IP_TABLE);
            pstmt.setString(1, courseId);
            LOGGER.info("运行SQL : "+pstmt);
            rset=pstmt.executeQuery();

            while(rset.next()){
                TIpTable ipTable=new TIpTable();
                ipTable.setIpTableId(rset.getLong("ipTableId"));
                ipTable.setNetwork(rset.getString("network"));
                ipTable.setCourseId(rset.getString("courseId"));
                ipTable.setHostNum(rset.getInt("hostNum"));
                ipTables.add(ipTable);

            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            DBUtil.getInstance().releaseRes(conn, pstmt, rset);
        }

        return ipTables;
    }

    public void save(TIpTable ipTable) {
        Connection conn=DBUtil.getInstance().getConn();
        PreparedStatement pstmt=null;

        try {
            //把对象中的各个属性取出之后，转化为SQL语句的一部分，最后使对象成为数据库表的一条记录。
            pstmt=conn.prepareStatement(SAVE_IP_TABLE);
            pstmt.setString(1, ipTable.getNetwork());
            pstmt.setString(2, ipTable.getCourseId());
            pstmt.setInt(3, ipTable.getHostNum());
            LOGGER.info("运行SQL : "+pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            DBUtil.getInstance().releaseRes(conn, pstmt, null);
        }

    }

    @Override
    public void update(TIpTable ipTable) {
        Connection conn=DBUtil.getInstance().getConn();
        PreparedStatement pstmt=null;

        try {
            //把对象中的各个属性取出之后，转化为SQL语句的一部分，最后使对象成为数据库表的一条记录。
            pstmt=conn.prepareStatement(UPDATE_IP_TABLE);
            pstmt.setInt(1, ipTable.getHostNum());
            pstmt.setLong(2, ipTable.getIpTableId());
            LOGGER.info("运行SQL : "+pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            DBUtil.getInstance().releaseRes(conn, pstmt, null);
        }
    }

}
