import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AccountService } from 'src/app/services/account.service';
import { FacebookLoginProvider, GoogleLoginProvider, SocialAuthService, SocialUser } from '@abacritt/angularx-social-login';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit{

      form!: FormGroup
      errorMsg: string = ""
      isLoggedIn: boolean = false
      isLoading: boolean = false
      onSubmitForm: any;

      constructor(private fb: FormBuilder, private accSvc: AccountService, private router: Router) { }

      ngOnInit(): void {
        const sessionId = localStorage.getItem("sessionId") ?? ''
        if (sessionId) {
          this.accSvc.authSession(sessionId)
            .then(result => {
              this.accSvc.isLoggedIn = this.isLoggedIn = true
              this.accSvc.userLoggedIn = result.data
              this.isLoading = false;
              this.router.navigate(['/profile'])
            })
            .catch(error => {
              this.router.navigate(['/'])
            })
        }
        this.form = this.createForm()} 

      private createForm() {
        return this.fb.group({
          username: this.fb.control<string>('', [Validators.required]),
          email: this.fb.control<string>('', [Validators.required, Validators.email]),
          password: this.fb.control<string>('', [Validators.required])
        })
      }


      }

      onSubmitForm() {
        const email = this.form.get('email')?.value
        const pwd = this.form.get('password')?.value
        const username = this.form.get('username')?.value
        this.isLoading = true
        this.accSvc.createAccount(username, email, pwd)
        .then(result=>{
          this.isLoading = false
          alert("Account created successfully!")
          this.router.navigate(['/'])
        })
        .catch(error=>{
          this.isLoading = false;
          console.error(">>> error: ", error)
          this.errorMsg = error.error.message
        })
        }


        


