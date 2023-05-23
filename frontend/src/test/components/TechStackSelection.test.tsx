import '@testing-library/jest-dom';
import { fireEvent, render, screen } from '@testing-library/react';
import TechStackSelection from '../../components/TechStackSelection';
import { RecoilRoot } from 'recoil';
import { selectedStackState } from '../../Recoil';
import { onChange, RecoilObserver } from '../jest/RecoilObserver';

describe('TechStackSelection', () => {
  test('TechStackSelection 테스트', () => {
    render(
      <RecoilRoot>
        <RecoilObserver node={selectedStackState} onChange={onChange} />
        <TechStackSelection />
      </RecoilRoot>,
    );

    const useTech = screen.getByText('사용 기술');
    expect(useTech).toBeInTheDocument();

    const searchTech = screen.getByPlaceholderText('기술 이름으로 검색');
    expect(searchTech).toBeInTheDocument();
    fireEvent.change(searchTech, { target: { value: 'react' } });
    const reactButton = screen.getByText('React 추가');
    expect(reactButton).toBeInTheDocument();
    const reactNativeButton = screen.getByText('ReactNative 추가');
    expect(reactNativeButton).toBeInTheDocument();
    fireEvent.click(reactButton);
    const deleteStack = screen.getByText('x');
    expect(reactNativeButton).toBeInTheDocument();
    fireEvent.click(deleteStack);

    expect(onChange).toHaveBeenCalledWith([]);
    expect(onChange).toHaveBeenCalledWith(['React']);
    expect(onChange).toHaveBeenCalledTimes(3);
  });
});
