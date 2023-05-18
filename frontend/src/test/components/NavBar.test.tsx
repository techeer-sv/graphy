import '@testing-library/jest-dom';
import NavBar from '../../components/NavBar';
import { render, fireEvent } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

test('renders logo and navigates to main page on click', () => {
  const { getByText } = render(
    <RecoilRoot>
      <Router>
        <NavBar />
      </Router>
    </RecoilRoot>,
  );

  const logoButton = getByText('Graphy');
  expect(logoButton).toBeInTheDocument();

  fireEvent.click(logoButton);
  expect(window.location.pathname).toBe('/');
});
