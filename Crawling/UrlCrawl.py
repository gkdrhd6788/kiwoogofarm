import pandas as pd
from selenium import webdriver
from selenium.webdriver.common.by import By

# 1페이지에 있는 url 크롤링

# 크롬창 띄우기
options = webdriver.ChromeOptions()
options.add_argument('headless')
options.add_argument('window-size=1920x1080')
options.add_argument("disable-gpu")
driver = webdriver.Chrome(options=options)

dict = {}  # 전체 데이터를 담을 딕셔너리 생성

# 레시피 창 url크롤링
url_list = []
title_list = []

# 240305_0920 기준 총 5445 페이지 - 5,000 페이지(40*5,000 = 200,000개) 크롤 예정 - 0318_1632 5473개
# 191페이지부터 231130 데이터 시작(번호 7014707 - 전자레인지 계란찜 만드는법) - 데이터 몇 개 쓸까?
# 10페이지 단위로 끊어서 다운로드 받기 - 40개*10페이지*10초 = 4000초 = 약 67분(약 1시간)
# i: 0~528
for i in range(0, 529):
    for page in range(195+10*i, 195+10*(i+1)):
        driver.get("https://www.10000recipe.com/recipe/list.html?order=date&page=%s" % page)
        # time.sleep(0.1)

        count = 0
        while count < 1:
            count = count + 1
            # URL_raw 크롤링 시작
            articles = "a.common_sp_link"  # 중간에 띄어쓰기는 .으로 대체, selector는 >로 이동
            article_raw = driver.find_elements(By.CSS_SELECTOR, articles)

            # 크롤링한 url 정제 시작
            for article in article_raw:
                url = article.get_attribute('href')
                url_list.append(url)
                # print(url)
        print(page)

    print("")
    print('url갯수: ', len(url_list))
    # print('title갯수: ', len(title_list))

    df = pd.DataFrame({'url': url_list})
    df.to_csv("10000_recipe_url_크롤링_%s.csv" % i, encoding='utf-8')  # csv파일로 따로 저장
    url_list = []
    print('i: ', i)
