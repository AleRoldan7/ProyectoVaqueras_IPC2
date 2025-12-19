import { TestBed } from '@angular/core/testing';

import { GrupoFamiliarService } from './grupo-familiar-service';

describe('GrupoFamiliarService', () => {
  let service: GrupoFamiliarService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GrupoFamiliarService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
