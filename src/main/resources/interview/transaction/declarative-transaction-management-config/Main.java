/*
Main.java (Bootstrap with AnnotationConfig)
*/
package x.y;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import x.y.config.MySpringConfig;
import x.y.service.UserService;
import x.y.service.CourseService;
import x.y.model.User;
import x.y.model.Course;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(MySpringConfig.class, 
                                                                        x.y.service.UserService.class,
                                                                        x.y.service.CourseService.class,
                                                                        x.y.dao.UserDao.class,
                                                                        x.y.dao.CourseDao.class);

        UserService userService = ctx.getBean(UserService.class);
        CourseService courseService = ctx.getBean(CourseService.class);

        // Test UserService
        Long userId = userService.registerUser(new User(null, "Alice"));
        System.out.println("Created user with id=" + userId);
        userService.getUser(userId);

        // Test CourseService
        Long courseId = courseService.addCourse(new Course(null, "Spring Boot"));
        System.out.println("Created course with id=" + courseId);
        courseService.getCourse(courseId);
    }
}