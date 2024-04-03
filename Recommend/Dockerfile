# 베이스 이미지 설정
FROM python:3.12.0

# 작업 디렉토리 설정
WORKDIR /app

# 필요한 파일 복사
COPY requirements.txt .

# 의존성 설치
RUN pip install --no-cache-dir -r requirements.txt

# 애플리케이션 소스 코드 복사
COPY . .

EXPOSE 8082

# Flask 애플리케이션 실행
CMD ["python", "app.py"]
