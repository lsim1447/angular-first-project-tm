package com.silerv.tendermaker.lazar.service;

import com.silerv.tendermaker.lazar.model.Detail;
import com.silerv.tendermaker.lazar.model.Tender;
import com.silerv.tendermaker.lazar.repository.TenderRepository;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by lazar on 2017. 12. 01..
 */
@Service
public class TenderService {
    private TenderRepository repository;

    @Autowired
    public TenderService(TenderRepository repository){
        this.repository = repository;
    }

    public Tender save(Tender tender){ return  repository.save(tender);}

    public Iterable<Tender> findAll(){ return  repository.findAll();}

    public void delete(Long id){ repository.delete(id);}

    public void delete(Tender tender){ repository.delete(tender);}

    public static void printToDocFile(Tender tender, Iterable<Detail> details){
         FileOutputStream out = null;
        try {
            out = new FileOutputStream("MyTender.docx");
            XWPFDocument doc = new XWPFDocument();
            XWPFParagraph par = doc.createParagraph();
            // Title: Recipient name and description
            par.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run =  par.createRun();
            run.setBold(true);
            run.setFontSize(25);
            run.setText(tender.getRecipient_name() + ":  " + tender.getDescription());

            // Who made the tender
            par = doc.createParagraph();
            par.setAlignment(ParagraphAlignment.RIGHT);
            run =  par.createRun();
            run.setBold(false);
            run.setFontSize(20);
            run.setItalic(true);
            run.setText("Készítette: " + tender.getOwn_name());
            run.addBreak();
            run.setText(tender.getChoosed_date());
            //space under the titles
            run.addBreak();
            run.addBreak();
            par.setBorderBottom(Borders.APPLES);
            //tasks
            par = doc.createParagraph();
            par.setAlignment(ParagraphAlignment.RIGHT);
            run =  par.createRun();
            run.setFontSize(16);
            double pc = 0;
            int counter = 0;
            String tmpQuantityAndUnit = null;
            String tmpPriceStr = null;
            for (Detail detail: details){
                if (detail.getTender().getTender_id() == tender.getTender_id()){
                    ++counter;
                    run.setBold(false);
                    run.setKerning(5);
                    run.setText(detail.getTask().getName());

                    StringBuilder builder1 = new StringBuilder();
                    for (int i = 0; i < (75 - detail.getTask().getName().length()); i++){
                        builder1.append(" ");
                    }
                    run.setText(builder1.toString());


                    tmpQuantityAndUnit = detail.getQuantity() + " " + detail.getTask().getUnit();
                    run.setText(tmpQuantityAndUnit);
                    StringBuilder builder2 = new StringBuilder();
                    for (int i = 0; i < (17 - tmpQuantityAndUnit.length()); i++){
                        builder2.append(" ");
                    }
                    run.setText(builder2.toString());


                    tmpPriceStr = detail.getPrice() + " LEI";
                    StringBuilder builder3 = new StringBuilder();
                    for (int i = 0; i < (12 - tmpPriceStr.length()); i++){
                        builder3.append(" ");
                    }
                    run.setText(builder3.toString());
                    run.setText(tmpPriceStr);
                    run.addBreak();
                    pc += detail.getPrice();
                }
            }

            par = doc.createParagraph();
            par.setAlignment(ParagraphAlignment.RIGHT);
            par.setBorderTop(Borders.APPLES);
            run =  par.createRun();
            run.setFontSize(25);
            run.setText("Összesen: " + pc + " LEI");

            doc.write(out);
        } catch (Exception e){
            System.out.println("BAJ VAN BAJ");
        } finally {
            try{
                out.close();
            } catch (IOException io){

            }
        }
    }
}
