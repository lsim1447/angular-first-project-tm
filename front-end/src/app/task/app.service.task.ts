import { Injectable} from '@angular/core';
import { Http, Response} from '@angular/http';
import { Headers, RequestOptions } from '@angular/http';
import { Observable} from 'rxjs';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';

import { Task} from './app.model.Task';
import { Body } from '@angular/http/src/body';
import { take } from 'rxjs/operators/take';

@Injectable()
export class TaskService{
    urlToTask = "http://localhost:8080/task/";
    constructor(private http:Http) { }

    deleteTask(task: Task){
        let headers = new Headers({ 'Content-Type': 'application/json' });
        this.http.delete(this.urlToTask, new RequestOptions({
            headers: headers,
            body: task
        })).subscribe((ok)=>{console.log(ok)});
    }

    getAll():Observable<Task[]> {
        return this.http.get(this.urlToTask).map(
            res => {
                return res.json().map( item => {
                    return new Task(
                        item.task_id,
                        item.name,
                        item.unit
                    );
                });
            }
        );
    }

    addNewTask(task:Task): Promise<Task> {
        let headers = new Headers({ 'Content-Type': 'application/json' });
            let options = new RequestOptions({ headers: headers });
            return this.http.post(this.urlToTask, task, options).toPromise()
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
