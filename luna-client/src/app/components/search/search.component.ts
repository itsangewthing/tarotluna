import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Card } from 'src/app/models/card';
import { TarotService } from 'src/app/services/tarot.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent {

    offset: number = 0
    terms!: string
  
    param$!: Subscription
    searchName!: string
    card!: Card[];
  
    constructor(private ar: ActivatedRoute, private router: Router, private tarotSvc: TarotService) {}
  
    ngOnInit() : void {
      this.param$ = this.ar.params.subscribe(
        (params) => {
          this.searchName = params['searchName']
  
          console.log(params)
      // submit -> retrieve product(s)
      this.tarotSvc.searchCardsByName(this.searchName)
        .then(result =>{
          this.card = result
          console.log(this.card)
        }).catch(error => {
          console.log(error)
        })
      }
      )
    }
  }
  

