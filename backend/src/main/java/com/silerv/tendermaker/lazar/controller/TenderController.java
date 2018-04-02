package com.silerv.tendermaker.lazar.controller;

import com.silerv.tendermaker.lazar.model.Detail;
import com.silerv.tendermaker.lazar.model.Tender;
import com.silerv.tendermaker.lazar.service.DetailService;
import com.silerv.tendermaker.lazar.service.TenderService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created by lazar on 2017. 11. 30..
 */

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/tender")
public class TenderController {
    private TenderService service;
    private DetailService detailService;

    public TenderController(TenderService service, DetailService detailService){
        this.service = service;
        this.detailService = detailService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Tender> getAll() {
        return service.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Tender save(@RequestBody Tender tender){
        return  service.save(tender);
    }

    @RequestMapping(path = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public long delete(@RequestBody Tender tender){
        Iterable<Detail> tmp = detailService.findAll();
        for (Detail detail : tmp){
            if (detail.getTender().getTender_id() == tender.getTender_id()){
                detailService.delete(detail);
            }
        }
        service.delete(tender);
        return -1;
    }

    @RequestMapping(path = "/print", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void printToDocFile(@RequestBody Tender tender){
        Iterable<Detail> tmp = detailService.findAll();
        int counter = 0;
        for (Detail detail: tmp){
            counter++;
        }
        System.out.println("Number of tasks: " + counter);
        this.service.printToDocFile(tender, tmp);
    }
}
