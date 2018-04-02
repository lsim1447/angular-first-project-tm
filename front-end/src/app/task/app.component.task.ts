import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Task}    from './app.model.Task';
import { TaskService} from './app.service.task';
import { take } from 'rxjs/operator/take';
import swal from 'sweetalert2'; 

@Component({
  selector: 'app-newtask-form',
  templateUrl: './app.component.task.html',
  styleUrls: ['./Task.scss']
})

export class TaskComponent {

  units = ['kg', 'fm', 'm', 'm²', 'm³', '-'];
  model = new Task(null, "", "");
  submitted = false;
  errorMessage: String;

  tasks: Task[] = [];

  onSubmit() { this.submitted = true; }

  resetTask() : void {
    this.model.task_id = null;
    this.model.name = "";
    this.model.unit = "";
  }
  
  deleteTask(task){
    swal({
      title: 'Biztosan törölni szeretnéd  a kiválasztott munkálatot?',
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
        this.taskService.deleteTask(task);
        let tmpTasks: Task[] = [];
        this.tasks.map(function(t){
            if (t.task_id != task.task_id) tmpTasks.push(t);
        })
        this.tasks = tmpTasks;
        swal(
          'Törölve!',
          'A kiválasztott munka törölve lett!.',
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

  saveTask(): void{
    this.taskService.addNewTask(this.model);
    let tmp: Task = new Task(Math.floor(Math.random() * 9999), this.model.name, this.model.unit);
    this.tasks.push(tmp);
    this.resetTask();
  }

  getAllTask(){
    this.tasks = [];
    this.taskService.getAll().subscribe( data => {
      this.tasks = data;
    });
  }

  constructor(private taskService: TaskService) {}
  
  ngOnInit(): void {
    this.getAllTask();
  }
  // TODO: Remove this when we're done
  //get diagnostic() { return JSON.stringify(this.model); }
}