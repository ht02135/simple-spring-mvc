package x.y.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import x.y.mapper.CourseMapper;
import x.y.model.Course;

@Service
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Transactional
    public Course addCourse(String title) {
        Course course = new Course();
        course.setTitle(title);
        courseMapper.insert(course);
        return course;
    }

    @Transactional(readOnly = true)
    public Course getCourse(Long id) {
        return courseMapper.findById(id);
    }
}