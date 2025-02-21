export interface User {
  id: number;
  name: string;
  email: string;
  password?: string;
  profile: 'ADMIN' | 'USER' | 'ANONYMOUS';
  creationDate: string;
  active: boolean;
}
