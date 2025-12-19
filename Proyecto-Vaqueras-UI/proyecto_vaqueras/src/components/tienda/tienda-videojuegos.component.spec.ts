import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TiendaVideojuegosComponent } from './tienda-videojuegos.component';

describe('TiendaVideojuegosComponent', () => {
  let component: TiendaVideojuegosComponent;
  let fixture: ComponentFixture<TiendaVideojuegosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TiendaVideojuegosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TiendaVideojuegosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
