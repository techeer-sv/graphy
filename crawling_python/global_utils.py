import os
from selenium import webdriver
from mysql.connector import connect


def get_database_connect():
    return connect(
        host="localhost",
        port=3307,
        user=os.getenv('DB_USERNAME'),
        password=os.getenv('DB_USER_PASSWORD'),
        database=os.getenv('DB_DATABASE')
    )


def get_driver():
    options = webdriver.ChromeOptions()
    options.add_argument('--headless')
    return webdriver.Chrome(options=options)
