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

  isLoggedIn: boolean = false;
  cardLists: CardList[] = []
  isEditing: boolean = false;
  form!: FormGroup
  card!: FormArray
  types: string[] = ['Major']
  username!: string
  isLoading: boolean = true;


  constructor(private router: Router, private tarotSvc: TarotService, private fb: FormBuilder, private accSvc:AccountService) { }


  ngOnInit(): void {
          const sessionId = localStorage.getItem("sessionId") ?? ''
          this.accSvc.authSession(sessionId)
          .then(result=>{
            this.accSvc.isLoggedIn = this.isLoggedIn = true
            this.accSvc.userLoggedIn = result.data
            this.isLoading = false;

            this.username = this.accSvc.userLoggedIn?.username ?? ''
            this.mealDbSvc.getAllAreas()
            .then(result=>{
              this.areas = result;
            })
            .then(result=>{
              this.mealDbSvc.getAllCategories()
              .then(result=>{
                this.categories = result;
              })
              .catch(error=>{
                console.error(">>> error: ", error)
              })
            })
            .catch(error=>{
              console.error(">>> error: ", error)
            })
        
            this.updateRecipes()
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
      
        private createForm(c?: Card) : FormGroup {
          this.createIngredientItems(c?.card, c?.cardlist)
          return this.fb.group({
            cardListId: this.fb.control<string>(c?.cardListId || ''),
            name: this.fb.control<string>(c?.name || '', [Validators.required]),
            category: this.fb.control<string>(c?.category || '', [Validators.required]),
            area: this.fb.control<string>(c?.country || '', [Validators.required]),
            instructions: this.fb.control<string>(c?.instructions || '', [Validators.required]),
            youtubeLink: this.fb.control<string>(c?.youtubeLink || ''),
            Card: this.ingredientsArray
          })
        }
      
        private createIngredientItems(ingredients: string[] = [], measurements: string[] = []) {
          this.ingredientsArray = this.fb.array([])
          for(let i = 0; i < ingredients.length; i++) {
            this.ingredientsArray.push(this.createIngredientItem(ingredients[i], measurements[i]))
          }
        }
      
        private createIngredientItem(i: string, m: string) {
          return this.fb.group({
            measurement: this.fb.control<string>(m, [Validators.required]),
            ingredient: this.fb.control<string>(i, [Validators.required])
          })
        }
      
        onAddIngredient() {
          this.ingredientsArray.push(this.createIngredientItem('',''))
        }
      
        onRemoveIngredient(idx: number) {
          this.ingredientsArray.removeAt(idx)
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
          const ingredientsArray: string[] = []
          const measurementsArray: string[] = []
          for(let i = 0; i < this.form.get('ingredients')?.value.length; i++) {
            ingredientsArray.push(this.form.get('ingredients')?.value[i].ingredient)
            measurementsArray.push(this.form.get('ingredients')?.value[i].measurement)
          }
      
          formData.set('recipeId', this.form.get('recipeId')?.value)
          formData.set('name', this.form.get('name')?.value)
          formData.set('thumbnail', this.thumbnailImage.nativeElement.files[0])
          formData.set('category', this.form.get('category')?.value)
          formData.set('area', this.form.get('area')?.value)
          formData.set('instructions', this.form.get('instructions')?.value)
          formData.set('youtubeLink', this.form.get('youtubeLink')?.value)
          formData.set('ingredients', ingredientsArray.join(','))
          formData.set('measurements', measurementsArray.join(','))
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
