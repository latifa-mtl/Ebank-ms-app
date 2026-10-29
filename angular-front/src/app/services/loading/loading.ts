import {Component, signal} from '@angular/core';

@Component({
  selector: 'app-loading',
  imports: [],
  templateUrl: './loading.html',
  styleUrl: './loading.css',
})
export class LoadingService {
  loading = signal<boolean>(false);
  public isLoading = this.loading.asReadonly();
  public setLoading(value : boolean) {
    this.loading.set(value);
  }
}
