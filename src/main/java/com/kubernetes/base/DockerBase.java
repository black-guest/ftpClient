package com.kubernetes.base;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.CreateNetworkResponse;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.PullImageResultCallback;
import com.github.dockerjava.core.command.PushImageResultCallback;
import com.kubernetes.config.DockerConfig;
import com.kubernetes.config.HarborConfig;
import com.kubernetes.config.NetworkConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.github.dockerjava.api.model.HostConfig.newHostConfig;


public class DockerBase {
    private static final Logger LOGGER = LoggerFactory.getLogger("com.kubernetes.base.DockerBase");
    private DockerClient client;
    private AuthConfig authConfig;

    public DockerBase(){
        this.client=connectDocker(DockerConfig.hostIp,DockerConfig.dockerPort);
        this.authConfig=new AuthConfig().withUsername(HarborConfig.userName).withPassword(HarborConfig.password)
                .withRegistryAddress(HarborConfig.hostIp+":"+HarborConfig.port);
    }

    /**
     * 连接docker服务器
     * @return
     */
    private static DockerClient connectDocker(String hostIp, Integer port){
        String connection="tcp://"+hostIp+":"+port;
//        DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://139.9.222.244:2375").build();
        return DockerClientBuilder.getInstance(connection).build();
    }

    public List<Image> getImages(){
        return client.listImagesCmd().exec();
    }
    public Image getImage(String repository, String tag){
        String repoTag=repository+":"+tag;
        List<Image> images=getImages();
        Image target=null;
        for(Image image:images){
            if(image.getRepoTags()!=null && image.getRepoTags()[0].contains(repoTag)){
                target=image;
                break;
            }
        }
        return target;
    }

    public Image getImage(String repository){
        String repoTag=repository;
        List<Image> images=getImages();
        Image target=null;
        for(Image image:images){
            if(image.getRepoTags()!=null ) {
                System.out.println(image.getRepoTags()[0]);
            }
            if(image.getRepoTags()!=null && image.getRepoTags()[0].contains(repoTag)){
                target=image;
                break;
            }
        }
        return target;
    }


    public void pushImage(Image image) throws InterruptedException {
//        String repoTag=HarborConfig.hostIp+":"+HarborConfig.port+"/c01/"+image.getRepoTags()[0];
        System.out.println(image.getRepoTags()[0]);
        System.out.println(client.pushImageCmd(image.getRepoTags()[0])
                .withAuthConfig(authConfig)
                .exec(new PushImageResultCallback())
                .awaitCompletion());
    }

    public void pushImage(String image) throws InterruptedException {
//        String repoTag=HarborConfig.hostIp+":"+HarborConfig.port+"/c01/"+image.getRepoTags()[0];
        System.out.println(client.pushImageCmd(image)
                .withAuthConfig(authConfig)
                .exec(new PushImageResultCallback())
                .awaitCompletion());
    }

    public void pushImage(Image image,String tag) throws InterruptedException {
//        System.out.println(image.getRepoTags()[0]);
        client.pushImageCmd(image.getRepoTags()[0]).withAuthConfig(authConfig)
                .exec(new PushImageResultCallback())
                .awaitCompletion();
    }

    public void pullImage(String repository,String tag) throws InterruptedException {
        String image=HarborConfig.hostIp+":"+HarborConfig.port+repository+":"+tag;
        System.out.println(
                client.pullImageCmd(image)
                .withAuthConfig(authConfig)
                .exec(new PullImageResultCallback())
                .awaitCompletion()
        );
    }

    public void pullImage(String image) throws InterruptedException {
        client.pullImageCmd(image)
                .withAuthConfig(authConfig)
                .exec(new PullImageResultCallback())
                .awaitCompletion();
    }

    public List<Container> getContainers(){
        return client.listContainersCmd().exec();
    }


    public void commitContainer(Container container,String repository){
        client.commitCmd(container.getId()).withRepository(repository).withTag("latest").exec();
    }

    public void commitContainer(Container container,String repository,String tag){
        client.commitCmd(container.getId()).withRepository(repository).withTag(tag).exec();
    }

    /**
     * k8s中pod在docker中的命名规则为 k8s_deploymentName_podName_namespace_others
     * @param namespace
     * @param deploymentName
     * @param podName
     */
    public Container getContainer(String namespace,String deploymentName,String podName){
        List<Container> containers=getContainers();
        Container target=null;
        for(Container container:containers){
            String name=container.getNames()[0];
            String targetString="k8s_"+deploymentName+"_"+podName+"_"+namespace;
            if(name.contains(targetString)){
                target=container;
                break;
            }
        }
        return target;
    }

    public void removeImage(Image image){
        try {
            client.removeImageCmd(image.getId()).withForce(true).exec();
        }catch (Exception e){

        }

    }

    public void removeImageWithForce(Image image){
        try {
            client.removeImageCmd(image.getId()).withForce(true).exec();
        }catch (Exception e){

        }
    }

    public void removeNoneImages(){
        List<Image> images=getImages();
        for(Image image:images){
            if(image.getRepoTags()==null||image.getRepoTags()[0].contains("<none>")){
                System.out.println(image);
                removeImageWithForce(image);
            }
        }
    }

    public void removeTargetImages(Image targetImage){
        List<Image> images=getImages();
        for(Image image:images){
            if(image.getRepoTags()==null||image.getRepoTags()[0].contains("<none>")){
                System.out.println(image);
                removeImageWithForce(image);
            }else if (image.getId().equals(targetImage.getId())){
                removeImageWithForce(targetImage);
            }
        }
    }

    public void tagImage(Image image,String imageNameWithRepository,String tag){
        client.tagImageCmd(image.getId(),imageNameWithRepository,tag).exec();
    }

    public void tagImage(Image image,String imageNameWithRepository){
        client.tagImageCmd(image.getId(),imageNameWithRepository,"latest").exec();
    }

    //启动容器
    public String startContainer(String image,Integer port,String hostName
            ,String ip,String containerName){
        LOGGER.info("startContainer方法启用");
        removeContainer(containerName);
        ExposedPort exposedPort = ExposedPort.tcp(port);

        Ports portBindings = new Ports();
        portBindings.bind(exposedPort, Ports.Binding.empty());

        CreateContainerResponse createContainerResponse = client.createContainerCmd(image)
                .withHostName(hostName)
                .withExposedPorts(exposedPort).withHostConfig(newHostConfig()
                        .withPortBindings(portBindings)
                        .withNetworkMode(NetworkConfig.bridgeName)
//                        .withPublishAllPorts(true)
                )
                .withName(containerName)
                .withIpv4Address(ip)
                .exec();
        client.startContainerCmd(createContainerResponse.getId()).exec();
        Integer exposePort= getPort(containerName);

        return "tcp://localhost:"+exposePort;
    }

    public Integer getPort(String containerName){
        Container container=getContainers(containerName);
        ContainerPort[] containerPorts=container.getPorts();
        for (ContainerPort containerPort : containerPorts) {
            if (containerPort.getPublicPort() != null) {
                return containerPort.getPublicPort();
            }
        }
        LOGGER.error("getPort方法中的port为Null");
        return null;
    }

    public Container getContainers(String containerName){
        List<Container> containers= getContainers();
//        return client.listContainersCmd().exec();
        for(Container container:containers){
            System.out.println("/"+containerName);
            System.out.println(container.getNames()[0].equals("/"+containerName));
            if(container.getNames()!=null
                    && (container.getNames()[0].equals(containerName)
                    || container.getNames()[0].equals("/"+containerName))){
                return container;
            }
        }
        return null;
    }

    public Boolean existContainer(String containerName){
        List<Container> containers=client.listContainersCmd().withShowAll(true).exec();
        for(Container container:containers){
            if(container.getNames()!=null
                    && (container.getNames()[0].equals(containerName)
                    || container.getNames()[0].equals("/"+containerName))
                    && container.getState().equals("running")){
                return true;
            }
        }
        return false;
    }

    public Boolean existContainer(String containerName,String repoName){
        List<Container> containers=client.listContainersCmd().withShowAll(true).exec();
        for(Container container:containers){
            if(container.getNames()!=null
                    && (container.getNames()[0].equals(containerName)
                    || container.getNames()[0].equals("/"+containerName))
                    && container.getState().equals("running")
                    && container.getImage().equals(repoName)){
                return true;
            }
        }
        return false;
    }

    //根据容器名称删除 容器(包括容器名含有-)
    public void  removeContainer(String containerName){
        LOGGER.info("removeContainer方法,根据容器名称删除 容器(包括容器名含有-)");
       List<Container> containers=client.listContainersCmd().withShowAll(true).exec();
       for(Container container:containers){
           System.out.println(container);
           if(container.getNames()!=null
                   && (container.getNames()[0].equals(containerName)
                   || container.getNames()[0].equals("/"+containerName)
                   || container.getNames()[0].contains("-"))){
               client.removeContainerCmd(container.getId()).withForce(true).exec();
           }
       }
    }

    public void login(){}

    public List<Network> getNetworks(){
        List<Network> networks = client.listNetworksCmd().exec();
        return networks;
    }

    public Network createNetwork(String networkName,String subnet){
        Network.Ipam ipam = new Network.Ipam().withConfig(new Network.Ipam.Config().withSubnet(subnet));
        CreateNetworkResponse createNetworkResponse = client.createNetworkCmd().withName(networkName).withIpam(ipam).exec();

        Network network = client.inspectNetworkCmd().withNetworkId(createNetworkResponse.getId()).exec();
        return network;
    }

    public boolean checkNetwork(){
        List<Network> networks=getNetworks();
        for(Network network:networks){
            String name=network.getName();
            String subnet="";
            if(!network.getIpam().getConfig().isEmpty())
                subnet=network.getIpam().getConfig().get(0).getSubnet();
            System.out.println(subnet);
            if(NetworkConfig.bridgeName.equals(name)
                    && NetworkConfig.subIp.equals(subnet)) {
                return true;
            }else if(!NetworkConfig.bridgeName.equals(name)
                    && NetworkConfig.subIp.equals(subnet)) {
                removeNetwork(network);
            }else if(NetworkConfig.bridgeName.equals(name)
                    &&  !NetworkConfig.subIp.equals(subnet)){
                removeNetwork(network);
            }
        }
        createNetwork(NetworkConfig.bridgeName,NetworkConfig.subIp);
        return true;
    }

    public void  removeNetwork(Network network){
        client.removeNetworkCmd(network.getId()).exec();
    }

}
