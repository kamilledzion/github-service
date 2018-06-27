import { Component, OnInit }from '@angular/core';
import { Router }from '@angular/router';

import { GithubRepository }from './model/github.repository.model';
import { ErrorResponse }from './model/error.response.model';
import { GithubService }from './github.repository.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  github: GithubRepository;
  errorResponse: ErrorResponse;
  username: string;
  repository: string;

  constructor(private router: Router, private githubService: GithubService) {}

  ngOnInit() {
    this.github = new GithubRepository();
    this.errorResponse = new ErrorResponse();
  }

  getDetails(): void {
    this.githubService.getDetails(this.username, this.repository)
      .subscribe(
        data => {
          this.github = data;
          this.errorResponse = new ErrorResponse();
        },
        error => {
          this.errorResponse = error.error
        }
      );
  }
}
