import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrearVideojuegoComponent } from './crear-videojuego.component';

describe('CrearVideojuegoComponent', () => {
  let component: CrearVideojuegoComponent;
  let fixture: ComponentFixture<CrearVideojuegoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CrearVideojuegoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CrearVideojuegoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
