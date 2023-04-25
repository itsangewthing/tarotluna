

export interface Card{
  user: boolean;
category: any;
    
    name_short: string;
    name: string;
    value: string;
    value_int: string;
    meaning_up: string;
    meaning_reverse: string;
    desc: string;
    createdBy: string;
    types: string[];
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
