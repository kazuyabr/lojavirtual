import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class HandleMessageService {

  constructor(private snack: MatSnackBar) { }

  showMessage(message: string, isError: boolean = false, time: number = 5000): void {
    this.snack.open(message, 'X', {
      duration: time,
      horizontalPosition: 'right',
      verticalPosition: 'top',
      panelClass: (isError) ? ['msg-error'] : ['msg-success'], 
    });
  }

  showDefaultMessage(): void {
    this.snack.open('Ocorreu um erro inesperado! Tente novamente.', 'X', {
      duration: 5000,
      horizontalPosition: 'right',
      verticalPosition: 'top',
      panelClass: ['msg-error'], 
    });
  }
}
