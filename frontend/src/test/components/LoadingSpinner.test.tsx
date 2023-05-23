import '@testing-library/jest-dom';
import { render, screen } from '@testing-library/react';
import LoadingSpinner from '../../components/LoadingSpinner';

test('renders LoadingSpinner correctly', () => {
  render(<LoadingSpinner />);

  const dotLoader = screen.getByTestId('DotLoader'); // 스피너 요소가 있는지 확인
  expect(dotLoader).toBeInTheDocument();

  const textElement = screen.getByText('로딩중 입니다. . .'); // 로딩중 텍스트가 있는지 확인
  expect(textElement).toBeInTheDocument();
});
