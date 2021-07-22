package com.kubernetes.dao;

import com.kubernetes.entity.NameGenerate;
import com.wwx.entity.TVm;

public interface VmDao {
    TVm getVm(NameGenerate nameGenerate);
}
