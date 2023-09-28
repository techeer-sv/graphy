import schedule
import subprocess


def crawl_jobkorea():
    subprocess.run(["python", "crawling_jobkorea.py"])


def crawl_saramin():
    subprocess.run(["python", "crawling_saramin.py"])


schedule.every().day.at("21:40").do(crawl_jobkorea)
schedule.every().day.at("21:40").do(crawl_saramin)

while True:
    schedule.run_pending()