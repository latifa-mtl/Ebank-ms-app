import { Component, inject } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AsyncPipe} from '@angular/common';
import {Account, AccountListState, RequestStatus} from '../model/accounts.model';
import {catchError, map, Observable, of} from 'rxjs';

@Component({
  selector: 'app-accounts',
  imports: [
    AsyncPipe
  ],
  templateUrl: './accounts.html',
  styleUrl: './accounts.css',
})
export class Accounts {
  private http = inject(HttpClient);
  accounts$ : Observable<AccountListState> = this.http.get<Account[]>
  ("http://localhost:9999/EBANK-SERVICE/accounts")
    .pipe(
      map(resp => {
        return {accounts: resp, status: RequestStatus.SUCCESS}
      }),
      catchError((err, caught) => {
        return of({status: RequestStatus.ERROR, errorMessage: err.statusText})
      })
    )
  protected readonly RequestStatus = RequestStatus;
}
