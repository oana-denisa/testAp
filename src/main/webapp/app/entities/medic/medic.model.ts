import { IUser } from 'app/entities/user/user.model';
import { IGrad } from 'app/entities/grad/grad.model';
import { ISpecializare } from 'app/entities/specializare/specializare.model';

export interface IMedic {
  id?: number;
  nume?: string;
  prenume?: string;
  disponibilitate?: boolean | null;
  user?: IUser | null;
  grad?: IGrad | null;
  specializare?: ISpecializare | null;
}

export class Medic implements IMedic {
  constructor(
    public id?: number,
    public nume?: string,
    public prenume?: string,
    public disponibilitate?: boolean | null,
    public user?: IUser | null,
    public grad?: IGrad | null,
    public specializare?: ISpecializare | null
  ) {
    this.disponibilitate = this.disponibilitate ?? false;
  }
}

export function getMedicIdentifier(medic: IMedic): number | undefined {
  return medic.id;
}
