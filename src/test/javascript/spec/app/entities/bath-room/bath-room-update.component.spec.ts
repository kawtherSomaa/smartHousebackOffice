import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SmartHousebackOfficeTestModule } from '../../../test.module';
import { BathRoomUpdateComponent } from 'app/entities/bath-room/bath-room-update.component';
import { BathRoomService } from 'app/entities/bath-room/bath-room.service';
import { BathRoom } from 'app/shared/model/bath-room.model';

describe('Component Tests', () => {
  describe('BathRoom Management Update Component', () => {
    let comp: BathRoomUpdateComponent;
    let fixture: ComponentFixture<BathRoomUpdateComponent>;
    let service: BathRoomService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SmartHousebackOfficeTestModule],
        declarations: [BathRoomUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BathRoomUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BathRoomUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BathRoomService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BathRoom(123);
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
        const entity = new BathRoom();
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
