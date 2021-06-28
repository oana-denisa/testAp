import * as dayjs from 'dayjs';
import { IUser } from 'app/entities/user/user.model';

export interface IClient {
  id?: number;
  nume?: string;
  prenume?: string;
  dataNastere?: dayjs.Dayjs | null;
  adresa?: string;
  telefon?: string;
  email?: string;
  disponibilitate?: boolean | null;
  user?: IUser | null;
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public nume?: string,
    public prenume?: string,
    public dataNastere?: dayjs.Dayjs | null,
    public adresa?: string,
    public telefon?: string,
    public email?: string,
    public disponibilitate?: boolean | null,
    public user?: IUser | null
  ) {
    this.disponibilitate = this.disponibilitate ?? false;
  }
}

export function getClientIdentifier(client: IClient): number | undefined {
  return client.id;
}
