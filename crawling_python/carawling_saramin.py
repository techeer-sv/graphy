from global_utils import *
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from datetime import datetime, timedelta
from dotenv import load_dotenv
import re

load_dotenv()


def crawling_job_data(driver, page_number):
    url = f'https://www.saramin.co.kr/zf_user/jobs/public/list?page={page_number}&isAjaxRequest=y&cat_kewd=84%2C92%2C87%2C86&company_cd=0%2C1%2C2%2C3%2C4%2C5%2C6%2C7%2C9%2C10&panel_type=domestic&search_optional_item=n&search_done=y&panel_count=y&preview=y'
    driver.get(url)

    wait = WebDriverWait(driver, 10)

    try:
        companies = wait.until(EC.presence_of_all_elements_located(
            (By.CSS_SELECTOR, '.list_item .col.company_nm a.str_tit, .list_item .col.company_nm span.str_tit')))
        contents = wait.until(EC.presence_of_all_elements_located(
            (By.CSS_SELECTOR, '.list_body .col.notification_info .job_tit .str_tit')))
        urls = wait.until(EC.presence_of_all_elements_located(
            (By.CSS_SELECTOR, '.list_body .col.notification_info .job_tit a.str_tit')))
        dates = wait.until(EC.presence_of_all_elements_located(
            (By.CSS_SELECTOR, '.list_body .col.support_info .support_detail .date')))
    except:
        return None

    data_list = []

    for i in range(len(companies)):
        company = companies[i].text
        content = contents[i].text
        url = urls[i].get_attribute('href')
        date_text = dates[i].text

        match_d = re.search(r"D-(\d+)", date_text)

        match_date = re.search(r"~(\d+\.\d+)\((\w+)\)", date_text)

        if match_d:
            days_to_add = int(match_d.group(1))
            current_date = datetime.now()
            calculated_date = current_date + timedelta(days=days_to_add)
            date = calculated_date.strftime("%Y-%m-%d")
        elif match_date:
            month_day, day_of_week = match_date.groups()
            current_year = datetime.now().year
            date_text = f"{current_year}-{month_day}"
            date = datetime.strptime(date_text, "%Y-%m.%d").strftime("%Y-%m-%d")

        data_list.append((company, content, url, date))

    return data_list


def main():
    driver = get_driver()
    page_number = 1

    while True:
        job_data = crawling_job_data(driver, page_number)
        if job_data is None:
            break

        db = get_database_connect()
        cursor = db.cursor()
        insert_query = "INSERT INTO job (company_name, title, url, expiration_date) VALUES (%s, %s, %s, %s)"
        cursor.executemany(insert_query, job_data)
        db.commit()
        cursor.close()
        db.close()

        page_number += 1

    driver.quit()


if __name__ == "__main__":
    main()
