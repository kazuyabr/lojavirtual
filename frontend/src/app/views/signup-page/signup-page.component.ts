import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ClienteCadastroModel } from 'src/app/models/cliente-cadastro.model';
import { EstadoModel } from 'src/app/models/estado.model';
import { CidadeModel } from 'src/app/models/cidade.model';
import { EstadoService } from 'src/app/services/estado.service';
import { CidadeService } from 'src/app/services/cidade.service';
import { Router } from '@angular/router';
import { ClienteService } from 'src/app/services/cliente.service';
import { HandleMessageService } from 'src/app/services/handle-message.service';

@Component({
  selector: 'app-signup-page',
  templateUrl: './signup-page.component.html',
  styleUrls: ['./signup-page.component.css'],
})
export class SignupPageComponent implements OnInit {

  signupFormControl: FormGroup;

  cliente: ClienteCadastroModel = {
    nome: '',
      email: '',
      tipo: 1,
      cpfOuCnpj: '',
      senha: '',

      cep: '',
      bairro: '',
      logradouro: '',
      numero: '',
      complemento: '',

      telefone1: '',
      telefone2: '',
      telefone3: '',

      cidadeId: null,
  };
  estados: EstadoModel[];
  cidades: CidadeModel[];

  constructor(
    private formBuilder: FormBuilder,
    private estadoService: EstadoService,
    private cidadeService: CidadeService,
    private clienteService: ClienteService,
    private message: HandleMessageService,
    private router: Router,
  ) {
    this.signupFormControl = this.formBuilder.group({
      nome: [
        this.cliente.nome, [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(120),
          Validators.pattern('[a-zA-Z ]*'),
        ]
      ],
      email: [
        this.cliente.email, [
          Validators.required,
          Validators.email,
        ]
      ],
      tipo: [
        this.cliente.tipo, [
          Validators.required,
        ]
      ],
      cpfOuCnpj: [
        this.cliente.cpfOuCnpj, [
          Validators.required,
          Validators.minLength(11),
          Validators.maxLength(14),
          Validators.pattern('[0-9]*'),
        ]
      ],
      senha: [
        this.cliente.senha, [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(10),
        ]
      ],

      cep: [
        this.cliente.cep, [
          Validators.required,
        ]
      ],
      bairro: [
        this.cliente.bairro, [
          Validators.required,
        ]
      ],
      logradouro: [
        this.cliente.logradouro, [
          Validators.required,
        ]
      ],
      numero: [
        this.cliente.numero, [
          Validators.required,
        ]
      ],
      complemento: [
        this.cliente.complemento,
      ],

      telefone1: [
        this.cliente.telefone1, [
          Validators.required,
        ]
      ],
      telefone2: [
        this.cliente.telefone2,
      ],
      telefone3: [
        this.cliente.telefone3,
      ],

      estadoId: [
        null, [
          Validators.required,
        ]
      ],
      cidadeId: [
        this.cliente.cidadeId, [
          Validators.required,
        ]
      ],
    });
  }

  ngOnInit(): void {
    this.estadoService.findAll().subscribe({
      next: (data) => {
        this.estados = data;
        this.signupFormControl.controls.estadoId.setValue(this.estados[0].id);
        this.updateCidades();
      }
    });
  }

  updateCidades() {
    const estadoId = this.signupFormControl.value.estadoId;
    this.cidadeService.findAll(estadoId).subscribe({
      next: (data) => {
        this.cidades = data;
        this.signupFormControl.controls.cidadeId.setValue(null);
      },
    });
  }

  sigupUser(): void {
    this.clienteService.insert(this.signupFormControl.value).subscribe({
      next: () => {
        this.message.showMessage('Cliente cadastrado com sucesso!');
        this.router.navigateByUrl('/products-page');
      }
    });
  }

  cancelar(): void {
    this.router.navigateByUrl('/products-page');
  }

}
