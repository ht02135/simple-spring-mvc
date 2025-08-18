package x.y.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import x.y.dao.CourseDao;
import x.y.model.Course;

@Service
public class CourseService {
    @Autowired
    private CourseDao courseDao;

    @Transactional
    public Course addCourse(String title) {
        return courseDao.save(new Course(title));
    }

    @Transactional(readOnly = true)
    public Course getCourse(Long id) {
        return courseDao.findById(id);
    }
}