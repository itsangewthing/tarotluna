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
    const suit = this.activatedRoute.snapshot.params['suit']
    const courtsrank = this.activatedRoute.snapshot.params['courtsrank']
    console.info("suit ", suit)
    console.info("courtsrank ", courtsrank)
    if(suit) {
      this.tarotSvc.getCardsBySuit(suit)
      .then(result=>{
        console.info(suit)
        this.cards.push(...result)
      })
      .then(result=>{
        this.tarotSvc.getCardsByCourtsRank(suit)
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

    } else if(courtsrank) {
        this.tarotSvc.getCardsByCourtsRank(courtsrank)
        .then(result=>{this.cards.push(...result)})
        .then(result=>{result.cards.forEach(v=> {v.user = true})
        this.cards.push(...result)
        this.isLoading = false;
        }).catch(error=>{
          console.error(">>> error:", error)
          this.isLoading=false;
        }).catch(error=>{
          this.isLoading = false;
        })
      return;
    }

    // query from search
    this.query = localStorage.getItem("cards") ?? ''
    if (this.query) {
      this.tarotSvc.getAllCards(this.query)
        .then((result: any) => {
          console.log(result)
          this.cardList = result
        })
        .then(result => {
          this.tarotSvc.searchCardsByName(this.query)
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
        .catch((error: any) => {
          console.error(">>> error: ", error)
          this.isLoading = false;
        })
    } else {
      this.router.navigate(['/search'])
    }
    localStorage.removeItem('query')
  }

}
