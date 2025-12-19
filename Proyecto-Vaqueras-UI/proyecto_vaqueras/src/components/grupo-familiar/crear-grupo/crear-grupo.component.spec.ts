import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrearGrupoComponent } from './crear-grupo.component';

describe('CrearGrupoComponent', () => {
  let component: CrearGrupoComponent;
  let fixture: ComponentFixture<CrearGrupoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CrearGrupoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CrearGrupoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
