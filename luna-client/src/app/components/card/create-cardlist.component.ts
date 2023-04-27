import { Component, OnInit } from '@angular/core';
import { AccountService } from 'src/app/services/account.service';
import { TarotService } from 'src/app/services/tarot.service';
import { Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';




@Component({
  selector: 'app-create-cardlist',
  templateUrl: './create-cardlist.component.html',
  styleUrls: ['./create-cardlist.component.css']
})

export class CreateCardlistComponent implements OnInit{

  tarotSvc!: TarotService
  accSvc!: AccountService

  isLoggedIn: boolean = false
  isLoading: boolean = true
  areas: any;
  router!: Router;
  

  onstructor(private fb: NewType, private tarotSvc: TarotService, private router: Router, private accSvc: AccountService) { }


  ngOnInit(): void {
    const sessionId = localStorage.getItem("sessionId") ?? ''
    this.tarotSvc.authSession(sessionId)
    .then(result=>{
      this.tarotSvc.isLoggedIn = this.isLoggedIn = true
      this.accSvc.userLoggedIn = result.data
      this.isLoading = false;

      this.tarotSvc.getAllCards()
      .then((result: any)=>{
        this.areas = result;
      })
      .catch((error: any)=>{
        console.error(">>> error: ", error)
      })
  
    })
    .catch(error=>{
      this.router.navigate(['/'])
      console.error("error >>>> ", error)
    })

    this.form = this.createForm()
  }

}
