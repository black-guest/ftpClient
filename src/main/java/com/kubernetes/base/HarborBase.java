package com.kubernetes.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kubernetes.config.HarborConfig;
import com.kubernetes.entity.HarborProject;
import com.kubernetes.entity.HarborRepository;
import com.kubernetes.entity.HarborTag;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;


public class HarborBase {
//    private RestTemplate restTemplate =new RestTemplateBuilder()
//            .basicAuthentication("admin","Harboor12345").build();
    private RestTemplate restTemplate =new RestTemplateBuilder()
            .basicAuthentication(HarborConfig.userName,HarborConfig.password).build();
//    private RestTemplate common=new RestTemplate();
//    private String harborBaseUrl="http://139.9.39.182/api/";
    private String harborBaseUrl="http://"+HarborConfig.hostIp+":"+HarborConfig.port+"/api/";


    public List<HarborProject> getProjects(){
        String result=restTemplate.getForObject(harborBaseUrl+"projects",String.class);
        return JSONArray.parseArray(result, HarborProject.class);
    }

    public List<HarborProject> getProjects(Integer pageNo, Integer pageSize){
        String result=restTemplate.getForObject(
                harborBaseUrl+"projects?page="+pageNo+"&page_size="+pageSize,String.class);
        return JSONArray.parseArray(result, HarborProject.class);
    }

    /**
     * Search parameter for project and repository name.
     */
    public Map<String,Object> search(String searchString){
        String result= restTemplate.getForObject(harborBaseUrl+"search?q="+searchString,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);

        String projects=jsonObject.getString("project");
        List<HarborProject> harborProjects=JSONArray.parseArray(projects, HarborProject.class);

        //"project_id":15,"tags_count":1,"project_name":"course-10","pull_count":0,
        // "repository_name":"course-10/10-t-2-hadoop","project_public":true
        String repos=jsonObject.getString("repository");
        List<HarborRepository> harborRepositories=JSONArray.parseArray(repos, HarborRepository.class);

        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("project",harborProjects);
        resultMap.put("repository",harborRepositories);
        return resultMap;
    }

    public HarborProject getProject(Long projectId){
        String result=restTemplate.getForObject(harborBaseUrl+"projects/"+projectId,String.class);
        return JSONObject.parseObject(result, HarborProject.class);
    }

    public Long getCourseId(Long projectId){
        HarborProject harborProject=getProject(projectId);
        String[] items=harborProject.getName().split("-");

        //正则表达式判断字符串是否为数字
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");

        if(items.length<2 || !pattern.matcher(items[1]).matches()) {
            return -1L;
        }
        return  Long.parseLong(items[1]);
    }

    public Long getProjectId(String projectName){
        List<HarborProject> harborProjects=getProjects();
        for(HarborProject harborProject:harborProjects){
            if(harborProject.getName().equals(projectName)){
                return harborProject.getProjectId();
            }
        }
        return -1L;
    }

    public boolean existProject(String projectName){
        List<HarborProject> harborProjects=getProjects();
        for(HarborProject harborProject:harborProjects){
            if(harborProject.getName().equals(projectName)){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param projectName 至少两个字符，以数字和字母开头
     */
    public ResponseEntity<String> createProject(String projectName){
        JSONObject metadata=new JSONObject(true);
        metadata.put("public",HarborConfig.ProjectMetadata.projectPublic);
        metadata.put("enable_content_trust",HarborConfig.ProjectMetadata.enableContentTrust);
        metadata.put("prevent_vul", HarborConfig.ProjectMetadata.preventVul);
        metadata.put("severity",HarborConfig.ProjectMetadata.severity);
        metadata.put("auto_scan",HarborConfig.ProjectMetadata.autoScan);

        JSONObject params=new JSONObject(true);
        params.put("project_name",projectName);
        params.put("metadata",metadata);

//        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
//        requestBody.add("project", params.toJSONString());

        HttpHeaders headers=new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<String>(params.toJSONString(), headers);
        return  restTemplate.postForEntity(harborBaseUrl+"projects", entity, String.class);
    }

    public Boolean deleteProject(Long projectId){
        restTemplate.delete(harborBaseUrl + "projects/" + projectId);
        return true;
    }

    public void deleteProject(String projectName){
        Long projectId=getProjectId(projectName);

        List<HarborRepository> harborRepositories=getRepositories(projectId);
        for(HarborRepository harborRepository:harborRepositories){
            deleteRepository(harborRepository.getName());
        }
        restTemplate.delete(harborBaseUrl+"projects/"+projectId);
    }

    public List<HarborRepository> getAllRepositories(){
        List<HarborRepository> harborRepositories=new ArrayList<>();
        List<HarborProject> harborProjects=getProjects();
        for(HarborProject harborProject:harborProjects){
            harborRepositories.addAll(getRepositories(harborProject.getProjectId()));
        }

        return harborRepositories;
    }

    public List<HarborRepository> getRepositories(Long projectId, Integer pageNo, Integer pageSize){
        String result=restTemplate.getForObject(
                harborBaseUrl+"repositories?project_id="+projectId+"&page="+pageNo+"&page_size="+pageSize,String.class);
        return JSONArray.parseArray(result, HarborRepository.class);
    }

    public List<HarborRepository> getRepositories(Long projectId){
        String result=restTemplate.getForObject(harborBaseUrl+"repositories?project_id="+projectId,String.class);
//        System.out.println(result);
        return JSONArray.parseArray(result, HarborRepository.class);
//        String result=restTemplate.getForObject(harborBaseUrl+"projects",String.class);
//        return JSONArray.parseArray(result, HarborProject.class);
    }

    public List<HarborRepository> getRepositories(String projectName){
        Long projectId=getProjectId(projectName);
        return getRepositories(projectId);
    }

    public HarborRepository getRepository(String repoName){
        String projectName=repoName.split("/")[0];
        List<HarborRepository> harborRepositories=getRepositories(projectName);
        for(HarborRepository harborRepository:harborRepositories){
            if(harborRepository.getName().equals(repoName)){
                return harborRepository;
            }
        }
        return null;
    }

    public Boolean existRepository(String projectName,String repoName){
        if(!existProject(projectName))
            return false;
        List<HarborRepository> harborRepositories=getRepositories(projectName);
        for(HarborRepository harborRepository:harborRepositories){
            if(harborRepository.getName().equals(projectName+"/"+repoName)){
                return true;
            }
        }
        return false;
    }

    public HarborTag getLastTag(String projectName,String repoName){
        List<HarborTag> tags=getTags(projectName,repoName);
        System.out.println("size "+tags.size());
        HarborTag result=tags.get(0);
        for(HarborTag tag:tags){
            System.out.println(tag.getCreatedDate().getTime()>result.getCreatedDate().getTime());
            if(tag.getCreatedDate().after(result.getCreatedDate())){
                result=tag;
            }
        }
        return result;
    }

    public Boolean deleteRepository(String repoName){
        restTemplate.delete(harborBaseUrl + "repositories/" + repoName);
        return true;
    }

    public Boolean deleteRepository(String projectName,String repoName) throws InterruptedException {
        if (existRepository(projectName, repoName))
            deleteRepository(projectName + "/" + repoName);
        while (existRepository(projectName, projectName + "/" + repoName)) {
            sleep(500);
        }
        return true;
    }


    public List<HarborTag> getTags(String projectName,String repoName){
        String result=restTemplate.getForObject(harborBaseUrl+"repositories/"+projectName+"/"+repoName+"/tags",String.class);
//        System.out.println(result);
        return JSONArray.parseArray(result, HarborTag.class);
    }

    public Boolean deleteTag(String projectName,String repoName,String tag){
        try{
            restTemplate.delete(harborBaseUrl+"repositories/"
                    +projectName+"/"+repoName+"/tags/"+tag);
            return true;
        }catch (Exception e){
            return false;
        }
    }


    public Boolean existTag(String projectName,String repoName,String tag){
        List<HarborTag> tags=getTags(projectName,repoName);
        for(HarborTag t:tags){
            if(t.getName().equals(tag))
                return true;
        }
        return false;
    }

    /**
     ​/repositories​/{repo_name}​/tags
        Retag an image
     */
    public Boolean reTag(String projectName,String repoName,String tag,String srcImage) throws InterruptedException {
        String pathRepo=projectName + "/" + repoName;

        if(!existProject(projectName))
            createProject(projectName);
        while (!(existProject(projectName))) {
            sleep(500 );
        }

        JSONObject metadata=new JSONObject(true);
        metadata.put("public",HarborConfig.ProjectMetadata.projectPublic);
        metadata.put("enable_content_trust",HarborConfig.ProjectMetadata.enableContentTrust);
        metadata.put("prevent_vul",HarborConfig.ProjectMetadata.preventVul);
        metadata.put("severity",HarborConfig.ProjectMetadata.severity);
        metadata.put("auto_scan",HarborConfig.ProjectMetadata.autoScan);

        JSONObject params=new JSONObject(true);
        params.put("tag",tag);
        params.put("src_image",srcImage);
        params.put("override",true);

//        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
//        requestBody.add("project", params.toJSONString());

        HttpHeaders headers=new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<String>(params.toJSONString(), headers);
//        return  restTemplate.postForEntity(harborBaseUrl+"projects", entity, String.class);
        Integer code=restTemplate.postForEntity(harborBaseUrl+"repositories/"+pathRepo+"/tags",entity,String.class)
                .getStatusCode().value();
        if(code==200)
            return true;
        return false;
    }

//    public HarborStatistics getStatistics(){
//        HarborStatistics result=restTemplate.getForObject(harborBaseUrl+"statistics",
//                HarborStatistics.class);
//        return result;
//    }
}
