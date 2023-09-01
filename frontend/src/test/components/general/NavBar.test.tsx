import '@testing-library/jest-dom';
import { render, screen, fireEvent } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

import NavBar from '../../../components/general/NavBar';
import { searchTextState } from '../../../Recoil';
import { onChange, RecoilObserver } from '../../jest/RecoilObserver';

test('NavBar 테스트', () => {
  render(
    <RecoilRoot>
      <BrowserRouter>
        <RecoilObserver<string> node={searchTextState} onchange={onChange} />
        <NavBar />
      </BrowserRouter>
    </RecoilRoot>,
  );

  const Biglogo = screen.getByText('Graphy'); // Graphy 텍스트가 있는 요소
  expect(Biglogo).toBeInTheDocument(); // 그 요소가 있는지 확인(렌더링 테스트)

  fireEvent.click(Biglogo); // Biglogo를 클릭해봄
  expect(window.location.pathname).toBe('/'); // 메인페이지로 이동하는지 확인

  const Smalllogo = screen.getByText('G');
  expect(Smalllogo).toBeInTheDocument();

  fireEvent.click(Smalllogo);
  expect(window.location.pathname).toBe('/');

  const SearchInput = screen.getByPlaceholderText('search');
  expect(SearchInput).toBeInTheDocument();

  fireEvent.change(SearchInput, { target: { value: '바뀐 값' } });
  expect(onChange).toHaveBeenCalledTimes(2);
  expect(onChange).toHaveBeenCalledWith(''); // 렌더링 처음 상태
  expect(onChange).toHaveBeenCalledWith('바뀐 값'); // 바뀐 값

  const toWriteButton = screen.getByRole('button', { name: 'toWritePage' });
  expect(toWriteButton).toBeInTheDocument();
  fireEvent.click(toWriteButton);
  expect(window.location.pathname).toBe('/write');
});
