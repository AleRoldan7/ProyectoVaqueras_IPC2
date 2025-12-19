import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActualizarEliminarCategoriaComponent } from './actualizar-eliminar-categoria.component';

describe('ActualizarEliminarCategoriaComponent', () => {
  let component: ActualizarEliminarCategoriaComponent;
  let fixture: ComponentFixture<ActualizarEliminarCategoriaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActualizarEliminarCategoriaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActualizarEliminarCategoriaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
