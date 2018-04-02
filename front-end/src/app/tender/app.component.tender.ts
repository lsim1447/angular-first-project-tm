import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Tender }    from './app.model.tender';

import { TenderService} from './app.service.tender';

@Component({
  selector: 'app-tender-form',
  templateUrl: './app.component.tender.html',
  styleUrls: ['./tender.scss']
})

export class TenderComponent implements OnInit{

  model = new Tender(null, "Lazar Levente", "", "2018-01-01", 0, "Write something");
  submitted = false;
  onSubmit() { 
    this.submitted = true; 
  }

  saveButtonClicked(): void{
    this.saveTender(this.model);
    window.location.reload();
  }

  resetModel(): void {
    this.model.tender_id = null;
    this.model.choosed_date = "2018-01-01";
    this.model.total = 0;
    this.model.recipient_name = "";
  }
  promiseBooks: Promise<Tender[]>
    tenders: Tender[];
  errorMessage: String;

  constructor(private tenderService: TenderService) {

  }
  
  ngOnInit(): void {
     
  }

  saveTender(tender): void {
    this.tenderService.saveTender(tender)
      .then( tender => {
          //console.log(tender);					 
       },
      error => this.errorMessage = <any>error);
  }   
   
  // TODO: Remove this when we're done
  //get diagnostic() { return JSON.stringify(this.model); }
}