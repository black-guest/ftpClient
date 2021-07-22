package com.kubernetes.service;

import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.kubernetes.base.DockerBase;
import com.kubernetes.base.HarborBase;
import com.kubernetes.config.HarborConfig;
import com.kubernetes.dao.CourseDao;
import com.kubernetes.dao.ImageDao;
import com.kubernetes.dao.TemplateDao;
import com.kubernetes.dao.VmDao;
import com.kubernetes.dao.impl.CourseDaoImpl;
import com.kubernetes.dao.impl.ImageDaoImpl;
import com.kubernetes.dao.impl.TemplateDaoImpl;
import com.kubernetes.dao.impl.VmDaoImpl;
import com.kubernetes.entity.HarborTag;
import com.kubernetes.entity.NameGenerate;
import com.wwx.entity.TCourse;
import com.wwx.entity.TImage;
import com.wwx.entity.TTemplate;
import com.wwx.entity.TVm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import javax.naming.Name;


public class DockerService {
    private static final Logger LOGGER = LoggerFactory.getLogger("com.kubernetes.service.DockerService");
    private DockerBase dockerBase=new DockerBase();
    private TemplateService templateService=new TemplateService();
    private NetworkService networkService=new NetworkService();
    private HarborBase harborBase=new HarborBase();

    public Map<String,Object> startContainer(NameGenerate nameGenerate) throws InterruptedException, IOException {

        Map<String,Object> result=new HashMap<>();
        String image= HarborConfig.hostIp+":"+HarborConfig.port+"/";

        TTemplate template=templateService.getTemplate(nameGenerate);
        if(template == null){
            LOGGER.error("startContainer方法中的template为Null");
            result.put("result","startContainer方法中的template为Null");
            return result;
        }
        //判断是否存在已经存在的容器
        if(dockerBase.existContainer(nameGenerate.getContainerName())){
            Integer exposedPort=dockerBase.getPort(nameGenerate.getContainerName());
            Map<String,Object> res=new HashMap<>();
            res.put("url","tcp://localhost:"+exposedPort+template.getUrl());
            res.put("type",template.getType());
            result.put("result",res);
            return result;
        }

        String ip=networkService.getHostIp(nameGenerate);

        if(harborBase.existRepository(nameGenerate.getProjectName(),nameGenerate.getRepoName())){
            HarborTag tag=harborBase.getLastTag(nameGenerate.getProjectName(),nameGenerate.getRepoName());
            image=image+nameGenerate.getProjectName()+"/"+nameGenerate.getRepoName()+":"+tag.getName();
        }else {
            image=image+template.getImage();
        }

        //下载镜像
        pullImage(image);


        LOGGER.info("启动容器:"+image);
        String url=dockerBase.startContainer(image, template.getExposePort(), template.getHostname(),
                ip, nameGenerate.getContainerName());
        if(url==null){
            LOGGER.error("startContainer方法中的url为Null");
        }

        url+=template.getUrl();
        Map<String,Object> res=new HashMap<>();
        res.put("url",url);
        res.put("type",template.getType());
        result.put("result",res);
        return result;
    }

    public Map<String,Object> restore(NameGenerate nameGenerate) throws InterruptedException, IOException {
        Map<String,Object> result=new HashMap<>();
        TTemplate template=templateService.getTemplate(nameGenerate);
        if(template == null){
            LOGGER.error("restore方法中的template为Null");
            result.put("result","restore方法中的template为Null");
            return result;
        }

        String image= HarborConfig.hostIp+":"+HarborConfig.port+"/"
                +template.getImage();


        //判断是否存在已经存在的容器
        if(dockerBase.existContainer(nameGenerate.getContainerName(),image)){
            Integer exposedPort=dockerBase.getPort(nameGenerate.getContainerName());
            result.put("result","tcp://localhost:"+exposedPort+template.getUrl());
            return result;
        }

        String ip=networkService.getHostIp(nameGenerate);


        //下载镜像
        pullImage(image);


        LOGGER.info("启动容器:"+image);
        String url=dockerBase.startContainer(image, template.getExposePort(), template.getHostname(),
                ip, nameGenerate.getContainerName());
        if(url==null){
            LOGGER.error("restore方法中的url为Null");
        }

        url+=template.getUrl();
        Map<String,Object> res=new HashMap<>();
        res.put("url",url);
        res.put("type",template.getType());
        result.put("result",res);
        return result;
    }

    public Map<String,Object> replaceDeployment(NameGenerate nameGenerate,String tagName) throws InterruptedException, IOException {
        String image= HarborConfig.hostIp+":"+HarborConfig.port+"/"
                +nameGenerate.getProjectName()+"/"+nameGenerate.getRepoName()
                +":"+tagName;
        Map<String,Object> result=new HashMap<>();

        TTemplate template=templateService.getTemplate(nameGenerate);
        if(template == null){
            LOGGER.error("replaceDeployment方法中的template为Null");
            result.put("result","startContainer方法中的template为Null");
            return result;
        }
        //判断是否存在已经存在的容器
        if(dockerBase.existContainer(nameGenerate.getContainerName(),image)){
            Integer exposedPort=dockerBase.getPort(nameGenerate.getContainerName());
            result.put("result","localhost:"+exposedPort+template.getUrl());
            return result;
        }

        String ip=networkService.getHostIp(nameGenerate);

        //下载镜像
        pullImage(image);



        LOGGER.info("启动容器:"+image);
        String url=dockerBase.startContainer(image, template.getExposePort(), template.getHostname(),
                ip, nameGenerate.getContainerName());
        if(url==null){
            LOGGER.error("replaceDeployment方法中的url为Null");
        }

        url+=template.getUrl();
        Map<String,Object> res=new HashMap<>();
        res.put("url",url);
        res.put("type",template.getType());
        result.put("result",res);
        return result;
    }

    //下载镜像 看一下能不能同步
    public void pullImage(String image) throws InterruptedException, IOException {
        LOGGER.info("开始下载镜像:"+image);
        String cmd="cmd /c start cmd.exe /c docker pull "+image;
        Runtime.getRuntime().exec(cmd);
        dockerBase.pullImage(image);
        LOGGER.info("下载镜像完成:"+image);

    }

}

