import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BibliotecaUsuarioComponent } from './biblioteca-usuario.component';

describe('BibliotecaUsuarioComponent', () => {
  let component: BibliotecaUsuarioComponent;
  let fixture: ComponentFixture<BibliotecaUsuarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BibliotecaUsuarioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BibliotecaUsuarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
