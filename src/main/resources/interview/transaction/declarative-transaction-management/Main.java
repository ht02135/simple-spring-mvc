package x.y;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import x.y.model.User;
import x.y.model.Course;
import x.y.service.UserService;
import x.y.service.CourseService;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        UserService userService = (UserService) ctx.getBean("userService");
        CourseService courseService = (CourseService) ctx.getBean("courseService");

        // Test UserService
        User user = new User(null, "Alice");
        Long userId = userService.registerUser(user);
        System.out.println("User created with ID: " + userId);

        userService.getUser(userId);

        // Test CourseService
        Course course = new Course(null, "Spring Framework Basics");
        Long courseId = courseService.addCourse(course);
        System.out.println("Course created with ID: " + courseId);

        courseService.getCourse(courseId);
    }
}