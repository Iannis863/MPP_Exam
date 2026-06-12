import {Component, inject, Injectable, signal} from '@angular/core';
import {Router, RouterOutlet} from '@angular/router';
import {FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {User} from './data/user';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ReactiveFormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
@Injectable({providedIn: 'root'})
export class App {
  protected readonly title = signal('exam-recap-ui');
  private router: Router = inject(Router);
  private http = inject(HttpClient);

  authForm: FormGroup = new FormGroup({
      username: new FormControl('')
    }
  );

  login() {
    let username = this.authForm.controls['username'].value;
    console.log("Attempting login for user:", username);
    this.http.post<any>('http://localhost:8080/user', username).subscribe({
      next: (res) => {
        console.log("Login successful, response:", res);
        // res is LoginResponse { user: User, game: Game }
        this.router.navigate(['user/' + res.user.id + '/game']);
      },
      error: (err) => {
        console.error("Login failed:", err);
      }
    })
  }
}
