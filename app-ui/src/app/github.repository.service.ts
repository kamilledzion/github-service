import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from "rxjs";

import { ErrorResponse }from './model/error.response.model';
import { GithubRepository } from './model/github.repository.model';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json'})
};

@Injectable()
export class GithubService {

  constructor(private http:HttpClient) {}

  private gitUrl = 'http://localhost:8080/repositories/';

  public getDetails(username: string, repository: string): Observable<GithubRepository> {
    return this.http.get<GithubRepository>(this.gitUrl + username + '/' + repository, httpOptions)
      .pipe(this.handleError);
  }

  private handleError(error: any) {
    return error;
  }
}
