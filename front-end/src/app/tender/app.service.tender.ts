import { Injectable} from '@angular/core';
import { Http, Response} from '@angular/http';
import { Headers, RequestOptions } from '@angular/http';
import { Observable} from 'rxjs';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';

import { Tender} from './app.model.tender';

@Injectable()
export class TenderService{
    url = "http://localhost:8080/tender/";
    deleteUrl = "http://localhost:8080/tender/delete";
    constructor(private http:Http) { }

    getAll(): Observable<Tender[]>{
         return this.http.get(this.url).map(
             res => {
                 return res.json().map( item =>{
                     return new Tender(
                         item.tender_id,
                         item.own_name,
                         item.recipient_name,
                         item.choosed_date,
                         item.total,
                         item.description
                     );
                 });
             }
         )
    }

    deleteTender(choosedTender: Tender): Promise<Tender> {
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });
        return this.http.post(this.deleteUrl, choosedTender, options).toPromise()
               .then(this.extractData)
                   .catch(this.handleErrorPromise);
    }

    saveTender(tender:Tender): Promise<Tender> {
        let headers = new Headers({ 'Content-Type': 'application/json' });
            let options = new RequestOptions({ headers: headers });
            return this.http.post(this.url, tender, options).toPromise()
                   .then(this.extractData)
                       .catch(this.handleErrorPromise);
    }	
    
    
    private extractData(res: Response) {
        let body = res.json();
        return body.data || {};
    }
   
    private handleErrorPromise (error: Response | any) {
        console.error(error.message || error);
        return Promise.reject(error.message || error);
    }
}
