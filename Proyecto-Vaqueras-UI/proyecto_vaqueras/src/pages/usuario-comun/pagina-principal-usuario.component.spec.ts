import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaPrincipalUsuarioComponent } from './pagina-principal-usuario.component';

describe('PaginaPrincipalUsuarioComponent', () => {
  let component: PaginaPrincipalUsuarioComponent;
  let fixture: ComponentFixture<PaginaPrincipalUsuarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaginaPrincipalUsuarioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaginaPrincipalUsuarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
