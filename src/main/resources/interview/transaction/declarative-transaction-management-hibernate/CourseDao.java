package x.y.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import x.y.model.Course;

@Repository
public class CourseDao {
    @PersistenceContext
    private EntityManager em;

    public Course save(Course course) {
        em.persist(course);
        return course;
    }

    public Course findById(Long id) {
        return em.find(Course.class, id);
    }
}