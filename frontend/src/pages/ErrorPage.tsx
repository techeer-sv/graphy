import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function ErrorPage() {
  const navigate = useNavigate();
  alert('없거나 삭제된 페이지 입니다.');
  useEffect(() => {
    navigate('/');
  }, []);
  return <></>;
}

export default ErrorPage;
