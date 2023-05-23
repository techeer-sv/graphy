import React from 'react';
import '@testing-library/jest-dom';
import { render, fireEvent, screen } from '@testing-library/react';
import { RecoilRoot } from 'recoil';
import { contentsState } from '../../Recoil';
import QuillEditor from '../../components/QuillEditor';
import { onChange, RecoilObserver } from '../jest/RecoilObserver';

jest.mock('react-quill', () => ({
  __esModule: true,
  default: React.forwardRef<
    HTMLDivElement,
    { onChange: (text: string) => void }
  >((props, ref) => (
    <div
      ref={ref}
      data-testid="quill-mock"
      onClick={() => props.onChange('new text')}
    />
  )),
}));

test('QuillEditor 테스트', () => {
  render(
    <RecoilRoot>
      <RecoilObserver node={contentsState} onChange={onChange} />
      <QuillEditor />
    </RecoilRoot>,
  );

  fireEvent.click(screen.getByTestId('quill-mock'));

  expect(onChange).toHaveBeenCalledWith('');
  expect(onChange).toHaveBeenCalledWith('new text');
  expect(onChange).toHaveBeenCalledTimes(2);
});
