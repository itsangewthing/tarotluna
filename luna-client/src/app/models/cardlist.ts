import { Card } from "./card";

export interface CardList extends Card{
    [x: string]: any|string;
    user: boolean;
    // name_short: string;
    // name: string;
    // value: string;

    // meaning_up: string;
    // meaning_reverse: string;
    // desc: string;
    // createdBy: string;
    // types: string[];
    cardList: string[];
    nhits?: number;
    clistId: string;
    cards?: Array<Card>;
    
}