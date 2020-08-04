import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SmartHousebackOfficeTestModule } from '../../../test.module';
import { LivingRoomUpdateComponent } from 'app/entities/living-room/living-room-update.component';
import { LivingRoomService } from 'app/entities/living-room/living-room.service';
import { LivingRoom } from 'app/shared/model/living-room.model';

describe('Component Tests', () => {
  describe('LivingRoom Management Update Component', () => {
    let comp: LivingRoomUpdateComponent;
    let fixture: ComponentFixture<LivingRoomUpdateComponent>;
    let service: LivingRoomService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SmartHousebackOfficeTestModule],
        declarations: [LivingRoomUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LivingRoomUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LivingRoomUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LivingRoomService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LivingRoom(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new LivingRoom();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
