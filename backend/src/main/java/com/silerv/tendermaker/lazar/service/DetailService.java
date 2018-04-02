package com.silerv.tendermaker.lazar.service;

import com.silerv.tendermaker.lazar.model.Detail;
import com.silerv.tendermaker.lazar.repository.DetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lazar on 2017. 12. 01..
 */
@Service
public class DetailService {
    private DetailRepository repository;

    @Autowired
    public DetailService(DetailRepository repository){
        this.repository = repository;
    }

    public Detail save(Detail detail){ return repository.save(detail);}

    public void save(Iterable<Detail> details){ repository.save(details);}

    public Iterable<Detail> findAll(){ return  repository.findAll();}

    public void delete(Long id){ repository.delete(id); }

    public void delete(Detail detail){ repository.delete(detail);}

    public void delete(Iterable<Detail> details, long tenderId){
        for (Detail detail: details) {
            if (detail.getTenderId() == tenderId) repository.delete(detail);
        }
    }
}
