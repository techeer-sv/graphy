FROM python:3.11

RUN python -m venv /venv
ENV PATH="/venv/bin:$PATH"

COPY . /app/
WORKDIR /app

RUN apt-get update && apt-get install -y chromium chromium-driver

RUN pip install --no-cache-dir -r requirements.txt

CMD ["python", "scheduler.py"]
