import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHouse } from 'app/shared/model/house.model';

@Component({
  selector: 'jhi-house-detail',
  templateUrl: './house-detail.component.html',
})
export class HouseDetailComponent implements OnInit {
  house: IHouse | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ house }) => (this.house = house));
  }

  previousState(): void {
    window.history.back();
  }
}
