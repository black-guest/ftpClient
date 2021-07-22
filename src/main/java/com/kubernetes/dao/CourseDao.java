package com.kubernetes.dao;

import com.wwx.entity.TCourse;

public interface CourseDao {
    TCourse getCourse(String courseId);
}
