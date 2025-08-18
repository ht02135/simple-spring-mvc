package x.y.service;

import x.y.dao.CourseDao;
import x.y.model.Course;

public class CourseService {

    private CourseDao courseDao;

    public void setCourseDao(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    // Transactional method
    public Long addCourse(Course course) {
        return courseDao.insertCourse(course);
    }

    // Read-only transactional method
    public Course getCourse(Long id) {
        return courseDao.findCourseById(id);
    }
}