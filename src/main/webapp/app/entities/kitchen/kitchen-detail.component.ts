import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKitchen } from 'app/shared/model/kitchen.model';

@Component({
  selector: 'jhi-kitchen-detail',
  templateUrl: './kitchen-detail.component.html',
})
export class KitchenDetailComponent implements OnInit {
  kitchen: IKitchen | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kitchen }) => (this.kitchen = kitchen));
  }

  previousState(): void {
    window.history.back();
  }
}
