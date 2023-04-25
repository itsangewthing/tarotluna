import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Card } from 'src/app/models/card';
import { CardList } from 'src/app/models/cardlist';
import { AccountService } from 'src/app/services/account.service';
import { TarotService } from 'src/app/services/tarot.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
onAddReadings() {
throw new Error('Method not implemented.');
}
onRemoveReadings(_t121: number) {
throw new Error('Method not implemented.');
}

  isLoggedIn: boolean = false;
  cardLists: CardList[] = []
  isEditing: boolean = false;
  form!: FormGroup
  card!: FormArray
  types: string[] = ['Major']
  username!: string
  isLoading: boolean = true;
  savedreadings!: FormGroup<any>;
  cardsArray: any;


  constructor(private router: Router, private tarotSvc: TarotService, private fb: FormBuilder, private accSvc:AccountService) { }


  ngOnInit(): void {
          const sessionId = localStorage.getItem("sessionId") ?? ''
          this.accSvc.authSession(sessionId)
          .then(result=>{
            this.accSvc.isLoggedIn = this.isLoggedIn = true
            this.accSvc.userLoggedIn = result.data
            this.isLoading = false;

            this.username = this.accSvc.userLoggedIn?.username ?? ''
            this.tarotSvc.getRandomCard()
            .then(result=>{
              this.randomCard = result;
            })
            .then(result=>{
              this.tarotSvc.getCardLists()
              .then(result=>{
                this.cardLists = result;
              })
              .catch(error=>{
                console.error(">>> error: ", error)
              })
            })
            .catch(error=>{
              console.error(">>> error: ", error)
            })
        
            this.updateCardList()
          })
          .catch(error=>{
            this.router.navigate(['/'])
            console.error("error >>>> ", error)
          })

          this.form = this.createForm()
        }

        onEdit(CardListId: string) {
          this.isEditing = true
          this.isLoading = true
          this.tarotSvc.getCardLists(CardListId)
          .then(result=>{
            console.info(">>> result: ", result)
            this.form = this.createForm(result)
            this.isLoading = false
          })
          .catch(error=>{
            console.error(">>> error: ", error)
            this.isLoading = false
          })
        }
      
        private createForm(c?: CardList) : FormGroup {
          this.createCardListItems(c?.card, c?.['cardlists'])
          return this.fb.group({
            cardListId: this.fb.control<string>(c?.cardList || ''),
            name: this.fb.control<string>(c?.name || '', [Validators.required]),
            cardLists: this.fb.control<string>(c?.cardList)
          })
        }
      
        private createCardListItems(card: string[] = [], cardList: string[] = []) {
          this.cardsArray = this.fb.array([])
          for(let i = 0; i < this.cardLists.length; i++) {
            this.cardsArray.push(this.createCardListItems(cards[i], cardList[i]))
          }
        }
      
        private createCardListItems(i: string, m: string) {
          return this.fb.group({
            card: this.fb.control<string>(m, [Validators.required]),
            cardList: this.fb.control<string>(i, [Validators.required])
          })
        }
      
        onAddIngredient() {
          this.cardsArray.push(this.createCardListItems('',''))
        }
      
        onRemoveIngredient(idx: number) {
          this.cardsArray.removeAt(idx)
        }
      
        onDelete(cardListId: string) {
          if(confirm('Are you sure?')) {
            console.info(">>> onDelete: ", cardListId)
            this.isLoading = true
            this.tarotSvc.deleteCardListById(cardListId, this.accSvc.userLoggedIn?.email ?? '')
            .then(result=>{
              console.info(">>> result ", result)
              this.updateCardList()
            })
            .catch(error=>{
              console.error(">>> error ", error)
              this.isLoading = false
            })
          }
        }
      
        cancelEdit() {
          this.form.reset()
          this.isEditing = false
        }
      
        onSubmitEdit() {
          const formData = new FormData()
          const cardsArray: string[] = []
          const cardsListsArray: string[] = []
          for(let i = 0; i < this.form.get('cards')?.value.length; i++) {
            cardsArray.push(this.form.get('cardList')?.value[i].cards)
            cardsListsArray.push(this.form.get('cardLists')?.value[i].cardList)
          }
      
          formData.set('cardsListId', this.form.get('cardsListId')?.value)
          formData.set('name', this.form.get('name')?.value)
          formData.set('cardsArray', cardsArray.join(','))
          formData.set('cardsListArray', cardsListsArray.join(','))
          formData.set('email', this.accSvc.userLoggedIn?.email ?? '')
      
          this.isLoading = true;
      
          this.tarotSvc.editCardList(formData)
          .then(result=>{
            console.log(">>> result: ", result)
            this.form.reset()
            this.updateCardList()
            this.isEditing = false;
          })
          .catch(error=>{
            console.log(">>> error: ", error)
            this.isLoading = false;
          })
        }
      
        private updateCardList() {
          this.tarotSvc.getCardListByEmail(this.accSvc.userLoggedIn?.email ?? '')
          .then(result=>{
            this.cardLists = result
            this.cardLists.forEach(v=>{
              v.user = true
            })
            this.isLoading = false;
          })
          .catch(error=>{
            this.router.navigate(['/notFound'])
            this.isLoading = false;
          })
        }

}
