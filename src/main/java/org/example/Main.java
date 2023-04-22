package org.example;

import org.example.Repository.Interface_Courses;
import org.example.Repository.Interface_Group;
import org.example.Repository.Interface_Rating;
import org.example.Repository.Interface_Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(Main.class);
        Interface_Student student = context.getBean(Interface_Student.class);

        Interface_Rating rating = context.getBean(Interface_Rating.class);
        Interface_Courses courses = context.getBean(Interface_Courses.class);
        Interface_Group group = context.getBean(Interface_Group.class);

        System.out.println();
        System.out.println("Всего обучается " + student.count() + " студентов");
        System.out.println("Всего есть " + rating.count() + " видов оценок");
        System.out.println("Студентам предоставлен выбор из " + courses.count() + " курсов");
        System.out.println("Студенты распределены по " + group.count() + " группам");

//        System.out.println(courses.getAll());
//        System.out.println(group.getAll());
//        System.out.println(rating.getAll());
//        System.out.println(student.getAll());

        System.out.println();
        System.out.println(student.Average(1));
        System.out.println(student.Average(2));
        System.out.println(student.Average(3));
        System.out.println(student.Average(4));
        System.out.println(student.Average(5));
    }
}