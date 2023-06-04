import { useEffect } from 'react';
import { RecoilState, useRecoilValue } from 'recoil';

interface RecoilObserverProps<T> {
  node: RecoilState<T>;
  onchange: (newValue: T) => void;
}

export const onChange = jest.fn();

export const RecoilObserver = <T,>({
  node,
  onchange,
}: RecoilObserverProps<T>) => {
  const value = useRecoilValue(node);
  useEffect(() => onChange(value), [onchange, value]);
  return null;
};
