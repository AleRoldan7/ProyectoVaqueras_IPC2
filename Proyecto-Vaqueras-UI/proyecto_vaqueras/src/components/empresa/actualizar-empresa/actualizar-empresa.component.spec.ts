import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActualizarEmpresaComponent } from './actualizar-empresa.component';

describe('ActualizarEmpresaComponent', () => {
  let component: ActualizarEmpresaComponent;
  let fixture: ComponentFixture<ActualizarEmpresaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActualizarEmpresaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActualizarEmpresaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
