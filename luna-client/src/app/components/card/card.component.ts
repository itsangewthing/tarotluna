import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Card } from 'src/app/models/card';
import { CardList } from 'src/app/models/cardlist';
import { AccountService } from 'src/app/services/account.service';
import { TarotService } from 'src/app/services/tarot.service';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit {

  card!: Card
  isLoading: boolean = true;

  constructor(private activatedRoute: ActivatedRoute, private tarotSvc: TarotService, private router: Router, private accSvc:AccountService) { }

  ngOnInit(): void {
    const card: string[] = this.activatedRoute.snapshot.params['card']
    const username: string = this.activatedRoute.snapshot.params['user']
    if (username) {
      this.tarotSvc.getRandomCard(this.card)
      .then(result => {
        this.card = result
        this.card.user = true
        this.isLoading = false;
      })
      .catch(error => {
        this.router.navigate(['/notFound'])
      })
    } else {
      this.tarotSvc.getRandomCard(this.card)
        .then(result => {
          this.card = result
          this.isLoading = false;
        })
        .catch(error => {
          this.isLoading = false;
          this.router.navigate(['/notFound'])
        })
    }
  }

}