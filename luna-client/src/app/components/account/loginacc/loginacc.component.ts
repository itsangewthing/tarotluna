import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from 'src/app/models/user';
import { AccountService } from 'src/app/services/account.service';
import { FacebookLoginProvider, GoogleLoginProvider, SocialAuthService, SocialUser } from '@abacritt/angularx-social-login';

@Component({
  selector: 'app-loginacc',
  templateUrl: './loginacc.component.html',
  styleUrls: ['./loginacc.component.css']
})
export class LoginaccComponent {
      form!: FormGroup
      isLoggedIn: boolean = false
      errorMsg: string = ""

      isLoading: boolean = false

      onSocialLoginSub$!: Subscription

      constructor(private fb: FormBuilder, private accSvc: AccountService, private router: Router, private socialAuthService: SocialAuthService) { }

      ngOnDestroy(): void {
        this.onSocialLoginSub$.unsubscribe()
      }

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
        this.form = this.createForm()

        this.onSocialLoginSub$ = this.socialAuthService.authState.subscribe((user: { email: string; name: string; id: string; }) => {
          if (!this.isLoading && user) {
            console.log(user)
            this.isLoading = true
            this.accSvc.socialLogin(user.email, user.name, user.id)
              .then(result => {
                const myUser: User = result.data
                console.log(myUser)
                this.isLoading = false
                this.accSvc.onLoginEvent.next(myUser)
                this.router.navigate(['/profile'])
              })
              .catch(error => {
                this.socialAuthService.signOut()
                this.isLoading = false
                this.accSvc.isLoggedIn = false
                this.errorMsg = "Account already exists"
              })
          }
        });
      }

      private createForm() {
        return this.fb.group({
          email: this.fb.control<string>('', [Validators.required, Validators.email]),
          password: this.fb.control<string>('', [Validators.required])
        })
      }

      onSubmitForm() {
        const email = this.form.get('email')?.value
        const pwd = this.form.get('password')?.value
        this.isLoading = true
        this.accSvc.auth(email, pwd)
          .then(result => {
            const myUser: User = result.data
            console.log(myUser)
            this.accSvc.onLoginEvent.next(myUser)
            this.isLoading = false
            this.router.navigate(['/profile'])
          })
          .catch(error => {
            this.isLoading = false
            this.errorMsg = "Invalid credentials"
          })
      }
}
