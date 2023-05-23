import '@testing-library/jest-dom';
import { render, screen, fireEvent } from '@testing-library/react';
import ErrorPage from '../../pages/ErrorPage';
import { BrowserRouter } from 'react-router-dom';

beforeEach(() => {
  window.alert = jest.fn();
});
test('ErrorPage 테스트', () => {
  render(
    <BrowserRouter>
      <ErrorPage />
    </BrowserRouter>,
  );
  expect(window.location.pathname).toBe('/');
});
