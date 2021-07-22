package com.kubernetes.base;

import com.github.dockerjava.api.model.Network;
import com.kubernetes.config.DockerConfig;
import com.kubernetes.config.NetworkConfig;
import com.kubernetes.entity.Software;

import java.io.IOException;
import java.util.List;

/**
 * 初始话docker环境
 */
public class EnvironmentBase {
    private WinBase winBase=new WinBase();
    private DockerBase dockerBase=new DockerBase();

    public Software getDockerDesktop(List<String> softwareRegs) throws IOException {
        String dockerDesktopReg=null;
        for(String softwareReg:softwareRegs){
            if(softwareReg.contains("Docker Desktop")){
                dockerDesktopReg=softwareReg;
            }
        }
        if(dockerDesktopReg==null)
            return null;
        return winBase.getSoftware(dockerDesktopReg);
    }

    public Software getDockerDesktop() throws IOException {
        List<String> softwareRegs=winBase.getSoftwareRegs();
        return getDockerDesktop(softwareRegs);
    }

    public boolean execDockerDesktop(Software dockerDesktop) throws IOException, InterruptedException {
        if(dockerStatus())
            return true;
        String path=dockerDesktop.getInstallLocation()+"/Docker Desktop.exe";
        winBase.execCmdWithoutResult(path);
        Long timeOut=0L;
//        System.out.println(timeOut);
        while(!dockerStatus()){
            Thread.sleep(1000);
            System.out.println(timeOut);
            if(timeOut>= DockerConfig.waitTime)
                return false;
            timeOut+=1000L;
        }
        return true;
    }

    public boolean execDockerDesktop() throws IOException, InterruptedException {
        Software docker=getDockerDesktop();
        return execDockerDesktop(docker);
    }

    public boolean dockerStatus() throws IOException {
        List<String> result=winBase.execCmdWithResult("cmd /c docker version");
        for(String s:result){
            System.out.println(s);
            if(s.contains("error") || s.contains("Error"))
                return false;
        }
        return true;
    }

    public boolean checkNetwork(){
        return dockerBase.checkNetwork();
    }

}
