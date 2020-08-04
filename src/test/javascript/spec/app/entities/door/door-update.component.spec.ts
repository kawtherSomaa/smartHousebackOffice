import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SmartHousebackOfficeTestModule } from '../../../test.module';
import { DoorUpdateComponent } from 'app/entities/door/door-update.component';
import { DoorService } from 'app/entities/door/door.service';
import { Door } from 'app/shared/model/door.model';

describe('Component Tests', () => {
  describe('Door Management Update Component', () => {
    let comp: DoorUpdateComponent;
    let fixture: ComponentFixture<DoorUpdateComponent>;
    let service: DoorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SmartHousebackOfficeTestModule],
        declarations: [DoorUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DoorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DoorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Door(123);
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
        const entity = new Door();
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
