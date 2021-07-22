package com.kubernetes.service;

import com.kubernetes.config.HarborConfig;
import com.kubernetes.dao.CourseDao;
import com.kubernetes.dao.TemplateDao;
import com.kubernetes.dao.VmDao;
import com.kubernetes.dao.impl.CourseDaoImpl;
import com.kubernetes.dao.impl.TemplateDaoImpl;
import com.kubernetes.dao.impl.VmDaoImpl;
import com.kubernetes.entity.NameGenerate;
import com.wwx.entity.TCourse;
import com.wwx.entity.TTemplate;
import com.wwx.entity.TVm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateService {
    private static final Logger LOGGER = LoggerFactory.getLogger("com.kubernetes.service.TemplateService");
    private CourseDao courseDao=new CourseDaoImpl();
    private VmDao vmDao=new VmDaoImpl();
    private TemplateDao templateDao=new TemplateDaoImpl();

    public TTemplate getTemplate(NameGenerate nameGenerate){
        TTemplate template;

        //个人虚机的情况
        if(nameGenerate.getCourseId().equals("0")) {
            TVm vm =vmDao.getVm(nameGenerate);
            template=templateDao.getTemplate(vm.getTemplateId().toString());
        }else{
            //课程虚机获取虚机模板
            TCourse course=courseDao.getCourse(nameGenerate.getCourseId());
            template=templateDao.getTemplate(course.getTemplateId().toString());
        }
        if(template == null){
            LOGGER.error("getTemplate方法中的template为Null");
        }
        return template;
    }
}
