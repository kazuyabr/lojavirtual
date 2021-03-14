import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OrderConfirmationRoutingModule } from './order-confirmation-routing.module';
import { OrderConfirmationComponent } from './order-confirmation.component';

import { MenuModule } from 'src/app/components/menu/menu.module';
import { RodapeModule } from 'src/app/components/rodape/rodape.module';

import { MatStepperModule } from '@angular/material/stepper';
import { ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatRadioModule, MAT_RADIO_DEFAULT_OPTIONS } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { CartItemModule } from 'src/app/components/cart-item/cart-item.module';
import { MatCardModule } from '@angular/material/card';

@NgModule({
  declarations: [OrderConfirmationComponent],
  imports: [
    ReactiveFormsModule,
    CommonModule,
    OrderConfirmationRoutingModule,
    MenuModule,
    RodapeModule,
    MatInputModule,
    MatStepperModule,
    MatButtonModule,
    MatRadioModule,
    MatSelectModule,
    CartItemModule,
    MatCardModule,
  ],
  providers: [{
    provide: MAT_RADIO_DEFAULT_OPTIONS,
    useValue: { color: 'warn' },
  }
  ]
})
export class OrderConfirmationModule { }
