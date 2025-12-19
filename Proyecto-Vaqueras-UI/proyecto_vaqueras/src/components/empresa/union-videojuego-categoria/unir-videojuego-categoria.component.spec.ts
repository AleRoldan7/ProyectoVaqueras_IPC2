import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnirVideojuegoCategoriaComponent } from './unir-videojuego-categoria.component';

describe('UnirVideojuegoCategoriaComponent', () => {
  let component: UnirVideojuegoCategoriaComponent;
  let fixture: ComponentFixture<UnirVideojuegoCategoriaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UnirVideojuegoCategoriaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnirVideojuegoCategoriaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
