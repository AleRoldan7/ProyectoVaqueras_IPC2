import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VideojuegosCreadosComponent } from './videojuegos-creados.component';

describe('VideojuegosCreadosComponent', () => {
  let component: VideojuegosCreadosComponent;
  let fixture: ComponentFixture<VideojuegosCreadosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VideojuegosCreadosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VideojuegosCreadosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
