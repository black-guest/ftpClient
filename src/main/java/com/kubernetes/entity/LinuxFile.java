package com.kubernetes.entity;

public class LinuxFile {
    private String source;
    private String fileName;
    private String lastModifyTime;
    private String fileType;
    private Integer fileSize; //单位byte

    public LinuxFile(String source){
        this.source=source;
        String[] item=source.split(" ");

        this.fileName=item[8];
        this.lastModifyTime=item[5]+" "+item[6]+" "+item[7];
        this.fileSize=Integer.valueOf(item[4]);
        if(item[0].startsWith("d")){
            this.fileType="dir";
        }else if(fileName.contains(".")){
            String[] temp=fileName.split("\\.");
            System.out.println(temp.length);
            this.fileType=temp[temp.length-1];
        }else{
            this.fileType="";
        }
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "LinuxFile{" +
                "source='" + source + '\'' +
                ", fileName='" + fileName + '\'' +
                ", lastModifyTime='" + lastModifyTime + '\'' +
                ", fileType='" + fileType + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }
}
