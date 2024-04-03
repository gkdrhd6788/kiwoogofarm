from flask import Flask, jsonify, request
from flask_sqlalchemy import SQLAlchemy
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np
import random

# Flask 애플리케이션 생성
app = Flask(__name__)

# MySQL 데이터베이스 설정
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://root:ssafy@j10b303.p.ssafy.io/member'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
db = SQLAlchemy(app)


# 데이터베이스 모델 정의
class Crop(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    image_url = db.Column(db.Text, nullable=False)
    name = db.Column(db.String(100), nullable=False)
    temperature = db.Column(db.String(100), nullable=False)
    sunshine = db.Column(db.String(100), nullable=False)
    water_period = db.Column(db.String(100), nullable=False)
    difficulty = db.Column(db.String(100), nullable=False)
    grow_time = db.Column(db.String(100), nullable=False)
    humidity = db.Column(db.String(100), nullable=False)
    grow_start = db.Column(db.String(100), nullable=False)
    water_exit = db.Column(db.String(100), nullable=False)
    likes = db.Column(db.Integer, nullable=False)


class CropFavorite(db.Model):
    __tablename__ = 'cropfavorites'
    id = db.Column(db.BIGINT, primary_key=True)
    is_liked = db.Column(db.Boolean, nullable=False)
    user_id = db.Column(db.BIGINT, db.ForeignKey('user.id'), nullable=False)
    crop_id = db.Column(db.BIGINT, db.ForeignKey('crop.id'), nullable=False)


def string_to_number(value):
    if value == "상":
        return 3
    elif value == "중":
        return 2
    elif value == "하":
        return 1
    elif value == "최하":
        return 0
    else:
        raise ValueError("Invalid value: {}".format(value))


def difficulty_to_number(value):
    if value == "어려움":
        return 3
    elif value == "보통":
        return 2
    elif value == "쉬움":
        return 1
    else:
        raise ValueError("Invalid value: {}".format(value))


def season_to_number(value):
    if value == "봄, 가을":
        return 2.9
    elif value == "봄":
        return 4
    elif value == "여름":
        return 3
    elif value == "가을":
        return 2
    elif value == "겨울":
        return 1
    elif value == "무관":
        return 0
    else:
        raise ValueError("Invalid value: {}".format(value))


def get_liked_crops_from_database(user_id):
    crop_favorites = CropFavorite.query.filter_by(user_id=user_id, is_liked=True).all()
    liked_crops = []
    for cropfavorites in crop_favorites:
        crop = Crop.query.get(cropfavorites.crop_id)
        if crop:
            crop_dict = {
                "id": crop.id,
                "name": crop.name,
                "temperature": crop.temperature,
                "sunshine": crop.sunshine,
                "water_period": crop.water_period,
                "difficulty": crop.difficulty,
                "grow_time": crop.grow_time,
                "humidity": crop.humidity,
                "grow_start": crop.grow_start,
                "water_exit": crop.water_exit,
                "likes": crop.likes,
            }
            liked_crops.append(crop_dict)
    return liked_crops


def calculate_cosine_similarity(liked_crops, crops):
    liked_crop_features = np.array([[string_to_number(liked_crop["temperature"]),
                                     string_to_number(liked_crop["sunshine"]),
                                     string_to_number(liked_crop["water_period"]),
                                     difficulty_to_number(liked_crop["difficulty"]),
                                     string_to_number(liked_crop["grow_time"]),
                                     string_to_number(liked_crop["humidity"]),
                                     season_to_number(liked_crop["grow_start"]),
                                     string_to_number(liked_crop["water_exit"])]
                                    for liked_crop in liked_crops])

    liked_crop_ids = set(liked_crop["id"] for liked_crop in liked_crops)

    crops_features = np.array([[string_to_number(crop["temperature"]),
                                string_to_number(crop["sunshine"]),
                                string_to_number(crop["water_period"]),
                                difficulty_to_number(crop["difficulty"]),
                                string_to_number(crop["grow_time"]),
                                string_to_number(crop["humidity"]),
                                season_to_number(crop["grow_start"]),
                                string_to_number(crop["water_exit"])]
                                for crop in crops if crop["id"] not in liked_crop_ids])

    cosine_similarities = cosine_similarity(liked_crop_features, crops_features)

    similar_crops_indices = np.argsort(cosine_similarities, axis=1)[:, ::-1]
    unique_indices = []
    for indices in similar_crops_indices:
        for index in indices:
            if index not in unique_indices:
                unique_indices.append(index)
            if len(unique_indices) == 3:  # 중복을 제거한 후 상위 3개의 작물만 추천
                break
        if len(unique_indices) == 3:
            break

    recommended_crops = [crops[index] for index in unique_indices]

    return recommended_crops


def calculate_euclidean_distance(liked_crops, crops):
    liked_crop_features = np.array([[string_to_number(liked_crop["temperature"]),
                                     string_to_number(liked_crop["sunshine"]),
                                     string_to_number(liked_crop["water_period"]),
                                     difficulty_to_number(liked_crop["difficulty"]),
                                     string_to_number(liked_crop["grow_time"]),
                                     string_to_number(liked_crop["humidity"]),
                                     season_to_number(liked_crop["grow_start"]),
                                     string_to_number(liked_crop["water_exit"])]
                                    for liked_crop in liked_crops])

    euclidean_distances = []

    liked_crop_ids = set(liked_crop["id"] for liked_crop in liked_crops)

    for crop in crops:
        if crop["id"] not in liked_crop_ids:
            crop_vector = np.array([string_to_number(crop["temperature"]),
                                    string_to_number(crop["sunshine"]),
                                    string_to_number(crop["water_period"]),
                                    difficulty_to_number(crop["difficulty"]),
                                    string_to_number(crop["grow_time"]),
                                    string_to_number(crop["humidity"]),
                                    season_to_number(crop["grow_start"]),
                                    string_to_number(crop["water_exit"])])

            distance = np.linalg.norm(liked_crop_features - crop_vector)
            euclidean_distances.append((crop, distance))

    sorted_distances = sorted(euclidean_distances, key=lambda x: x[1])
    recommended_crops = [crop for crop, _ in sorted_distances[:3]]

    return recommended_crops


def inside_calculate_euclidean_distance(liked_crops, crops):
    liked_crop_features = np.array([[string_to_number(liked_crop["temperature"]),
                                     string_to_number(liked_crop["sunshine"]),
                                     string_to_number(liked_crop["water_period"]),
                                     difficulty_to_number(liked_crop["difficulty"]),
                                     string_to_number(liked_crop["grow_time"]),
                                     string_to_number(liked_crop["humidity"])]
                                    for liked_crop in liked_crops])

    euclidean_distances = []

    for crop in crops:
            crop_vector = np.array([string_to_number(crop["temperature"]),
                                    string_to_number(crop["sunshine"]),
                                    string_to_number(crop["water_period"]),
                                    difficulty_to_number(crop["difficulty"]),
                                    string_to_number(crop["grow_time"]),
                                    string_to_number(crop["humidity"])])

            distance = np.linalg.norm(liked_crop_features - crop_vector)
            euclidean_distances.append((crop, distance))

    sorted_distances = sorted(euclidean_distances, key=lambda x: x[1])
    recommended_crops = [crop for crop, _ in sorted_distances[:6]]

    return recommended_crops


def outside_calculate_euclidean_distance(liked_crops, crops):
    liked_crop_features = np.array([[season_to_number(liked_crop["grow_start"]),
                                     string_to_number(liked_crop["sunshine"]),
                                     string_to_number(liked_crop["water_period"]),
                                     difficulty_to_number(liked_crop["difficulty"]),
                                     string_to_number(liked_crop["grow_time"]),
                                     string_to_number(liked_crop["water_exit"])]
                                    for liked_crop in liked_crops])

    euclidean_distances = []

    for crop in crops:
            crop_vector = np.array([season_to_number(crop["grow_start"]),
                                    string_to_number(crop["sunshine"]),
                                    string_to_number(crop["water_period"]),
                                    difficulty_to_number(crop["difficulty"]),
                                    string_to_number(crop["grow_time"]),
                                    string_to_number(crop["water_exit"])])

            distance = np.linalg.norm(liked_crop_features - crop_vector)
            euclidean_distances.append((crop, distance))

    sorted_distances = sorted(euclidean_distances, key=lambda x: x[1])
    recommended_crops = [crop for crop, _ in sorted_distances[:6]]

    return recommended_crops


def top_recommend(crops):
    sorted_crops = sorted(crops, key=lambda x: x['likes'], reverse=True)

    # 상위 3개의 요소 선택
    top_recommendations = sorted_crops[:3]

    return top_recommendations


@app.route('/crop/<int:user_id>', methods=['GET'])
def get_euclidean_recommended_crop(user_id):

    liked_crops = get_liked_crops_from_database(user_id)

    all_crops = Crop.query.all()
    crops = []
    for crop in all_crops:
        crop.dict ={
            "id": crop.id,
            "name": crop.name,
            "image_url": crop.image_url,
            "temperature": crop.temperature,
            "sunshine": crop.sunshine,
            "water_period": crop.water_period,
            "difficulty": crop.difficulty,
            "grow_time": crop.grow_time,
            "humidity": crop.humidity,
            "grow_start": crop.grow_start,
            "water_exit": crop.water_exit,
            "likes": crop.likes
        }
        crops.append(crop.dict)

    if not liked_crops:
        recommended_crop = top_recommend(crops)
    else:
        recommended_crop = calculate_euclidean_distance(liked_crops, crops)

    return jsonify({"recommended_crop": recommended_crop})


@app.route('/insideCrop', methods=['POST'])
def post_euclidean_recommended_inside_crop():

    liked_crops = request.json.get('liked_crops')

    all_crops = Crop.query.all()
    crops = []
    for crop in all_crops:
        crop.dict ={
            "id": crop.id,
            "name": crop.name,
            "image_url": crop.image_url,
            "temperature": crop.temperature,
            "sunshine": crop.sunshine,
            "water_period": crop.water_period,
            "difficulty": crop.difficulty,
            "grow_time": crop.grow_time,
            "humidity": crop.humidity,
            "grow_start": crop.grow_start,
            "water_exit": crop.water_exit,
            "likes": crop.likes
        }
        crops.append(crop.dict)

    recommended_crop = inside_calculate_euclidean_distance(liked_crops, crops)

    return jsonify({"recommended_crop": recommended_crop})


@app.route('/outsideCrop', methods=['POST'])
def post_euclidean_recommended_outside_crop():

    liked_crops = request.json.get('liked_crops')

    all_crops = Crop.query.all()
    crops = []
    for crop in all_crops:
        crop.dict ={
            "id": crop.id,
            "name": crop.name,
            "image_url": crop.image_url,
            "temperature": crop.temperature,
            "sunshine": crop.sunshine,
            "water_period": crop.water_period,
            "difficulty": crop.difficulty,
            "grow_time": crop.grow_time,
            "humidity": crop.humidity,
            "grow_start": crop.grow_start,
            "water_exit": crop.water_exit,
            "likes": crop.likes
        }
        crops.append(crop.dict)

    recommended_crop = outside_calculate_euclidean_distance(liked_crops, crops)

    return jsonify({"recommended_crop": recommended_crop})


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8082, debug=True)
