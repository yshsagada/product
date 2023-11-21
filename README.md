# 상품 최저가 프로젝트

---
### 사용 기술
- Spring Boot 3.1.5
- Redis
- Kafka
- Test Containers
- gradle

### 로컬 실행 환경 (필요 조건)
- kafka (port: 9092)
- redis (port: 6380)
- h2 
- docker daemon
- docker compose 
  - redis, kafka 로컬 실행 -> 프로젝트 root내 docker-compose.yml 사용

### 실행
- 프로젝트 root 내의 docker-compose.yml 을 이용한 redis, kafka 실행
```shell
  docker-compose up -d
```
- build
```
  ./gradle build
```

- run
```shell
  java -jar build/libs/product-0.0.1-SNAPSHOT.jar
```
### 구현 기능
1. 고객은 카테고리 별로 최저가격인 브랜드와 가격을 조회하고 총액이 얼마인지 확인할 수 있어야 합니다.
2. 고객은 단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격인 브랜드와 총액이 얼마인지 확인할 수 있어야 합니다.
3. 고객은 특정 카테고리에서 최저가격 브랜드와 최고가격 브랜드를 확인하고 각 브랜드 상품의 가격을 확인할 수 있어야 합니다.
4. 운영자는 새로운 브랜드를 등록하고, 모든 브랜드의 상품을 추가, 변경, 삭제할 수 있어야 합니다.

### 기타 구현 사항
- swagger (로컬 실행시 http://localhost:8080/swagger-ui.html)
- 단위테스트 및 통합 테스트
- 통합테스트
  - Test Containers 라는 프레임 워크를 사용하여 통합 테스트를 시작하기전 컨테이너 프로세스를 띄우고 통합 테스트를 진행합니다.
  - 사용 이유는 테스트시에 사용하는 데이터를 서로 참조 하지 않게하였고 따로 redis,kafka를 실행 없이 작동 하기 위함입니다.
- 로컬 실행시 초기 데이터를 넣었습니다
  - data.sql (brand, product)
  - DataInitService 이용한 ZSET 데이터 초기화 -> local 실행시마다 local에 6380 레디스 초기화
### 도메인

- product (상품)
  - product_id (PK)
  - brand_id (브랜드 FK)
  - product_price (가격)
  - product_name (이름)
  - product_status (SERVICED - 서비스중, DELETED - 삭제 상태)
  - category_type (카테고리)
- brand (브랜드)
  - brand_id (PK)
  - brand_name (이름)
  - brand_status (SERVICED - 서비스중, DELETED - 삭제 상태)
- category (enum)
  - TOP("상의")
  - OUTER("아우터")
  - BOTTOM("바지")
  - SNEAKERS("스니커즈")
  - BAG("가방")
  - HAT("모자")
  - SOCKS("양말")
  - ACCESSORY("액세서리")

### 구현 제약 사항
- 상품 변경(update) 기능에서 카테고리 변경은 제한됩니다.
- 즉각 실시간 데이터 반영하여 조회가 되지 않을 수 있습니다.
- 상품과 브랜드 경우 DELETED 상태인 엔티티들을 배치 작업을 통해 Hard delete 작업이 필요합니다.
- ZSET 키 경우 상품이 많아질 수록 멤버가 상품 수 만큼 늘어납니다.
  - 일정 주기마다 최저 ~ 최고 사이 멤버들을 삭제하는 로직이 필요합니다.


### 구현 내용
- 최저가 검색 최적화를 위해 Redis Sorted Set 자료구조를 사용 했습니다.
- 상품의 이벤트나 브랜드의 이벤트가 발생시마다 이벤트를 발행하고 이벤트를 구독하여 특정 카테고리 또는 브랜드의 상품 가격을 ZSET score에 반영하여 빠르게 최저가, 최고가를 검색할 수 있도록 구현 했습니다.
- 구현에 사용한 Sorted Set 자료구조 키
  - 특정 카테고리 상품의 가격 정렬 키
    - key : category:{카테고리타입} (ex: category:TOP)
    - member : productId
    - score : product price
  - 특정 브랜드의 카테고리별 가격 정렬 키
    - key : brand:{카테고리타입} (ex: brand:TOP)
    - member : productId:brandId (ex: 1:1)
    - score : product price
  - 특정 브랜드의 카테고리별 최저가 상품 전체 가격 합 정렬 키
    - key: brand-total
    - member: brandId
    - score: totalPrice (특정 브랜드 모든 카테고리 최저가 상품 가격 전체 합)
  
- 스프링 이벤트를 이용하여 로직을 분리하였고 트랜잭션 제어하여 이벤트를 발행 했습니다.
- 이벤트 리스너 전용 쓰레드풀을 설정하여 리스너에 @Async 사용시에 성능을 높였습니다.
- 이벤트 리스너에서 카프카를 이용한 큐에 이벤트를 produce 처리하였습니다. 
  - 특정 상품에 이벤트 순서를 큐의 파티션을 이용해 보장하려고 사용했지만 다른 처리가 필요하지 않다면 카프카를 이용한 큐를 사용하지 않아도 될것 같습니다.
