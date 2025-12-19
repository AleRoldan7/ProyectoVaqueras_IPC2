import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompraVideojuegoComponent } from './compra-videojuego.component';

describe('CompraVideojuegoComponent', () => {
  let component: CompraVideojuegoComponent;
  let fixture: ComponentFixture<CompraVideojuegoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompraVideojuegoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CompraVideojuegoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
