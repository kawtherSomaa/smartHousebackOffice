import { Moment } from 'moment';

export interface IReservation {
  id?: number;
  dateDebut?: Moment;
  dateFin?: Moment;
  userLogin?: string;
  userId?: number;
}

export class Reservation implements IReservation {
  constructor(public id?: number, public dateDebut?: Moment, public dateFin?: Moment, public userLogin?: string, public userId?: number) {}
}
