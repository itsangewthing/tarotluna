import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginaccComponent } from './loginacc.component';

describe('LoginaccComponent', () => {
  let component: LoginaccComponent;
  let fixture: ComponentFixture<LoginaccComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoginaccComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginaccComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
