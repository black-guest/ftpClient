package com.kubernetes.util;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedHashMap;

public class YmlReader {

    public LinkedHashMap readYml(String filePath) {

        String path="";

        String os = System.getProperty("os.name");
        System.out.println(os);
        if(os.contains("Linux")) {
            path=System.getProperty("user.dir")+"/"+filePath;
        }else {
            String rootPath=System.getProperty("user.dir")+"/src/main/resources/";
            path=rootPath+filePath;
        }


        System.out.println(path);

        File yamlFile=new File(path);
        Yaml yaml = new Yaml();
//        System.out.println(yamlFile.exists());
        try {
            if (yamlFile.exists()) {
                //获取test.yaml文件中的配置数据，然后转换为obj，
                Object obj = yaml.load(new FileInputStream(yamlFile));
//            System.out.println(obj);
                //也可以将值转换为Map
                LinkedHashMap map = (LinkedHashMap) yaml.load(new FileInputStream(yamlFile));

//            HashMap map=yaml.loadAs(new FileInputStream(yamlFile), HashMap.class);
//            System.out.println(map);
                //通过map我们取值就可以了.
                return map;
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }
}
