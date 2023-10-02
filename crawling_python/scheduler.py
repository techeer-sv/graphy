import pytz
import schedule
import subprocess

kst = pytz.timezone('Asia/Seoul')


def crawl_jobkorea():
    subprocess.run(["python", "crawling_jobkorea.py"])


def crawl_saramin():
    subprocess.run(["python", "crawling_saramin.py"])


schedule.every(3).days.at("06:00").do(crawl_saramin)
schedule.every(3).days.at("06:03").do(crawl_jobkorea)

if __name__ == "__main__":
    while True:
        schedule.run_pending()
