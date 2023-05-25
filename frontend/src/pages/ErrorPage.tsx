import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function ErrorPage(): JSX.Element {
  const navigate = useNavigate();
  alert('없거나 삭제된 페이지입니다.');
  useEffect(() => {
    navigate('/');
  }, []);

  return <div />;
}

export default ErrorPage;
