/*
DAOs (no transactions here, just called by services)
*/
package x.y.dao;

import org.springframework.stereotype.Repository;
import x.y.model.Course;

@Repository
public class CourseDao {
    public Long insertCourse(Course course) {
        System.out.println("Inserting course: " + course.getTitle());
        return 100L;
    }
    public Course findCourseById(Long id) {
        System.out.println("Fetching course with id=" + id);
        return new Course(id, "Sample Course");
    }
}