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
    chrome_options.add_argument("--headless")
    chrome_options.add_argument("--no-sandbox")
    chrome_options.add_argument("--disable-dev-shm-usage")
    chrome_options.add_argument("--remote-debugging-port=9222")
    chrome_options.add_argument("--disable-gpu")
    chrome_options.add_argument("--disable-extensions")
    chrome_options.add_argument("--log-level=DEBUG")
    chrome_options.add_argument(
        "user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.5938.92 Safari/537.36")

    service = webdriver.ChromeService(executable_path=path)
    driver = webdriver.Chrome(options=chrome_options, service=service)

    return driver
