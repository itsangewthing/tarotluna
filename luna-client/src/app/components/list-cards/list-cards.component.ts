import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TarotService } from 'src/app/services/tarot.service';

@Component({
  selector: 'app-list-cards',
  templateUrl: './list-cards.component.html',
  styleUrls: ['./list-cards.component.css']
})
export class ListCardsComponent {
  query!: string
  isLoading: boolean = true;
  cardList: any;
  cards: any;

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private tarotSvc: TarotService) { }

  ngOnInit(): void {
    // query from links
    const suite = this.activatedRoute.snapshot.params['suite']
    const courts = this.activatedRoute.snapshot.params['courts']
    console.info("suite ", suite)
    console.info("courts ", courts)
    if(suite) {
      this.tarotSvc.getCardsByCategory(suite)
      .then(result=>{
        console.info(suite)
        this.cards.push(...result)
      })
      .then(result=>{
        this.tarotSvc.getCardsByCategory(suite)
        .then(result=>{
          result.forEach(v=>{
            v.user = true
          })
          this.cards.push(...result)
          this.isLoading = false;
        })
        .catch(error=>{
          console.error(">>> error: ", error)
          this.isLoading = false;
        })
      })
      .catch(error=>{
        console.error(">>> error: ", error)
        this.isLoading = false;
      })

      return;

    } 

    // query from search
    this.query = localStorage.getItem('query') ?? ''
    if (this.query) {
      this.tarotSvc.get(this.query)
        .then(result => {
          console.log(result)
          this.cardList = result
        })
        .then(result => {
          this.tarotSvc.getCardsByName(this.query)
          .then(result => {
            result.forEach(v=>{
              v.user = true
            })
            this.cardList.push(...result)
            this.isLoading = false;
          })
          .catch(error=>{
            console.error(">>> error: ", error)
            this.isLoading = false;
          })
        })
        .catch(error => {
          console.error(">>> error: ", error)
          this.isLoading = false;
        })
    } else {
      this.router.navigate(['/search'])
    }
    localStorage.removeItem('query')
  }

}
