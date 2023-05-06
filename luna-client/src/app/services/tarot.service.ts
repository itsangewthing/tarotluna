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
      searchCardsByName(searchCard: string): Promise<Card[]>{
        const params = new HttpParams().set("searchCard", searchCard)

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
            this.httpClient.get<CardList[]>(`#/cardlist/${name}`, { headers })
        )
    }

 

    createCardList(formData: FormData): Promise<Response> {
        return firstValueFrom(
            this.httpClient.post<Response>('#/cardlist/user/', formData)
        )
    }

    editCardList(formData: FormData): Promise<Response> {
        return firstValueFrom(
            this.httpClient.put<Response>('#/cardlist/{user}/{cardListid}', formData)
        )
    }

    getCardListById(cardListId: string): Promise<Card> {
        const headers = new HttpHeaders()
            .set('Accept', 'application/json')
            .set('Content-Type', 'application/json')
        return firstValueFrom(
            this.httpClient.get<Card>(`#/cardlist/${cardListId}`, { headers })
        )
    }

    getCardListByEmail(email: string): Promise<CardList[]> {
        const headers = new HttpHeaders()
            .set('Accept', 'application/json')
            .set('Content-Type', 'application/json')
        const params = new HttpParams().set('email', email)
        return firstValueFrom(
            this.httpClient.get<CardList[]>(`#/cardlist/{email}`, { headers, params })
        )
    }

    deleteCardListByEmail(CardListId: BigInteger, email: string): Promise<Response> {
        const headers = new HttpHeaders()
            .set('Accept', 'application/json')
            .set('Content-Type', 'application/json')
        return firstValueFrom(
            this.httpClient.delete<Response>(`#/cardlist/${email}`, { headers, body: email })
        )
    }

    getCardsBySuit(suit: string) {
        return firstValueFrom(
            this.httpClient.get<Card[]>(`/api/v1/cards/{suit}`)
        )
    }

    getCardsByCourtsRank(courtsRank: string) {
        return firstValueFrom(
            this.httpClient.get<Card[]>(`/api/v1/cards/{rank}`)
        )
    }

    getRandomCard(n: BigInteger) {
        return firstValueFrom(
            this.httpClient.get<Card[]>(`/cards/random?n=1`)
        )
    }


    getCardLists(cardlists: String) {
        return firstValueFrom(
            this.httpClient.get<Card[]>(`/cardlist`)
        )
    }


    getAllCards(): Promise<string[]> {
        const headers = new HttpHeaders()
        .set('Accept', 'application/json')
        .set('Content-Type', 'application/json')
        return firstValueFrom(
            this.httpClient.get<string[]>('/cards', { headers})
        )
    }



}
