package services;

import db.StudentRepository;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import models.StudentSS;

import java.util.List;

public class StudentService extends Service<List<StudentSS>> {
    StudentRepository studentRepository = new StudentRepository();

    @Override
    protected Task<List<StudentSS>> createTask() {

        Task myTask = new Task<List<StudentSS>>() {
            @Override
            protected List<StudentSS> call() throws Exception {
                Thread.sleep(5000);
                return studentRepository.showStudentInfo();
            }
        };
        return myTask;
    }
}
