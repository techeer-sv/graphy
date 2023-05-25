import '@testing-library/jest-dom';
import { render } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';

import ErrorPage from '../../pages/ErrorPage';

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
