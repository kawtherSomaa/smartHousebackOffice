export interface ILivingRoom {
  id?: number;
  libelleLivingRoom?: string;
}

export class LivingRoom implements ILivingRoom {
  constructor(public id?: number, public libelleLivingRoom?: string) {}
}
