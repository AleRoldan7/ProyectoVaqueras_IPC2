import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PorcentajeComisionComponent } from './porcentaje-comision.component';

describe('PorcentajeComisionComponent', () => {
  let component: PorcentajeComisionComponent;
  let fixture: ComponentFixture<PorcentajeComisionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PorcentajeComisionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PorcentajeComisionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
