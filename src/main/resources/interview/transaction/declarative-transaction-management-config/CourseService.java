
/*
Annotated CourseService.java
*/
package x.y.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import x.y.dao.CourseDao;
import x.y.model.Course;

import javax.annotation.Resource;

@Service
public class CourseService {

    @Resource
    private CourseDao courseDao;

    @Transactional
    public Long addCourse(Course course) {
        return courseDao.insertCourse(course);
    }

    @Transactional(readOnly = true)
    public Course getCourse(Long id) {
        return courseDao.findCourseById(id);
    }
}