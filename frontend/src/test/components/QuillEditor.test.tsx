import React from 'react';
import '@testing-library/jest-dom';
import { render, fireEvent, screen } from '@testing-library/react';
import { RecoilRoot, useRecoilValue } from 'recoil';
import { contentsState } from '../../Recoil';
import QuillEditor from '../../components/QuillEditor';
import { useEffect } from 'react';

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

const onChange = jest.fn();

export const RecoilObserver = ({ node, onChange }: any) => {
  const value = useRecoilValue(node);
  useEffect(() => onChange(value), [onChange, value]);
  return null;
};

test('QuillEditor 테스트', async () => {
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
