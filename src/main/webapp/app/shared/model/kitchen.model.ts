export interface IKitchen {
  id?: number;
  libelleKitchen?: string;
}

export class Kitchen implements IKitchen {
  constructor(public id?: number, public libelleKitchen?: string) {}
}
