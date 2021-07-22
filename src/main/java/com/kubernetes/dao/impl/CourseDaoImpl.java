package com.kubernetes.dao.impl;

import com.kubernetes.dao.CourseDao;
import com.kubernetes.util.DBUtil;
import com.wwx.entity.TCourse;
import com.wwx.entity.TStudent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseDaoImpl implements CourseDao {
    private static final Logger LOGGER = LoggerFactory.getLogger("com.kubernetes.dao.impl.CourseDaoImpl");
    private static final String SQL_LOADBYID=
            "select * from t_course where courseId=?";
    @Override
    public TCourse getCourse(String courseId) {
        Connection conn= DBUtil.getInstance().getConn();
        PreparedStatement pstmt=null;
        ResultSet rset=null;
        TCourse course=null;

        try {
            pstmt=conn.prepareStatement(SQL_LOADBYID);
            pstmt.setString(1, courseId);
            LOGGER.info("运行SQL : "+pstmt);
            rset=pstmt.executeQuery();

            if(rset.next()){
                course=new TCourse();
                course.setCourseId(rset.getLong("courseId"));
                course.setCourseName(rset.getString("courseName"));
                course.setCourseType(rset.getInt("courseType"));
                course.setCourseImagePath(rset.getString("courseImagePath"));
                course.setState(rset.getInt("state"));
                course.setTags(rset.getString("tags"));
                course.setTemplateId(rset.getLong("templateId"));
            }



        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            DBUtil.getInstance().releaseRes(conn, pstmt, rset);
        }

        return course;
    }
}
