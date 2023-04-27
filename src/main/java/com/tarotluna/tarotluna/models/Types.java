package com.tarotluna.tarotluna.models;


public enum Types {
    MAJOR("Major"), 
    MINOR("Minor");

        private final String types;

        Types(String types) {this.types = types; }

        public String getTypes(){ return types; }
        
}


