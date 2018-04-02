import { Component, OnInit } from '@angular/core';

import { Task} from './../task/app.model.Task'
import { Tender} from './../tender/app.model.tender'
import { Detail} from './app.model.detail';

import { forEach } from '@angular/router/src/utils/collection';
import {Observable} from 'rxjs';
import 'rxjs/add/operator/map';
import swal from 'sweetalert2'; 

import { TenderService } from '../tender/app.service.tender';
import { TaskService} from './../task/app.service.task';
import { CompileTenderService} from './app.service.compile_tender';
import { FooterComponent} from './../footer/footer.component';

@Component({
    selector: 'tender-container',
    templateUrl: './app.component.compile_tender.html',
    styleUrls: ['./compile_tender.scss']
})
export class CompileTenderComponent { 

  constructor(private compileTenderService: CompileTenderService, private taskService: TaskService, private tenderService: TenderService){}

  private tasks: Task[];
  private tenders: Tender[];
  private details: Detail[] = [];
  private selectedTender = new Tender(null,"","","",0,"");
  private selectedTask = new Task(0, "", "");
  private newDetail = new Detail(null, null, null, 0, 0);
  private totalPrice: number = 0;
  
  private all_details : Detail[] = [];

  getAllTask(){
    this.tasks = [];
    this.taskService.getAll().subscribe( data => {
      this.tasks = data;
    });
  }

  getAllTender(){
    this.tenders = [];
    this.tenderService.getAll().subscribe( data => {
       this.tenders = data;
       this.selectedTender = data[0];
       this.totalPrice = data[0].total;
    })
  }
  
  deleteTender(): void {
    swal({
      title: 'Biztosan törölni szeretnéd  a teljes számlát/rendelést?',
      text: "Nem fogod majd tudni visszaállitani a régi adatokat!",
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Igen, törlés!',
      cancelButtonText: 'Nem, kilépés!',
      confirmButtonClass: 'btn btn-success',
      cancelButtonClass: 'btn btn-danger',
      buttonsStyling: false,
      reverseButtons: true
    }).then((result) => {
      if (result.value) {
        this.tenderService.deleteTender(this.selectedTender);
        this.getAllTender();
        this.getAllDetails();
        window.location.reload();
        swal(
          'Törölve!',
          'A kiválasztott sor ki lett törölve!.',
          'success'
        )
      } else if (
        // Read more about handling dismissals
        //result.dismiss === swal.DismissReason.cancel
        console.log("sasszem")
      ) {
        swal(
          'Visszavonva',
          'A kiválasztott sor sértetlen maradt.',
          'error'
        )
      }
    })
  }

  printTender(): void {
    this.saveTender();
    this.selectedTender.total = this.totalPrice;
    this.compileTenderService.printTender(this.selectedTender);
  }

  getAllDetails(){
    this.all_details = [];
    this.compileTenderService.getAll().subscribe( data => {
      this.all_details = data;
      this.setupDetails();
    })
  }
  
  getTotalPrice(): number{
      let total = 0;
      if (this.details.length == 0) return 0;
      total = this.details.map(
        function(detail){ 
          //console.log(detail);
          return detail.price;
        }
      ).reduce(
        function(a,b){
          return Number(a) + Number(b);
        }
      );
      return total; 
  }

  addToList(): void {
    let tempTask = new Task(this.selectedTask.task_id, this.selectedTask.name, this.selectedTask.unit);
    let tempTender = new Tender(this.selectedTender.tender_id, this.selectedTender.own_name, this.selectedTender.recipient_name, 
                                this.selectedTender.choosed_date, this.selectedTender.total, this.selectedTender.description);
    let tempDetail = new Detail(null, tempTender,tempTask, this.newDetail.quantity, this.newDetail.price);
    this.details.push(tempDetail);
    this.totalPrice = this.getTotalPrice();
  }

  saveTender():void {
    this.compileTenderService.saveDetails(this.details);
    let tempTender = new Tender(this.selectedTender.tender_id, this.selectedTender.own_name, this.selectedTender.recipient_name, 
                                this.selectedTender.choosed_date, this.totalPrice, this.selectedTender.description);
    this.tenderService.saveTender(tempTender);
  }
  
  setupDetails(){
    let s_t_id = this.selectedTender.tender_id;
    let tmp = this.all_details.map(function(detail){ if (detail.tender.tender_id == s_t_id) return detail; else return null; });
    let tmp2: Detail[] = [];
    tmp.map(function(detail){ if (detail != null) tmp2.push(detail); });
    this.details = tmp2;
    this.totalPrice = this.getTotalPrice();
  }

  selectedTenderChanged(): void {
    this.getAllDetails();
  }

  showRowData(detail){
     console.log(detail);
  }

  deleteDetail(detail){
    swal({
      title: 'Biztosan törölni szeretnéd?',
      text: "Nem fogod majd tudni visszaállitani a régi adatokat!",
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Igen, törlés!',
      cancelButtonText: 'Nem, kilépés!',
      confirmButtonClass: 'btn btn-success',
      cancelButtonClass: 'btn btn-danger',
      buttonsStyling: false,
      reverseButtons: true
    }).then((result) => {
      if (result.value) {
        this.details = this.details.map(function(det){ 
          if (det != detail) 
            return det; 
            else return null; 
          }
       );
      let newTotal: number = 0;
      let tmp2: Detail[] = [];
      this.details.map(function(detail){ 
          if (detail != null) {
            tmp2.push(detail); 
            newTotal = newTotal + detail.price;
          }
      });
      this.details = tmp2;
      this.totalPrice = this.getTotalPrice();
        swal(
          'Törölve!',
          'A kiválasztott sor ki lett törölve!.',
          'success'
        )
      } else if (
        // Read more about handling dismissals
        //result.dismiss === swal.DismissReason.cancel
        console.log("sasszem")
      ) {
        swal(
          'Visszavonva',
          'A kiválasztott sor sértetlen maradt.',
          'error'
        )
      }
    })
  }


  ngOnInit(): void {
     this.getAllTask();
     this.getAllTender();
     this.getAllDetails();
  }
}