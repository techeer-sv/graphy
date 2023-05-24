import '@testing-library/jest-dom';
import { render, fireEvent, screen } from '@testing-library/react';
import React from 'react';
import { RecoilRoot } from 'recoil';

import QuillEditor from '../../components/QuillEditor';
import { contentsState } from '../../Recoil';
import { onChange, RecoilObserver } from '../jest/RecoilObserver';

jest.mock('react-quill', () => ({
  __esModule: true,
  default: React.forwardRef<
    HTMLButtonElement,
    { onChange: (text: string) => void }
  >((props, ref) => (
    <button
      ref={ref}
      data-testid="quill-mock"
      onClick={() => props.onChange('new text')}
      aria-label="mock-button"
      type="button"
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
