import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';

export const roleGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const requiredRole = route.data['requiredRole'] as string;
  const allowAnonymous = route.data['allowAnonymous'] as boolean;

  if (allowAnonymous) {
    return true;
  }

  if (authService.isAuthenticated()) {
    if (requiredRole && !authService.hasRole(requiredRole)) {
      return router.createUrlTree(['/unauthorized']);
    }
    return true;
  }

  return router.createUrlTree(['/login']);
};
