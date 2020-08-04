import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartHousebackOfficeTestModule } from '../../../test.module';
import { HouseDetailComponent } from 'app/entities/house/house-detail.component';
import { House } from 'app/shared/model/house.model';

describe('Component Tests', () => {
  describe('House Management Detail Component', () => {
    let comp: HouseDetailComponent;
    let fixture: ComponentFixture<HouseDetailComponent>;
    const route = ({ data: of({ house: new House(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SmartHousebackOfficeTestModule],
        declarations: [HouseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(HouseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HouseDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load house on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.house).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
