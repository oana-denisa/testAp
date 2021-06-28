import { IMedic } from 'app/entities/medic/medic.model';

export interface ISpecializare {
  id?: number;
  denumire?: string;
  medics?: IMedic[] | null;
}

export class Specializare implements ISpecializare {
  constructor(public id?: number, public denumire?: string, public medics?: IMedic[] | null) {}
}

export function getSpecializareIdentifier(specializare: ISpecializare): number | undefined {
  return specializare.id;
}
