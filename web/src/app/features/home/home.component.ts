import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  template: `
<div class="min-h-screen bg-slate-50 flex flex-col">
  <header class="bg-white border-b border-slate-200 p-4 flex justify-between items-center shadow-sm">
    <div class="flex items-center gap-2">
      <div class="w-10 h-10 bg-blue-600 rounded flex items-center justify-center">
        <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path d="M12 14l9-5-9-5-9 5 9 5z" />
          <path d="M12 14l6.16-3.422a12.083 12.083 0 01.665 6.479A11.952 11.952 0 0012 20.055a11.952 11.952 0 00-6.824-2.998 12.078 12.078 0 01.665-6.479L12 14z" />
        </svg>
      </div>
      <span class="text-xl font-bold text-slate-900">EadHub</span>
    </div>
    <button (click)="logout()" class="btn btn-ghost btn-sm text-slate-500">Sair</button>
  </header>

  <main class="flex-grow p-8 flex flex-col items-center justify-center text-center">
    <div class="max-w-2xl">
      <h1 class="text-4xl font-bold text-slate-900 mb-4">Olá! Você está autenticado.</h1>
      <p class="text-lg text-slate-600 mb-8">Esta é a área logada do portal EadHub. Em breve você poderá gerenciar seus cursos aqui.</p>
      <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div class="card bg-white border border-slate-200 shadow-sm p-6">
          <div class="text-blue-600 mb-4 flex justify-center">
            <svg class="h-8 w-8" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253" /></svg>
          </div>
          <h3 class="font-bold text-slate-900">Meus Cursos</h3>
        </div>
        <div class="card bg-white border border-slate-200 shadow-sm p-6 opacity-50">
          <div class="text-slate-400 mb-4 flex justify-center">
            <svg class="h-8 w-8" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" /></svg>
          </div>
          <h3 class="font-bold text-slate-400">Perfil</h3>
        </div>
        <div class="card bg-white border border-slate-200 shadow-sm p-6 opacity-50">
          <div class="text-slate-400 mb-4 flex justify-center">
            <svg class="h-8 w-8" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" /></svg>
          </div>
          <h3 class="font-bold text-slate-400">Relatórios</h3>
        </div>
      </div>
    </div>
  </main>
</div>
  `
})
export class HomeComponent {
  private readonly authService = inject(AuthService);

  logout() {
    this.authService.logout();
  }
}
