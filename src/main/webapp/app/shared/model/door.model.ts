export interface IDoor {
  id?: number;
  libelleDoor?: string;
}

export class Door implements IDoor {
  constructor(public id?: number, public libelleDoor?: string) {}
}
