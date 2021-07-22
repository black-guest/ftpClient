package com.kubernetes.dao.impl;

import com.kubernetes.dao.VmDao;
import com.kubernetes.entity.NameGenerate;
import com.kubernetes.util.DBUtil;
import com.wwx.entity.TVm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VmDaoImpl implements VmDao {
    private static final Logger LOGGER = LoggerFactory.getLogger("com.kubernetes.dao.impl.VmDaoImpl");
    private static final String LOAD_VM=
            "select * from t_vm where userId=? and userType=? and vmName=?";
    @Override
    public TVm getVm(NameGenerate nameGenerate) {
        Connection conn= DBUtil.getInstance().getConn();
        PreparedStatement pstmt=null;
        ResultSet rset=null;
        TVm vm=null;

        try {
            pstmt=conn.prepareStatement(LOAD_VM);
            pstmt.setString(1, nameGenerate.getUserId());
            pstmt.setString(2, nameGenerate.getUserType());
            pstmt.setString(3, nameGenerate.getImageName());
            LOGGER.info("运行SQL : "+pstmt);
            rset=pstmt.executeQuery();

            if(rset.next()){
                vm=new TVm();
                vm.setVmId(rset.getLong("vmId"));
                vm.setUserType(rset.getString("userType"));
                vm.setUserId(rset.getLong("userId"));
                vm.setVmName(rset.getString("vmName"));
                vm.setDescription(rset.getString("description"));
                vm.setTemplateId(rset.getLong("templateId"));
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            DBUtil.getInstance().releaseRes(conn, pstmt, rset);
        }

        return vm;
    }
}
