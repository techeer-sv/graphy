import { useEffect } from 'react';
import { useRecoilValue } from 'recoil';

export const onChange = jest.fn();

export const RecoilObserver = ({ node, onchange }: any) => {
  const value = useRecoilValue(node);
  useEffect(() => onChange(value), [onchange, value]);
  return null;
};
