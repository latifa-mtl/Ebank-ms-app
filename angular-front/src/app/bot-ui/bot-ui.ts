import { Component, inject } from '@angular/core';
import {HttpClient, HttpDownloadProgressEvent, HttpEvent, HttpEventType, HttpResponse} from '@angular/common/http';
import { Observable, map } from 'rxjs';import { AsyncPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {MarkdownComponent} from 'ngx-markdown';
import {LoadingService} from '../services/loading/loading';

interface ChatEvent {
  type: 'Sent' | 'Response' | 'Other';
  content?: string;
  data?: unknown;
}

@Component({
  selector: 'app-bot-ui',
  imports: [AsyncPipe, FormsModule,MarkdownComponent],
  standalone: true,
  templateUrl: './bot-ui.html',
  styleUrl: './bot-ui.css',
})
export class BotUi {
  query  = '' ;

  http = inject(HttpClient);
  response$! : Observable<string>
  streamResponse$!: Observable<ChatEvent>;

  public loadService = inject(LoadingService);

  askAgent() {
    this.response$ = this.http.get(
      'http://localhost:9999/EBANK-BOT/chat',
      {
        params: { query: this.query },
        responseType: 'text'
      }
    );
  }

  askAgentStream() {
    this.streamResponse$ = this.http.get(
      'http://localhost:9999/EBANK-BOT/chatStream',
      {
        params: { query: this.query },
        responseType: 'text',
        observe: 'events',
        reportProgress: true
      }
    ).pipe(
      map((event: HttpEvent<string>): ChatEvent => {
        switch (event.type) {
          case HttpEventType.Sent:
            return { type: 'Sent' };

          case HttpEventType.DownloadProgress:
            return {
              type: 'Response',
              content: (event as HttpDownloadProgressEvent).partialText ?? ''
            };

          case HttpEventType.Response:
            return {
              type: 'Response',
              content: (event as HttpResponse<string>).body ?? ''
            };

          default:
            return {
              type: 'Other',
              data: event
            };
        }
      })
    );
  }
}
