export interface IHouse {
  id?: number;
  libelleHouse?: string;
  userLogin?: string;
  userId?: number;
  livingroomsId?: number;
  doorsId?: number;
  bathroomsId?: number;
  kitchensId?: number;
  roomsId?: number;
}

export class House implements IHouse {
  constructor(
    public id?: number,
    public libelleHouse?: string,
    public userLogin?: string,
    public userId?: number,
    public livingroomsId?: number,
    public doorsId?: number,
    public bathroomsId?: number,
    public kitchensId?: number,
    public roomsId?: number
  ) {}
}
