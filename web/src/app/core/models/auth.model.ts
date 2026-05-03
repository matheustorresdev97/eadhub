export interface LoginRequest {
  username: string;
  password: string;
}

export interface SignupRequest {
  username: string;
  email: string;
  password: string;
  fullName: string;
  phoneNumber?: string;
  cpf?: string;
}

export interface JwtResponse {
  token: string;
}
