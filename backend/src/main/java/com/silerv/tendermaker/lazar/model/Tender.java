package com.silerv.tendermaker.lazar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by lazar on 2017. 12. 01..
 */
@Entity
@Data
@NoArgsConstructor
public class Tender {
    @Id
    @SequenceGenerator(name = "seq_gen")
    @GeneratedValue(generator = "seq_gen")
    private long tender_id;

    @NotNull
    private String own_name;

    @NotNull
    private String recipient_name;

    @NotNull
    private String choosed_date;

    private double total;

    @NotNull
    private String description;

    @OneToMany(mappedBy = "tender")
    @JsonIgnore
    private Set<Detail> details;

    public long getTender_id() {
        return tender_id;
    }

    public void setTender_id(long tender_id) {
        this.tender_id = tender_id;
    }

    public String getOwn_name() {
        return own_name;
    }

    public void setOwn_name(String own_name) {
        this.own_name = own_name;
    }

    public String getRecipient_name() {
        return recipient_name;
    }

    public void setRecipient_name(String recipient_name) {
        this.recipient_name = recipient_name;
    }

    public String getChoosed_date() {
        return choosed_date;
    }

    public void setChoosed_date(String choosed_date) {
        this.choosed_date = choosed_date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Detail> getDetails() {
        return details;
    }

    public void setDetails(Set<Detail> details) {
        this.details = details;
    }
}
