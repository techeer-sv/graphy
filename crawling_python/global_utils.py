import os
from selenium import webdriver
from mysql.connector import connect


def get_database_connect():
    return connect(
        host="mysql",
        port=3306,
        user=os.getenv('DB_USERNAME'),
        password=os.getenv('DB_USER_PASSWORD'),
        database=os.getenv('DB_DATABASE')
    )


def get_driver():

    path = '/usr/bin/chromedriver'

    chrome_options = webdriver.ChromeOptions()
    chrome_options.add_argument("--headless")  # GUI 없이 실행
    chrome_options.add_argument("--no-sandbox")  # Docker 환경에서 필요한 설정
    chrome_options.add_argument("--disable-dev-shm-usage")  # 공유 메모리 사용 제한을 해제
    chrome_options.add_argument("--remote-debugging-port=9222")  # 디버깅 포트 설정
    chrome_options.add_argument("--disable-gpu")  # GPU 사용 비활성화 (가상 서버 등에서 필요)
    chrome_options.add_argument("--disable-extensions")
    chrome_options.add_argument("--log-level=DEBUG")
    chrome_options.add_argument(
        "user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.5938.92 Safari/537.36")

    service = webdriver.ChromeService(executable_path=path)
    driver = webdriver.Chrome(options=chrome_options, service=service)

    return driver
