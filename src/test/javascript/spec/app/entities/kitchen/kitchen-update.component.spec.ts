import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SmartHousebackOfficeTestModule } from '../../../test.module';
import { KitchenUpdateComponent } from 'app/entities/kitchen/kitchen-update.component';
import { KitchenService } from 'app/entities/kitchen/kitchen.service';
import { Kitchen } from 'app/shared/model/kitchen.model';

describe('Component Tests', () => {
  describe('Kitchen Management Update Component', () => {
    let comp: KitchenUpdateComponent;
    let fixture: ComponentFixture<KitchenUpdateComponent>;
    let service: KitchenService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SmartHousebackOfficeTestModule],
        declarations: [KitchenUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(KitchenUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KitchenUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KitchenService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Kitchen(123);
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
        const entity = new Kitchen();
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
