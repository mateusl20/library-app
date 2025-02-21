import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';

export const roleGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  console.log('roleGuard called for route:', state.url);
  console.log('Required role:', route.data['requiredRole']);
  console.log('isAuthenticated:', authService.isAuthenticated());

  const requiredRole = route.data['requiredRole'] as string;
  const allowAnonymous = route.data['allowAnonymous'] as boolean;

  if (allowAnonymous) {
    return true;
  }

  if (authService.isAuthenticated()) {
    if (requiredRole && !authService.hasRole(requiredRole)) {
      console.log('User does not have required role');
      return router.createUrlTree(['/unauthorized']);
    }
    console.log('Access granted');
    return true;
  }

  console.log('User not authenticated, redirecting to login');
  return router.createUrlTree(['/login']);
};
