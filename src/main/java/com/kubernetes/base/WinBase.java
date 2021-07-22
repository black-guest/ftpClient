package com.kubernetes.base;

import com.kubernetes.entity.Software;
import lombok.SneakyThrows;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class WinBase {

    public List<String> getSoftwareRegs() throws IOException {
        Properties props = System.getProperties();
        System.out.println("操作系统的版本：" + props.getProperty("os.version"));

        String cmd="cmd /c reg query HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\";

        return execCmdWithResult(cmd);
    }

    public Software getSoftware(String softwareReg) throws IOException {
        Software software=new Software();
        String stringFormat="cmd /c reg query \"%s\" /v %s";

        List<String> res1=execCmdWithResult(String.format(stringFormat,softwareReg,"DisplayName"));
        software.setDisplayName(
                    //去掉无用信息
                res1.get(1).split("    ")[2]);

        List<String> res2=execCmdWithResult(String.format(stringFormat,softwareReg,"InstallLocation"));
        software.setInstallLocation(
                //去掉无用信息
                res2.get(1).split("    ")[2]);

        return software;
    }

    public List<String> execCmdWithResult(String cmd) throws IOException {
        List<String> result=new ArrayList<>();
        Runtime runtime = Runtime.getRuntime();
        Process process=runtime.exec(cmd);
        BufferedReader in = new BufferedReader(new InputStreamReader(process
                .getInputStream(), "GBK"));

        String s = null;
//        while ((s = in.readLine()) != null) {
//            if(s.trim().length()>0) {
//                result.add(s.trim());
//                System.out.println(s.trim());
//            }
//        }

        SequenceInputStream sis = new SequenceInputStream (process.getInputStream (), process.getErrorStream ());
        InputStreamReader isr = new InputStreamReader (sis, "gbk");
        in = new BufferedReader (isr);

        while ((s = in.readLine()) != null) {
            if(s.trim().length()>0) {
                result.add(s.trim());
                System.out.println(s.trim());
            }
        }

        in.close();
        process.destroy();
        return result;
    }

    public void execCmdWithoutResult(String cmd) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process process=runtime.exec(cmd);
    }


    public List<String> getDockerResult(String cmd) throws IOException, InterruptedException {
        List<String> result=new ArrayList<>();
        Runtime runtime = Runtime.getRuntime();
        final Process[] process = {null};

        new Thread(){
            @SneakyThrows
            public void run(){

                process[0] =runtime.exec(cmd);
            }
        }.start();


        while(process[0] ==null){
            System.out.println(process[0]==null);
        }


        System.out.println("finish while");
        BufferedReader in = new BufferedReader(new InputStreamReader(process[0]
                .getInputStream(), "GBK"));
        System.out.println("finish in");

        String s = null;
        while ((s = in.readLine()) != null) {
            if(s.trim().length()>0) {
                result.add(s.trim());
                System.out.println(s.trim());
            }
        }

        System.out.println("finish readLine");

        in.close();
        process[0].destroy();
        return result;
    }
}
