package com.silerv.tendermaker.lazar.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by lazar on 2017. 12. 01..
 */
@Entity
@Data
@NoArgsConstructor
public class Detail {
    @Id
    @SequenceGenerator(name = "seq_gen")
    @GeneratedValue(generator = "seq_gen")
    private long detail_id;

    @ManyToOne
    @JoinColumn(name = "tender_id")
    private Tender tender;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @NotNull
    private double quantity;

    @NotNull
    private double price;

    public long getTenderId(){
        return tender.getTender_id();
    }

    public long getDetail_id(){
        return this.detail_id;
    }

    public long getTask_Id(){ return  this.task.getTask_id();}

    public void setDetail_id(long detail_id) {
        this.detail_id = detail_id;
    }

    public Tender getTender() {
        return tender;
    }

    public void setTender(Tender tender) {
        this.tender = tender;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
