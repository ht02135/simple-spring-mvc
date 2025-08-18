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

    @PostMapping
    public Course create(@RequestParam String title) {
        return courseService.addCourse(title);
    }

    @GetMapping("/{id}")
    public Course get(@PathVariable Long id) {
        return courseService.getCourse(id);
    }
}