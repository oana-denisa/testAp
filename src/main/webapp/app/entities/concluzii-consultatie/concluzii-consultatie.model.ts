import { IConsultatie } from 'app/entities/consultatie/consultatie.model';

export interface IConcluziiConsultatie {
  id?: number;
  diagnostic?: string;
  tratament?: string;
  observatii?: string;
  controlUrmator?: string;
  programare?: IConsultatie | null;
}

export class ConcluziiConsultatie implements IConcluziiConsultatie {
  constructor(
    public id?: number,
    public diagnostic?: string,
    public tratament?: string,
    public observatii?: string,
    public controlUrmator?: string,
    public programare?: IConsultatie | null
  ) {}
}

export function getConcluziiConsultatieIdentifier(concluziiConsultatie: IConcluziiConsultatie): number | undefined {
  return concluziiConsultatie.id;
}
