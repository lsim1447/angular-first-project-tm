package com.silerv.tendermaker.lazar.service;

import com.silerv.tendermaker.lazar.model.Task;
import com.silerv.tendermaker.lazar.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lazar on 2017. 12. 01..
 */
@Service
public class TaskService {
    private TaskRepository repository;

    @Autowired
    public TaskService(TaskRepository repository){
        this.repository = repository;
    }

    public Iterable<Task> findAll(){ return  repository.findAll();}

    public Task save(Task task){ return  repository.save(task); }

    public void delete(Task task) { repository.delete(task);}

    public void delete(Long id){ repository.delete(id);}
}
