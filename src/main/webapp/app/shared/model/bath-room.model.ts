export interface IBathRoom {
  id?: number;
  libelleBathRoom?: string;
}

export class BathRoom implements IBathRoom {
  constructor(public id?: number, public libelleBathRoom?: string) {}
}
