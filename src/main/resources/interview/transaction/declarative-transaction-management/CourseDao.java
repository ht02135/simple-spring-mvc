package x.y.dao;

import x.y.model.Course;

public class CourseDao {

    public Long insertCourse(Course course) {
        // Simulate SQL insert
        System.out.println("Inserting course into DB: " + course.getTitle());
        return 100L; // pretend generated ID
    }

    public Course findCourseById(Long id) {
        // Simulate SQL select
        System.out.println("Fetching course from DB with id=" + id);
        return new Course(id, "Sample Course");
    }
}