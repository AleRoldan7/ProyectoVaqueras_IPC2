import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgregarImagenComponent } from './agregar-imagen.component';

describe('AgregarImagenComponent', () => {
  let component: AgregarImagenComponent;
  let fixture: ComponentFixture<AgregarImagenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AgregarImagenComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgregarImagenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
