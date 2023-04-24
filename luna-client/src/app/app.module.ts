import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { MaterialModule } from 'src/material.module';
import { TarotService } from './services/tarot.service';
import { AccountService } from './services/account.service';
import { GoogleLoginProvider, FacebookLoginProvider, SocialAuthServiceConfig, SocialLoginModule } from '@abacritt/angularx-social-login';
import { CreateCardlistComponent } from './components/create-cardlist/create-cardlist.component';
import { SearchComponent } from './components/search/search.component';
import { NotFoundComponent } from './components/error/not-found/not-found.component';
import { CreateaccComponent } from './components/account/createacc/createacc.component';
import { LoginaccComponent } from './components/account/loginacc/loginacc.component';
import { SettingsComponent } from './components/account/settings/settings.component';
import { ProfileComponent } from './components/profile/profile.component';
import { CreateComponent } from './components/account/create/create.component';


const appRoutes: Routes =[
  
]
@NgModule({
  declarations: [
    AppComponent,
    CreateCardlistComponent,
    SearchComponent,
    NotFoundComponent,
    CreateaccComponent,
    LoginaccComponent,
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
    MaterialModule
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
