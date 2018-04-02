import { Injectable} from '@angular/core';
import { Http, Response} from '@angular/http';
import { Headers, RequestOptions } from '@angular/http';
import { Task} from './../task/app.model.Task';
import { Detail} from './../compile_tender//app.model.detail';

import { Observable} from 'rxjs';
import 'rxjs/add/operator/map';
import { Tender } from '../tender/app.model.tender';

@Injectable()
export class CompileTenderService{
    urlToDetail = "http://localhost:8080/detail/";
    utlToTender = "http://localhost:8080/tender/print"
    constructor(private http:Http) { }

    getAll(): Observable<Detail[]>{
        return this.http.get(this.urlToDetail).map(
            res => {
                return res.json().map( item =>{
                    return new Detail(
                        item.detail_id,
                        item.tender,
                        item.task,
                        item.quantity,
                        item.price
                    );
                });
            }
        )
   }

    saveDetails(details: Detail[]): Promise<Number> {
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });
        return this.http.post(this.urlToDetail, details, options).toPromise()
               .then(this.extractData)
               .catch(this.handleErrorPromise);
    }

    printTender(tender: Tender): Promise<Number> {
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });
        return this.http.post(this.utlToTender, tender, options).toPromise()
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