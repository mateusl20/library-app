export interface Book {
  id?: number;
  title: string;
  category: 'INFORMATICA' | 'CIENCIAS' | null;
  copies: number;
  available?: boolean;
}
