import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule} from '@angular/forms'
import { HttpModule } from '@angular/http';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { TaskComponent} from './task/app.component.task';
import { TenderComponent} from './tender/app.component.tender';
import { PageNotFoundComponent} from './notfound/app.component.pagenotfound';
import { HeaderComponent} from './header/header.component';
import { FooterComponent} from './footer/footer.component';

import { CompileTenderComponent} from './compile_tender/app.component.compile_tender';
import { HomeComponent} from './home/app.component.home';

import { TenderService} from './tender/app.service.tender';
import { TaskService} from './task/app.service.task';
import { CompileTenderService}   from './compile_tender/app.service.compile_tender';

const appRoutes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'new_tender', component: TenderComponent },
  { path: 'new_task', component: TaskComponent },
  { path: 'compile_tender', component: CompileTenderComponent },
  { path: '**', component: PageNotFoundComponent },
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    TaskComponent,
    TenderComponent,
    HomeComponent,
    PageNotFoundComponent, 
    CompileTenderComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule, 
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true } // <-- debugging purposes only
    )
  ],
  providers: [ TenderService, TaskService, CompileTenderService],
  bootstrap: [AppComponent]
})
export class AppModule { }
