package com.silerv.tendermaker.lazar.controller;

import com.silerv.tendermaker.lazar.model.Detail;
import com.silerv.tendermaker.lazar.model.Tender;
import com.silerv.tendermaker.lazar.service.DetailService;
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
@RequestMapping("/detail")
public class DetailController {
    private DetailService service;
    private TenderService tenderService;
    @Autowired
    public DetailController(DetailService service, TenderService tenderService){
        this.service = service;
        this.tenderService = tenderService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Detail> getAll() {
        ArrayList<Detail> tmp = (ArrayList<Detail>) service.findAll();
        //System.out.println(tmp.size());
        return tmp;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public int save(@RequestBody ArrayList<Detail> details){
        try{
            Iterable<Detail> tmp = service.findAll();
            service.delete(tmp, details.get(0).getTenderId() );
            service.save(details);

            if (details.size() != 0) {
                Tender t = details.get(0).getTender();
                double pc = 0;
                for (Detail detail : details) {
                    pc += detail.getPrice();
                }
                t.setTotal(pc);
                tenderService.save(t);
            }
            return 0;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return -1;
        }
    }

}
