# 필요한 패키지 불러오기
from selenium import webdriver
import requests, json
from bs4 import BeautifulSoup
import pandas as pd

from selenium import webdriver
from selenium.webdriver.common.by import By
from tqdm.notebook import tqdm

# # 1페이지에 있는 url 크롤링
#
# 크롬창 띄우기
options = webdriver.ChromeOptions()
options.add_argument('headless')
options.add_argument('window-size=1920x1080')
options.add_argument("disable-gpu")
driver = webdriver.Chrome(options=options)

for n in range(0, 529):
    url_load = pd.read_csv("10000_recipe_url_크롤링_%s.csv" % n, encoding='utf-8')  # url파일 불러오기 - 1,2,3 파일

    num_list = len(url_load)
    print(num_list)
    # url_load

    dict = {}  # 전체 크롤링 데이터를 담을 그릇
    detail_dict = {}
    number = 400  # 수집할 글 갯수 정하기
    # 수집한 url 돌면서 데이터 수집
    for i in tqdm(range(0, number)):
        url = url_load['url'][i]
        driver = webdriver.Chrome(options=options)
        driver.get(url)  # 글 띄우기

        # 크롤링
        try:
            target_info = {}  # 개별 레시피 내용을 담을 딕셔너리 생성

            overlays = "txtRecipeId"  # 요리 시리얼 번호
            sNum = driver.find_element(By.ID, overlays)
            serialNum = sNum.get_attribute('innerText')
            serialNum = serialNum[1:]

            # overlays = "div.view2_summary.st3>h3"  # 요리 제목
            # tit = driver.find_element(By.CSS_SELECTOR, overlays)
            # title = tit.text

            # overlays = "recipeIntro"  # 요리 간단 소개
            # tit = driver.find_element(By.ID, overlays)
            # titleinfo = tit.text

            # overlays = "div.ready_ingre3"  # 재료 목록
            # ingre = driver.find_element(By.CSS_SELECTOR, overlays)
            # ingredient = ingre.text

            # 요리 방식

            overlays1 = "div.centeredcrop>img"  # 요리 대표 사진
            img = driver.find_element(By.CSS_SELECTOR, overlays1)  # 요리 대표 사진 선택
            pic = img.get_attribute('src')  # 사진 url 크롤링

            # overlays = "view2_summary_info3"  # 난이도
            # diff = driver.find_element(By.CLASS_NAME, overlays)
            # difficulty = diff.text

            # 추천수

            # ===== 레시피 상세 파일 =====
            recipe_detail = {}

            # overlays = "div.view_step"  # 내용 크롤링
            # contents = driver.find_elements(By.CSS_SELECTOR, overlays)
            response = requests.get(url)
            html = response.text
            soup = BeautifulSoup(html, 'html.parser')
            food_info = soup.find(attrs={'type': 'application/ld+json'})
            result = json.loads(food_info.text)
            # ingredient = ','.join(result['recipeIngredient'])
            recipe = [result['recipeInstructions'][i]['text'] for i in range(len(result['recipeInstructions']))]  # 레시피 순서, 내용
            images = [result['recipeInstructions'][i]['image'] for i in range(len(result['recipeInstructions']))]  # 레시피 사진
            order = []
            for j in range(len(recipe)):
                # recipe[i] = f'{i + 1}. ' + recipe[i]
                order.append(j+1)
                # print(j)
                recipe_detail['order'] = order[j]
                recipe_detail['recipe'] = recipe[j]
                recipe_detail['images'] = images[j]
                recipe_detail['serialNum'] = serialNum
                # print("recipe_detail")
                # print(recipe_detail)
                # print(len(detail_dict)+1)
                detail_dict[len(detail_dict)+1] = recipe_detail.copy()
                # print("detail_dict")
                # print(detail_dict)
                # time.sleep(0.1)
            print(i, order)

            # content_list = []
            # for content in contents:
            #     content_list.append(content.text)

            # content_str = ' '.join(content_list)  # content_str

            # # 글 하나는 target_info라는 딕셔너리에 담기게 되고,
            # target_info['title'] = title
            # target_info['ingredient'] = ingredient
            target_info['pic'] = pic
            # # target_info['content'] = content_str
            target_info['serial_num'] = serialNum
            #
            # # 각각의 글은 dict라는 딕셔너리에 담기게 됩니다.
            dict[i] = target_info
            # time.sleep(0.1)
            #
            # # 크롤링이 성공하면 글 제목을 출력하게 되고,
            # print(i, title)

            # 글 하나 크롤링 후 크롬 창을 닫습니다.
            driver.close()

        except Exception as e:
            print(e, 'This page is error :', i)
            driver.close()
            # time.sleep(0.1)
            continue

        # 에러나면 현재 크롬창을 닫고 다음 글(i+1)로 이동합니다.

        # 중간,중간에 파일로 저장하기
        # if i == 30 or 50 or 80:
        #     # 판다스로 만들기
        #     import pandas as pd
        #
        #     result_df = pd.DataFrame.from_dict(dict, 'index')
        #
        #     # 저장하기
        #     result_df.to_excel("10000_recipe_크롤링.xlsx", encoding='utf-8-sig')
        #     time.sleep(3)

    result_df = pd.DataFrame.from_dict(dict, 'index')
    detail_df = pd.DataFrame.from_dict(detail_dict, 'index')


    # 저장하기
    result_df.to_excel("10000_recipe_크롤링_%s.xlsx" % n)
    detail_df.to_excel("10000_recipe_상세_크롤링_%s.xlsx" % n)
    # time.sleep(0.1)

    print('수집한 글 갯수: ', len(dict))
    # print(dict)
