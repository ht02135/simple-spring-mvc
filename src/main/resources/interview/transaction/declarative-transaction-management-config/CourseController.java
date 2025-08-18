package x.y.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import x.y.model.Course;
import x.y.service.CourseService;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // POST /courses
    @PostMapping
    public Long createCourse(@RequestBody Course course) {
        return courseService.addCourse(course);
    }

    // GET /courses/{id}
    @GetMapping("/{id}")
    public Course getCourse(@PathVariable Long id) {
        return courseService.getCourse(id);
    }
}