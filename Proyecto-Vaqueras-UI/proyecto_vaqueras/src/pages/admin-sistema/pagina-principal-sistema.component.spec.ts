import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaPrincipalSistemaComponent } from './pagina-principal-sistema.component';

describe('PaginaPrincipalSistemaComponent', () => {
  let component: PaginaPrincipalSistemaComponent;
  let fixture: ComponentFixture<PaginaPrincipalSistemaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaginaPrincipalSistemaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaginaPrincipalSistemaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
