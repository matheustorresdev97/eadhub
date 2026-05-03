export enum UserStatus {
  ACTIVE = 'ACTIVE',
  BLOCKED = 'BLOCKED'
}

export enum UserType {
  ADMIN = 'ADMIN',
  STUDENT = 'STUDENT',
  INSTRUCTOR = 'INSTRUCTOR',
  USER = 'USER'
}

export interface User {
  userId: string;
  username: string;
  email: string;
  fullName: string;
  userStatus: UserStatus;
  userType: UserType;
  phoneNumber?: string;
  cpf?: string;
  imageUrl?: string;
}
