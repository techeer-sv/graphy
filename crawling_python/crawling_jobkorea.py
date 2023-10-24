import re
import time
from datetime import datetime, timedelta

from global_utils import *
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from dotenv import load_dotenv
from selenium.webdriver.support.wait import WebDriverWait

load_dotenv()

def crawling_job_data(driver, page_number, existing_contents):
    url = f'https://www.jobkorea.co.kr/Recruit/Joblist?menucode=local&localorder=1#anchorGICnt_{page_number}'
    driver.get(url)
    wait = WebDriverWait(driver, 10)

    if page_number == 1:
        duty_btn = driver.find_element(By.CSS_SELECTOR, 'p.btn_tit')
        duty_btn.click()

        dev_data_label = driver.find_element(By.CSS_SELECTOR, 'label[for="duty_step1_10031"]')
        dev_data_label.click()

        backend_dev = driver.find_element(By.XPATH, '//span[contains(text(), "백엔드개발자")]')
        backend_dev.click()

        frontend_dev = driver.find_element(By.XPATH, '//span[contains(text(), "프론트엔드개발자")]')
        frontend_dev.click()

        web_dev = driver.find_element(By.XPATH, '//span[contains(text(), "웹개발자")]')
        web_dev.click()

        app_dev = driver.find_element(By.XPATH, '//span[contains(text(), "앱개발자")]')
        app_dev.click()

        career_btn = driver.find_element(By.XPATH, '//p[contains(text(), "경력")]')
        career_btn.click()

        newbie_label = driver.find_element(By.XPATH, '//label[contains(@for, "career1") and .//span[text()="신입"]]')
        newbie_label.click()

        search_button = driver.find_element(By.ID, 'dev-btn-search')
        search_button.click()

        time.sleep(4)

    try:
        companies = wait.until(EC.presence_of_all_elements_located((By.CSS_SELECTOR, 'td.tplCo')))
        contents = wait.until(EC.presence_of_all_elements_located((By.CSS_SELECTOR, 'td.tplTit strong a.link')))
        dates = wait.until(EC.presence_of_all_elements_located((By.CSS_SELECTOR, 'span.date.dotum')))
        urls = wait.until(EC.presence_of_all_elements_located((By.CSS_SELECTOR, 'td.tplTit strong a.link')))
    except:
        return None

    data_list = []

    for i in range(len(companies)):
        company_name = companies[i].text.strip()
        content = contents[i].get_attribute("title")

        if not content:
            content = contents[i].text.strip()

        date_text = dates[i].text.strip()
        date_match = re.search(r"~(\d{2}/\d{2})\((\w+)\)", date_text)

        if date_match:
            month_day, day_of_week = date_match.groups()
            current_year = datetime.now().year
            date_text = f"{current_year}-{month_day}"
            expiration_date = datetime.strptime(date_text, "%Y-%m/%d")
        elif "오늘마감" in date_text:
            expiration_date = datetime.now()
        elif "내일마감" in date_text:
            expiration_date = datetime.now() + timedelta(days=1)
        elif "모레마감" in date_text:
            expiration_date = datetime.now() + timedelta(days=2)
        elif "상시채용" in date_text:
            expiration_date = datetime.max
        else:
            expiration_date = None

        if expiration_date:
            expiration_date = expiration_date.strftime("%Y-%m-%d")
        else:
            expiration_date = ""

        url = urls[i].get_attribute("href")

        if content not in existing_contents:
            data_list.append((company_name, content, expiration_date, url))

    return data_list

def main():
    driver = get_driver()
    page_nuber = 1

    db = get_database_connect()
    cursor = db.cursor()
    cursor.execute("SELECT title, expiration_date FROM job")
    existing_contents = {row[0]: row[1] for row in cursor.fetchall()}
    cursor.close()
    db.close()

    while True:
        job_data = crawling_job_data(driver, page_nuber, existing_contents.keys())
        if job_data is None:
            break

        db = get_database_connect()
        cursor = db.cursor()
        insert_query = "INSERT INTO job (company_name, title, expiration_date, url) VALUES (%s, %s, %s, %s)"
        cursor.executemany(insert_query, job_data)
        db.commit()

        for title, expiration_date in existing_contents.items():
            if expiration_date == "9999-12-31 00:00:00.000000" and title not in [content[1] for content in job_data]:
                cursor.execute("DELETE FROM job WHERE title = %s", (title,))
                db.commit()

        cursor.close()
        db.close()

        page_nuber += 1

    driver.quit()

if __name__ == "__main__":
    main()
