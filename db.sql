-- CREATE DATABASE ssafy;
-- SELECT VERSION();
use member;

drop table if exists CropFavorites;
drop table if exists GardenFavorites;
drop table if exists RecipeFavorites;
drop table if exists User;
drop table if exists Crop;

drop table if exists CropRecipe;
drop table if exists RecipeDetail;
drop table if exists Recipe;

drop table if exists Garden;


CREATE TABLE User (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255),
    password VARCHAR(255),
    nickname VARCHAR(50),
    image_url VARCHAR(255),
    age INT,
    role VARCHAR(20),
    social_type VARCHAR(20),
    social_id VARCHAR(255),
    refresh_token VARCHAR(255)
);


CREATE TABLE Crop (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    thumbnail_url VARCHAR(255),
    image_url VARCHAR(255),
    category VARCHAR(255),
    temperature VARCHAR(255),
    difficulty VARCHAR(10),
    sunshine VARCHAR(255),
    grow_time VARCHAR(255),
    height VARCHAR(255),
    water_period VARCHAR(255),
    humidity VARCHAR(255),
    is_hydroponics BOOLEAN,
    grow_start VARCHAR(255),
    water_exit VARCHAR(255),
    crop_info mediumtext,
    water_period_info VARCHAR(255),
    sunshine_info VARCHAR(255),
    humidity_info VARCHAR(255),
    temperature_info VARCHAR(255),
    effect_info VARCHAR(255),
    link VARCHAR(255),
    grow_info mediumtext
    
);

CREATE TABLE CropRecipe (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    crop_id BIGINT NOT NULL,
	recipe_id BIGINT NOT NULL
);

CREATE TABLE CropFavorites (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    is_liked BOOLEAN NOT NULL,
    user_id BIGINT NOT NULL,
    crop_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES User(id),
    FOREIGN KEY (crop_id) REFERENCES Crop(id)
);


CREATE TABLE Recipe (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    serialnum BIGINT,
    name VARCHAR(255),
    info VARCHAR(255),
    ingredients VARCHAR(255),
    cook VARCHAR(255),
    image_url VARCHAR(255),
    difficulty VARCHAR(255),
    likes INT
);

CREATE TABLE RecipeDetail (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    recipe_id BIGINT NOT NULL,
    recipe_order INT ,
    info mediumtext,
    image_url VARCHAR(255),
    FOREIGN KEY (recipe_id) REFERENCES Recipe(id)
);

CREATE TABLE RecipeFavorites (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    is_liked BOOLEAN NOT NULL,
	user_id BIGINT NOT NULL,
    recipe_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES User(id),
	FOREIGN KEY (recipe_id) REFERENCES Recipe(id)
);

CREATE TABLE Garden (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    address VARCHAR(255),
    area INT,
    price INT,
    tel VARCHAR(255),
	operator VARCHAR(255),
    facilites VARCHAR(255),
    apply VARCHAR(255),
    latitude DOUBLE,
    longtitude DOUBLE,
    soil_phase_symbol VARCHAR(255)
);

CREATE TABLE GardenFavorites (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    is_liked BOOLEAN NOT NULL,
    user_id BIGINT NOT NULL,
    garden_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES User(id),
    FOREIGN KEY (garden_id) REFERENCES Garden(id)
);

INSERT INTO Crop (name, thumbnail_url, image_url, category, temperature, difficulty, sunshine, grow_time, height, water_period, humidity, is_hydroponics, grow_start, water_exit, crop_info, water_period_info, sunshine_info, humidity_info, temperature_info, effect_info, link, grow_info)
VALUES
('감자', '감자 썸네일 URL', '감자 이미지 URL', '채소', '15-25°C', '보통', '상', '3-4', '30cm', '매일', '60-80%', true, '봄', '상', '감자는 마령서(馬鈴薯)·하지감자·북감저(北甘藷)로도 불리며, 페루·칠레 등의 안데스 산맥 원산지에서 주로 재배되는 온대 지방의 중요한 작물 중 하나입니다. 높이는 일반적으로 60∼100 cm이며, 특유의 향기가 나는데, 이는 그 독특한 특성 중 하나입니다. 땅속에 있는 줄기 마디에서 나온 기는줄기가 발달하여 덩이줄기를 형성합니다.

덩이줄기에는 오목하게 팬 눈 자국이 있으며, 그 자국에서는 작고 어린 싹이 돋아납니다. 땅 위 줄기의 단면은 둥글게 모가 져 있습니다. 잎은 줄기의 각 마디에서 나오며, 대개 3∼4쌍의 작은 잎으로 이루어진 겹잎이며 작은 잎 사이에는 다시 작은 조각잎이 붙습니다.

6월경에 잎겨드랑이에서 긴 꽃대가 나와 취산꽃차례를 이루며, 지름 2∼3 cm 정도의 작은 열매가 달린 별 모양의 5갈래로 얕게 갈라진 엷은 자주색 또는 흰색의 꽃이 핍니다. 꽃이 진 뒤에는 토마토와 유사한 작은 열매가 형성됩니다.

감자는 음식으로 삶아서 주식이나 간식으로 소비되며, 굽거나 기름에 튀겨 먹기도 합니다. 또한 소주와 알코올의 원료로 사용되며, 감자 녹말은 당면, 공업용 원료뿐만 아니라 사료로도 사용됩니다.

감자의 영양 성분은 덩이줄기에 수분 75%, 녹말 13∼20%, 단백질 1.5∼2.6%, 무기질 0.6∼1%, 환원당 0.03 mg, 비타민 C 10~30 mg이 포함되어 있습니다. 필수 아미노산은 밀가루보다 많이 함유되어 있습니다. 날감자 100g은 약 80 kcal의 열량이 있습니다.

덩이줄기의 싹이 돋는 부분에는 솔라닌(solanine, C45H73O15N)이라는 알칼로이드가 포함되어 있어 독성이 있으므로, 싹이 나거나 빛이 푸르게 변한 감자는 식용을 자제해야 합니다.', '감자는 다른 작물보다는 비교적 건조하게 기르는 식물입니다. 하지만 감자 싹이 올라오는 초창기인 4월 초와 덩이줄기가 커지는 시기인 5월 중순 ~ 6월 초 경에는 물을 많이 주어야 합니다. 덩이줄기가 성숙하는 시기와 수확을 앞둔 6월 중순부터는 건조한 것이 좋기 때문에 물관리에 신경을 써야 합니다.', '감자는 일조량이 많아야 잘 자랍니다.', '감자는 60~80%의 습도에서 잘 자랍니다.', '20~26℃의 온도에서 잘 자라요.', '감자는 영양가가 풍부하여 칼륨과 비타민 C를 공급하며, 항산화작용을 통해 면역력을 강화합니다. 식이 섬유 함량이 높아 소화를 촉진하고 포만감을 유지하며, 에너지를 공급하여 활동성을 높입니다. 그러나 감자 요리 방법에 따라 영양소 함량이 변할 수 있으므로 다양한 조리법으로 섭취하는 것이 이상적입니다.', '감자 링크', '
감자를 기르는 기본 단계는 다음과 같습니다:

토양 준비: 잘 배수되고 흙이 풍부한 토양을 선택하고, 흙을 풀어서 굳어진 부분을 풀어주고 비료를 섞어 준비합니다.

심기: 감자씨를 준비하고, 준비한 토양에 간격을 두고 심어줍니다. 감자씨는 눈이 있는 부분을 위로 하여 심는 것이 좋습니다.

관리: 감자는 흙이 잘 들어가 있어야 하며, 흙이 위로 나오면 다시 덮어줍니다. 필요에 따라 물을 주고, 잡초를 제거하며, 살충제나 비료를 주기적으로 사용하여 병해충으로부터 보호합니다.

수확: 감자는 보통 심은 후 2~4개월 후에 수확할 수 있습니다. 수확할 때에는 굴착기를 사용하거나 손으로 땅을 파서 감자를 수확합니다.

저장: 수확한 감자는 직사광선을 피하고 건조한 곳에 저장합니다. 햇볕에 오래 노출되면 푸른 부분이 생길 수 있으므로 주의해야 합니다.

위 단계를 따라가면서 적절한 관리를 하면 건강하고 맛있는 감자를 기를 수 있습니다.'),
 ('상추', '상추 썸네일 URL', '상추 이미지 URL', '채소', '15-20°C', '보통', '상', '1-2', '20-30cm', '2-3일마다', '70-80%', false, '봄', '중', '상추는 다양한 종류가 있으며, 샐러드나 쌈으로 많이 활용되는 작물입니다. 상추는 햇빛을 좋아하지만, 너무 강한 직사광선은 피해야 합니다.', '상추는 토양이 마르지 않도록 주기적으로 물을 주어야 합니다.', '상추는 일조량이 충분해야 잘 자랍니다.', '상추는 높은 습도에서 잘 자랍니다.', '상추는 서늘한 온도에서 잘 자라며, 고온에서는 잘 자라지 않습니다.', '상추는 신선한 녹색 채소로, 비타민 A와 C가 풍부하며, 식이섬유도 많아 건강에 좋습니다.', '상추 링크', '상추를 기르는 기본 단계는 토양 준비, 심기, 관리, 수확, 저장입니다.'),
 ('딸기', '딸기 썸네일 URL', '딸기 이미지 URL', '과실류', '10-25°C', '보통', '상', '3-6', '15-30cm', '3-5일마다', '70-80%', true, '봄', '상', '딸기는 달콤하고 맛있어 많은 사람들에게 사랑받는 과일입니다. 딸기는 비교적 온도가 낮은 환경에서 잘 자라며, 충분한 햇빛을 필요로 합니다.', '딸기는 토양이 마르지 않도록 주기적으로 물을 주되, 과습에 주의해야 합니다.', '딸기는 충분한 햇빛을 필요로 합니다.', '딸기는 적당한 습도에서 잘 자랍니다.', '딸기는 서늘한 온도에서 잘 자라며, 너무 높은 온도는 피해야 합니다.', '딸기는 비타민 C가 풍부하며, 항산화 물질도 많아 건강에 좋습니다.', '딸기 링크', '딸기를 기르는 기본 단계는 토양 준비, 심기, 관리, 수확, 저장입니다.');


select * from Crop;
