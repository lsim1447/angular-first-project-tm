package com.silerv.tendermaker.lazar.controller;

import com.silerv.tendermaker.lazar.model.Detail;
import com.silerv.tendermaker.lazar.model.Task;
import com.silerv.tendermaker.lazar.model.Tender;
import com.silerv.tendermaker.lazar.service.DetailService;
import com.silerv.tendermaker.lazar.service.TaskService;
import com.silerv.tendermaker.lazar.service.TenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created by lazar on 2017. 12. 01..
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/task")
public class TaskController {
    private TaskService taskService;
    private DetailService detailService;
    private TenderService tenderService;

    @Autowired
    public TaskController(TaskService taskService, DetailService detailService, TenderService tenderService){
        this.taskService = taskService;
        this.tenderService = tenderService;
        this.detailService = detailService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Task> getAll() {
        return taskService.findAll();
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@RequestBody Task task){
        ArrayList<Detail> details = (ArrayList<Detail>) detailService.findAll();

        for (Detail detail: details) {
            if (detail.getTask_Id() == task.getTask_id()){
                Tender tender = new Tender();
                tender.setTender_id(detail.getTenderId());
                tender.setOwn_name(detail.getTender().getOwn_name());
                tender.setRecipient_name(detail.getTender().getRecipient_name());
                tender.setChoosed_date(detail.getTender().getChoosed_date());
                tender.setTotal(detail.getTender().getTotal() - detail.getPrice());
                tender.setDescription(detail.getTender().getDescription());
                this.tenderService.save(tender);
                this.detailService.delete(detail);
            }
        }
        this.taskService.delete(task);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Task save(@RequestBody Task task){
        return taskService.save(task);
    }
}
