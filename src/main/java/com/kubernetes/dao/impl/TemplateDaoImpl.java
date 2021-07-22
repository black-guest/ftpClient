package com.kubernetes.dao.impl;

import com.kubernetes.dao.TemplateDao;
import com.kubernetes.util.DBUtil;
import com.wwx.entity.TCourse;
import com.wwx.entity.TTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TemplateDaoImpl implements TemplateDao {
    private static final Logger LOGGER = LoggerFactory.getLogger("com.kubernetes.dao.impl.TemplateDaoImpl");
    private static final String SQL_LOADBYID=
            "select * from t_template where templateId=?";

    @Override
    public TTemplate getTemplate(String templateId) {
        Connection conn= DBUtil.getInstance().getConn();
        PreparedStatement pstmt=null;
        ResultSet rset=null;
        TTemplate template=null;

        try {
            pstmt=conn.prepareStatement(SQL_LOADBYID);
            pstmt.setString(1, templateId);
            LOGGER.info("运行SQL : "+pstmt);
            rset=pstmt.executeQuery();

            if(rset.next()){
                template=new TTemplate();
                template.setTemplateId(rset.getLong("templateId"));
                template.setCpu(rset.getDouble("cpu"));
                template.setMemory(rset.getString("memory"));
                template.setHostname(rset.getString("hostname"));
                template.setImage(rset.getString("image"));
                template.setName(rset.getString("name"));
                template.setState(rset.getInt("state"));
                template.setUrl(rset.getString("url"));
                template.setExposePort(rset.getInt("exposePort"));
                template.setType(rset.getInt("type"));
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            DBUtil.getInstance().releaseRes(conn, pstmt, rset);
        }

        return template;
    }
}
