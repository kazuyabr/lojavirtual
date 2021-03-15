import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css']
})
export class LandingPageComponent implements OnInit {

  constructor(
    private auth: AuthenticationService,
  ) { }

  ngOnInit(): void {
    this.handleRefreshToken();
  }

  handleRefreshToken(): void {
    this.auth.refreshToken().subscribe({
      next: (data) => {
        this.auth.successfullLogin(data.headers.get('Authorization'));
      },

      error: () => {}
    });
  }

}
