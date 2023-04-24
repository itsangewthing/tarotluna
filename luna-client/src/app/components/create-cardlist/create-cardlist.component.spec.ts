import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateCardlistComponent } from './create-cardlist.component';

describe('CreateCardlistComponent', () => {
  let component: CreateCardlistComponent;
  let fixture: ComponentFixture<CreateCardlistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateCardlistComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateCardlistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
