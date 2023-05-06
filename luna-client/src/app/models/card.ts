

export interface Card{
  user: boolean;
category: any;
    
    name_short: string;
    name: string;
    value: string;
    meaning_up: string;
    meaning_reverse: string;
    desc: string;
    createdBy: string;
    type?: Card.TypeEnum;
    courtsRank?: Card.CourtsRankEnum;
    suit?: Card.SuitEnum;
    card: string[
        
    ];

}

export namespace Card {
    export type TypeEnum = 'major' | 'minor';
    export const TypeEnum = {
        Major: 'major' as TypeEnum,
        Minor: 'minor' as TypeEnum
    };
}

export namespace Card {
    export type SuitEnum = 'wands' | 'swords' | 'cups' | 'pentacles';
    export const SuitEnum = {
        Wands: 'wands' as SuitEnum,
        Swords: 'Swords' as SuitEnum,
        Cups: 'Cups' as SuitEnum,
        Pentacles: 'Pentacles' as SuitEnum
    };
}


export namespace Card {
    export type CourtsRankEnum = 'queen' | 'queens' | 'king' | 'kings' | 'knight' | 'knights' | 'page' | 'pages';
    export const CourtsRankEnum = {
        Queen: 'queen' as CourtsRankEnum,
        Queens: 'queens' as CourtsRankEnum,
        King: 'king' as CourtsRankEnum,
        Kings: 'kings' as CourtsRankEnum,
        Knight: 'knight' as CourtsRankEnum,
        Knights: 'knights' as CourtsRankEnum,
        Page: 'page' as CourtsRankEnum,
        Pages: 'pages' as CourtsRankEnum
    };
}

