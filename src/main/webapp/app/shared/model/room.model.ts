export interface IRoom {
  id?: number;
  libelleRoom?: string;
}

export class Room implements IRoom {
  constructor(public id?: number, public libelleRoom?: string) {}
}
