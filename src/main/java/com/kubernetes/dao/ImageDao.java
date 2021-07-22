package com.kubernetes.dao;

import com.kubernetes.entity.NameGenerate;
import com.wwx.entity.TImage;

import java.util.List;

public interface ImageDao {
    TImage getImage(NameGenerate nameGenerate);
    void save(TImage image);
    List<TImage> getImageByNetwork(String network);
}
