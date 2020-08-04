import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartHousebackOfficeTestModule } from '../../../test.module';
import { BathRoomDetailComponent } from 'app/entities/bath-room/bath-room-detail.component';
import { BathRoom } from 'app/shared/model/bath-room.model';

describe('Component Tests', () => {
  describe('BathRoom Management Detail Component', () => {
    let comp: BathRoomDetailComponent;
    let fixture: ComponentFixture<BathRoomDetailComponent>;
    const route = ({ data: of({ bathRoom: new BathRoom(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SmartHousebackOfficeTestModule],
        declarations: [BathRoomDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BathRoomDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BathRoomDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bathRoom on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bathRoom).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
