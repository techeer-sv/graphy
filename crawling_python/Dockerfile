FROM python:3.11

RUN python -m venv /venv
ENV PATH="/venv/bin:$PATH"

# 기본 작업 디렉토리 설정
COPY . /app/
WORKDIR /app

# 필요한 패키지 설치
RUN apt-get update && apt-get install -y wget unzip chromium chromium-driver

# Python 패키지 설치
RUN pip install --no-cache-dir -r requirements.txt

CMD ["python", "scheduler.py"]
