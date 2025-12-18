import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaPrincipalEmpresaComponent } from './pagina-principal-empresa.component';

describe('PaginaPrincipalEmpresaComponent', () => {
  let component: PaginaPrincipalEmpresaComponent;
  let fixture: ComponentFixture<PaginaPrincipalEmpresaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaginaPrincipalEmpresaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaginaPrincipalEmpresaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
