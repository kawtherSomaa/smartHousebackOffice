import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartHousebackOfficeTestModule } from '../../../test.module';
import { LivingRoomDetailComponent } from 'app/entities/living-room/living-room-detail.component';
import { LivingRoom } from 'app/shared/model/living-room.model';

describe('Component Tests', () => {
  describe('LivingRoom Management Detail Component', () => {
    let comp: LivingRoomDetailComponent;
    let fixture: ComponentFixture<LivingRoomDetailComponent>;
    const route = ({ data: of({ livingRoom: new LivingRoom(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SmartHousebackOfficeTestModule],
        declarations: [LivingRoomDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LivingRoomDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LivingRoomDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load livingRoom on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.livingRoom).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
