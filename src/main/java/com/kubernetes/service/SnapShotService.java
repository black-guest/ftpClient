package com.kubernetes.service;

import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.kubernetes.base.DockerBase;
import com.kubernetes.base.HarborBase;
import com.kubernetes.config.HarborConfig;
import com.kubernetes.entity.HarborTag;
import com.kubernetes.entity.NameGenerate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SnapShotService {
    private static final Logger LOGGER = LoggerFactory.getLogger("com.kubernetes.service.SnapShotService");
    HarborBase harborBase=new HarborBase();
    DockerBase dockerBase=new DockerBase();

    public List<HarborTag> getSnapShots(NameGenerate nameGenerate){
        if(harborBase.existRepository(nameGenerate.getProjectName(),nameGenerate.getRepoName())) {
            LOGGER.info("getSnapShots方法中 获取镜像"+nameGenerate.getProjectName()
                    +"/"+nameGenerate.getRepoName()+"的快照列表");
            return harborBase.getTags(nameGenerate.getProjectName(),nameGenerate.getRepoName());
        }else {
            LOGGER.info("getSnapShots方法中 无此镜像"+nameGenerate.getProjectName()
                    +"/"+nameGenerate.getRepoName()+"的快照列表");
            return new ArrayList<HarborTag>();
        }
    }

    public void pushSnapShot(NameGenerate nameGenerate, String tagName) throws InterruptedException, IOException {
        String  repository=HarborConfig.hostIp+":"+HarborConfig.port
                +"/"+nameGenerate.getProjectName()+"/"+nameGenerate.getRepoName();
        if(!harborBase.existProject(nameGenerate.getProjectName())){
            harborBase.createProject(nameGenerate.getProjectName());
        }
        while(!harborBase.existProject(nameGenerate.getProjectName())){
            Thread.sleep(500);
        }
        LOGGER.info("pushSnapShot方法中 开始生成镜像 "+repository+":"+tagName);
        Container container=dockerBase.getContainers(nameGenerate.getContainerName());
        dockerBase.commitContainer(container,repository,tagName);
        Image image=dockerBase.getImage(repository,tagName);
        LOGGER.info("pushSnapShot方法中 开始上传镜像 "+repository+":"+tagName);
//        dockerBase.pushImage(image);
        pushImage(image);
        LOGGER.info("pushSnapShot方法中 开始删除镜像 "+repository+":"+tagName);
        dockerBase.removeImage(image);
    }

    //下载镜像 看一下能不能同步
    public void pushImage(Image image) throws InterruptedException, IOException {
        LOGGER.info("开始下载镜像:"+image.getRepoTags()[0]);
        String cmd="cmd /c start cmd.exe /c docker push "+image.getRepoTags()[0];
        Runtime.getRuntime().exec(cmd);
        dockerBase.pushImage(image);
        LOGGER.info("下载镜像完成:"+image.getRepoTags()[0]);
    }

    public Boolean deleteSnapShot(NameGenerate nameGenerate, String tagName){
        try {
            LOGGER.info("pushSnapShot方法中 开始删除快照 "
                    +nameGenerate.getProjectName()+"/"+nameGenerate.getRepoName()+":"+tagName);
            harborBase.deleteTag(nameGenerate.getProjectName(),
                    nameGenerate.getRepoName(),tagName);
            while (harborBase.existTag(nameGenerate.getProjectName(),
                    nameGenerate.getRepoName(),tagName)) {
                Thread.sleep(500);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
