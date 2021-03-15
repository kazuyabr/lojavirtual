import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SigupPageComponent } from './sigup-page.component';

describe('SigupPageComponent', () => {
  let component: SigupPageComponent;
  let fixture: ComponentFixture<SigupPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SigupPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SigupPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
