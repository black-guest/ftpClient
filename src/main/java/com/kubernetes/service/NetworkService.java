package com.kubernetes.service;

import com.kubernetes.config.NetworkConfig;
import com.kubernetes.dao.ImageDao;
import com.kubernetes.dao.IpTableDao;
import com.kubernetes.dao.impl.ImageDaoImpl;
import com.kubernetes.dao.impl.IpTableDaoImpl;
import com.kubernetes.entity.NameGenerate;
import com.wwx.entity.TImage;
import com.wwx.entity.TIpTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NetworkService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkService.class);

    private IpTableDao ipTableDao=new IpTableDaoImpl();
    private ImageDao imageDao=new ImageDaoImpl();

    public String getHostIp(NameGenerate nameGenerate){
        TImage image=imageDao.getImage(nameGenerate);
        if(image==null){
            return getNewHostIp(nameGenerate);
        }
        return image.getImageIp();
    }

    /**
     * 获取新的网络地址
     * @param courseId
     * @return
     */
    private String getNewNetwork(String courseId){
        List<TIpTable> ipTables=ipTableDao.getIpTable(courseId);

        List<String> networks=new ArrayList<String>();

        for(TIpTable ipTable:ipTables){
            if(ipTable.getCourseId().equals(courseId) &&  ipTable.getHostNum()<254)
                return ipTable.getNetwork();
            networks.add(ipTable.getNetwork());
        }

//        NetworkConfig networkConfig=new NetworkConfig();
//        networks.addAll(Arrays.asList(networkConfig.getK8sNetwork()));
        networks.addAll(Arrays.asList(NetworkConfig.k8sNetwork));

        List<Integer> subNetworks=new ArrayList<>();
        for(String s:networks){
            String i=s.split("\\.")[2];
            subNetworks.add(Integer.valueOf(i));
        }

        Integer subNetwork=0;

        for(Integer i=1;i<255;i++){
            if(!subNetworks.contains(i)){
                subNetwork=i;
                break;
            }
        }
//        String[] res=networkConfig.getCidr().split("\\.");
        String[] res=NetworkConfig.subIp.split("\\.");
        return res[0]+"."+res[1]+"."+subNetwork+".0";
    }

    /**
     * 获取 新的IP地址
     * @return
     */
    private String getNewHostIp(NameGenerate nameGenerate){
//        QueryWrapper<TIpTable> queryWrapper=new QueryWrapper<>();
//        queryWrapper.lambda().eq(TIpTable::getCourseId,courseId)
//                .lt(TIpTable::getHostNum,254);
//        LambdaQueryWrapper<TIpTable> q=queryWrapper.lambda();
//        List<TIpTable> ipTables=list(q);

        String courseId=nameGenerate.getCourseId();
        String userType=nameGenerate.getUserType();
        String userId=nameGenerate.getUserId();
        String imageName=nameGenerate.getImageName();

        List<TIpTable> ipTables=ipTableDao.getIpTable(courseId);

        if(needipTables(ipTables)){
            String network = getNewNetwork(courseId);
            TIpTable ipTable=new TIpTable(courseId,network,1);

            String[] ips=network.split("\\.");
            String hostIp=ips[0]+"."+ips[1]+"."+ips[2]+".1";

            TImage image=new TImage(imageName,courseId,userType,userId,hostIp,network);
            ipTableDao.save(ipTable);
            imageDao.save(image);

            return hostIp;
        }else{
            TIpTable ipTable=ipTables.get(0);
            String network=ipTable.getNetwork();

//            QueryWrapper<TImage> imageQuery=new QueryWrapper<>();
//            imageQuery.lambda().eq(TImage::getNetwork,network);
//            LambdaQueryWrapper<TImage> imageQ=imageQuery.lambda();
//            List<TImage> images=imageService.list(imageQ);

            List<TImage> images=imageDao.getImageByNetwork(network);
            List<Integer> subHostIps=new ArrayList<>();

            for(TImage image:images){
                String subImageIp=image.getImageIp().split("\\.")[3];
                subHostIps.add(Integer.parseInt(subImageIp));
            }

            Integer subHostIp=0;

            for(Integer i=1;i<255;i++){
                if(!subHostIps.contains(i)){
                    subHostIp=i;
                    break;
                }
            }

            String[] ips=network.split("\\.");
            String hostIp=ips[0]+"."+ips[1]+"."+ips[2]+"."+subHostIp;

            //存储 image的信息
            TImage image=new TImage(imageName,courseId,userType,userId,hostIp,network);
            imageDao.save(image);

            //更新  ipTable中的主机数
            ipTable.setHostNum(ipTable.getHostNum()+1);
//            UpdateWrapper<TIpTable> updateWrapper=new UpdateWrapper<>();
//            updateWrapper.lambda().eq(TIpTable::getIpTableId,ipTable.getIpTableId());
//            update(ipTable,updateWrapper.lambda());
            ipTableDao.update(ipTable);
            return hostIp;

        }
    }

    private boolean needipTables(List<TIpTable> ipTables){
        if(ipTables==null)
            return true;
        for(TIpTable ipTable:ipTables){
            if(ipTable.getHostNum()<254)
                return false;
        }
        return true;
    }
}
