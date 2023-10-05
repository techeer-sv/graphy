import pytz
import schedule
import subprocess

from crawling_python.global_utils import get_database_connect

kst = pytz.timezone('Asia/Seoul')

def delete_expired_data(today):
    kst = pytz.timezone('Asia/Seoul')
    connect = get_database_connect()
    cursor = connect.cursor()

    query = "DELETE FROM job WHERE expiration_date < %s"
    cursor.execute(query, (today,))

    connect.commit()
    cursor.close()
    connect.close()

def crawl_jobkorea():
    subprocess.run(["python", "crawling_jobkorea.py"])


def crawl_saramin():
    subprocess.run(["python", "crawling_saramin.py"])


schedule.every().day.at("00:00").do(crawl_saramin)
schedule.every().day.at("00:03").do(crawl_jobkorea)
schedule.every().day.at("00:05").do(delete_expired_data)

if __name__ == "__main__":
    while True:
        schedule.run_pending()
