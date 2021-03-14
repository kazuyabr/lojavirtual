import { Component, OnInit } from '@angular/core';
import { StorageService } from 'src/app/services/storage.service';
import { ClienteService } from 'src/app/services/cliente.service';
import { HandleMessageService } from 'src/app/services/handle-message.service';
import { ClienteModel } from 'src/app/models/cliente.model';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css']
})
export class ProfilePageComponent implements OnInit {

  cliente: ClienteModel;

  constructor(
    private storage: StorageService,
    private clienteService: ClienteService,
    private message: HandleMessageService,
  ) { }

  ngOnInit(): void {
    const localUser = this.storage.getLocalUser();

    if (localUser && localUser.email) {
      this.clienteService.findByEmail(localUser.email).subscribe({
        next: (data) => {
          const { id, nome, email, imgPath } = data as ClienteModel;
          this.cliente = {
            id,
            nome,
            email,
          };
          this.getIfImageExists(imgPath);
        },

        error: () => {}
      });
    }
  }

  getIfImageExists(img: string): void {
    this.clienteService.getClientImage(img).subscribe({
      next: () => {
        this.cliente.imgPath = `${environment.baseImageURL}/clientes/picture/${img}`;
      },
      error: () => {
        this.cliente.imgPath = null;
      },
    });
  }

}
