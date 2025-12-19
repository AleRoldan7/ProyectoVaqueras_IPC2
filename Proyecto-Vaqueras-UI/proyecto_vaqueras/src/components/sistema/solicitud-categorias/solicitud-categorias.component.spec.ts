import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SolicitudCategoriasComponent } from './solicitud-categorias.component';

describe('SolicitudCategoriasComponent', () => {
  let component: SolicitudCategoriasComponent;
  let fixture: ComponentFixture<SolicitudCategoriasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SolicitudCategoriasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SolicitudCategoriasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
