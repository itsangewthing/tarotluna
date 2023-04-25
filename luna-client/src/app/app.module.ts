import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { MaterialModule } from 'src/app/material.module';
import { TarotService } from './services/tarot.service';
import { AccountService } from './services/account.service';
import { CreateCardlistComponent } from './components/create-cardlist/create-cardlist.component';
import { SearchComponent } from './components/search/search.component';
import { NotFoundComponent } from './components/error/not-found/not-found.component';
import { SettingsComponent } from './components/account/settings/settings.component';
import { ProfileComponent } from './components/profile/profile.component';
import { CreateComponent } from './components/account/create/create.component';
import { LoginComponent } from './components/account/login/login.component';
import { GoogleLoginProvider, SocialAuthService, SocialAuthServiceConfig, SocialLoginModule } from '@abacritt/angularx-social-login';



const appRoutes: Routes =[
  {path:"", component: LoginComponent},
  {path:"profile", component: ProfileComponent},
  {path:"account/create", component: CreateComponent},
  {path:"account/settings", component: SettingsComponent},
  {path:"account/cardlists/create", component:CreateCardlistComponent},
  {path:"search", component:SearchComponent},
  {path:"cardlist", component: ListRecipesComponent},
  {path:"cards/category/:cat", component:ListRecipesComponent},
  {path:"recipes/area/:area", component:ListRecipesComponent},
  {path:"card/:user/:cardListId", component:AppComponent},
  {path:"card/:cardListId", component:AppComponent},
  {path:"**", component: NotFoundComponent}
]
@NgModule({
  declarations: [
    AppComponent,
    CreateCardlistComponent,
    SearchComponent,
    NotFoundComponent,
    SettingsComponent,
    ProfileComponent,
    CreateComponent,
  
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes, {useHash:true}),
    HttpClientModule,
    MaterialModule, 
    SocialLoginModule,


  ],
  providers: [AccountService, TarotService,{provide: 'SocialAuthServiceConfig',
  useValue: {
    autoLogin: true,
    providers: [
      {
        id: GoogleLoginProvider.PROVIDER_ID,
        provider: new GoogleLoginProvider('942508289464-7dfn185umum2qefq9ftt7smk34p6p7gs.apps.googleusercontent.com'),
      }
    ],
  } as SocialAuthServiceConfig,}],
  bootstrap: [AppComponent]
})
export class AppModule { }
