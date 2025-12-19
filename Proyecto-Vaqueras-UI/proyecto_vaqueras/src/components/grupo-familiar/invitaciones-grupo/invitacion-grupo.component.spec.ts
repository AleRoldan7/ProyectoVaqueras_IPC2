import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InvitacionGrupoComponent } from './invitacion-grupo.component';

describe('InvitacionGrupoComponent', () => {
  let component: InvitacionGrupoComponent;
  let fixture: ComponentFixture<InvitacionGrupoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InvitacionGrupoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InvitacionGrupoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
