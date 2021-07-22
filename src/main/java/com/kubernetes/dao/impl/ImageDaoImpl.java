package com.kubernetes.dao.impl;

import com.kubernetes.dao.ImageDao;
import com.kubernetes.entity.NameGenerate;
import com.kubernetes.util.DBUtil;
import com.wwx.entity.TCourse;
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

public class ImageDaoImpl implements ImageDao {
    private static final Logger LOGGER = LoggerFactory.getLogger("com.kubernetes.dao.impl.ImageDaoImpl");
    private static final String GET_IMAGE=
            "select * from t_image where userType=? and userId=? " +
                    "and courseId=? and imageName=?";
    private static final String GET_IMAGE_NETWORK=
            "select * from t_image where network=?";
    private static final String SAVE_IMAGE="insert into t_image" +
            "(courseId,userType,userId,imageName,imageIp,network) values(?,?,?,?,?,?)";

    @Override
    public TImage getImage(NameGenerate nameGenerate) {
        Connection conn= DBUtil.getInstance().getConn();
        PreparedStatement pstmt=null;
        ResultSet rset=null;
        TImage image=null;

        try {
            pstmt=conn.prepareStatement(GET_IMAGE);
            pstmt.setString(1, nameGenerate.getUserType());
            pstmt.setString(2, nameGenerate.getUserId());
            pstmt.setString(3, nameGenerate.getCourseId());
            pstmt.setString(4, nameGenerate.getImageName());
            LOGGER.info("运行SQL : "+pstmt);
            rset=pstmt.executeQuery();

            if(rset.next()){
                image=new TImage();
                image.setImageId(rset.getLong("imageId"));
                image.setImageName(rset.getString("imageName"));
                image.setCourseId(rset.getString("courseId"));
                image.setUserType(rset.getString("userType"));
                image.setUserId(rset.getString("userId"));
                image.setImageIp(rset.getString("imageIp"));
                image.setNetwork(rset.getString("network"));
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            DBUtil.getInstance().releaseRes(conn, pstmt, rset);
        }

        return image;
    }

    public List<TImage> getImageByNetwork(String network) {
        Connection conn= DBUtil.getInstance().getConn();
        PreparedStatement pstmt=null;
        ResultSet rset=null;
        List<TImage> images=new ArrayList<>();

        try {
            pstmt=conn.prepareStatement(GET_IMAGE_NETWORK);
            pstmt.setString(1, network);
            LOGGER.info("运行SQL : "+pstmt);
            rset=pstmt.executeQuery();

            while(rset.next()){
                TImage image=new TImage();
                image.setImageId(rset.getLong("imageId"));
                image.setImageName(rset.getString("imageName"));
                image.setCourseId(rset.getString("courseId"));
                image.setUserType(rset.getString("userType"));
                image.setUserId(rset.getString("userId"));
                image.setImageIp(rset.getString("imageIp"));
                image.setNetwork(rset.getString("network"));
                images.add(image);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            DBUtil.getInstance().releaseRes(conn, pstmt, rset);
        }

        return images;
    }

    public void save(TImage image) {
        Connection conn=DBUtil.getInstance().getConn();
        PreparedStatement pstmt=null;
        try {
            //把对象中的各个属性取出之后，转化为SQL语句的一部分，最后使对象成为数据库表的一条记录。
            pstmt=conn.prepareStatement(SAVE_IMAGE);
            pstmt.setString(1, image.getCourseId());
            pstmt.setString(2, image.getUserType());
            pstmt.setString(3, image.getUserId());
            pstmt.setString(4, image.getImageName());
            pstmt.setString(5, image.getImageIp());
            pstmt.setString(6, image.getNetwork());
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
