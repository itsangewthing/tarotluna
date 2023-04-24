import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http"
import { Injectable } from "@angular/core"
import { firstValueFrom, lastValueFrom } from "rxjs"
import { CardList } from "../models/cardlist"
import { Card } from "../models/card"

@Injectable({
    providedIn: 'root'
  }
)

export class TarotService {
    

    authSession(sessionId: string) {
      throw new Error('Method not implemented.')
    }
    isLoggedIn!: boolean

    constructor(private httpClient: HttpClient) { }

// 1------------- SEARCH CARDS BY NAME
      searchCardsByName(searchName: string): Promise<Card[]>{
        const params = new HttpParams().set("searchName", searchName)

            const headers = new HttpHeaders()
                                .set('content-type', 'application/json')
                                .set('Access-Control-Allow-Origin', '*')

            return lastValueFrom(this.httpClient.get<Card[]>(`/cards/search`, {headers, params}))
                }


    getCardListsByName(name: string): Promise<CardList[]> {
        const headers = new HttpHeaders()
            .set('Accept', 'application/json')
            .set('Content-Type', 'application/json')
        return firstValueFrom(
            this.httpClient.get<CardList[]>(`/api/v1/cardlist/${name}`, { headers })
        )
    }

 

    createCardList(formData: FormData): Promise<Response> {
        return firstValueFrom(
            this.httpClient.post<Response>('#/divination/user/', formData)
        )
    }

    editCardList(formData: FormData): Promise<Response> {
        return firstValueFrom(
            this.httpClient.put<Response>('#/divination/{user}/{cardListid}', formData)
        )
    }

    getCardListById(cardListId: string): Promise<Card> {
        const headers = new HttpHeaders()
            .set('Accept', 'application/json')
            .set('Content-Type', 'application/json')
        return firstValueFrom(
            this.httpClient.get<Card>(`/api/divination/cardList/${cardListId}`, { headers })
        )
    }

    getCardListByEmail(email: string): Promise<CardList[]> {
        const headers = new HttpHeaders()
            .set('Accept', 'application/json')
            .set('Content-Type', 'application/json')
        const params = new HttpParams().set('email', email)
        return firstValueFrom(
            this.httpClient.get<CardList[]>(`/api/v1/cardlists/{email}`, { headers, params })
        )
    }

    deleteCardListById(CardListId: BigInteger, email: string): Promise<Response> {
        const headers = new HttpHeaders()
            .set('Accept', 'application/json')
            .set('Content-Type', 'application/json')
        return firstValueFrom(
            this.httpClient.delete<Response>(`/api/v1/card/cardlists/${CardListId}`, { headers, body: email })
        )
    }

    searchName(searchName: string) {
        return firstValueFrom(
            this.httpClient.get<Card[]>(`/api/v1/cards/searchName`)
        )
    }

    getRandomCard(n: BigInteger) {
        return firstValueFrom(
            this.httpClient.get<Card[]>(`/cards/random?n=1`)
        )
    }


    getCardLists(cardlists: String) {
        return firstValueFrom(
            this.httpClient.get<Card[]>(`/cardlists`)
        )
    }

}
