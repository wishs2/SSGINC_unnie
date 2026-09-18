# SSGINC_unnie

> This repository is a personal fork of the original team project for refactoring and study purposes.

> 이 레포지토리는 리팩토링 및 공부를 위한 기존 팀 프로젝트의 개인 포크입니다.

![image](https://github.com/user-attachments/assets/4c4f5183-9d08-433f-8639-abf8835aea47)

위치 및 영수증 기반 뷰티 업체 리뷰 커뮤니티 웹 플랫폼 **"언니어때"** 입니다.

---

## 프로젝트 정보
- **프로젝트 기간:** 2025년 2월 11일 ~ 2025년 3월 27일
- **프로젝트 형태:** 팀 프로젝트

> 프로젝트 종료 후 OCR 데이터 처리 구조를 개인적으로 리팩토링했습니다.
> 
> 해당 내용은 Portfolio의 Problem Solving에서 자세히 확인할 수 있습니다.

---

## 목차

- [프로젝트 정보](#프로젝트-정보)
- [1. 프로젝트 소개](#1-프로젝트-소개)
  - [팀원 구성](#팀원-구성)
  - [팀원별 개발 내용](#팀원별-개발-내용)
- [2. 아키텍쳐와 브랜치 전략](#2-아키텍쳐와-브랜치-전략)
  - [아키텍쳐](#아키텍쳐)
  - [브랜치 전략](#브랜치-전략)
  - [배포(재배포 예정)](#배포재배포-예정)
- [3. 개발 환경](#3-개발-환경)
  - [사용한 외부 API](#사용한-외부-api)
- [4. 프로젝트 구조](#4-프로젝트-구조)
- [5. UI 구성 및 기능](#5-ui-구성-및-기능)
  - [기능](#기능)
    - [회원가입](#회원가입)
    - [소셜로그인](#소셜로그인)
    - [ID/PW 찾기](#idpw-찾기)
    - [내주변 뷰티샵 조회](#내주변-뷰티샵-조회)
    - [리뷰 작성](#리뷰-작성)
  - [마이페이지](#마이페이지)
    - [업체 등록](#업체-등록)
    - [디자이너 등록](#디자이너-등록)
    - [시술 등록](#시술-등록)
    - [회원 정보 수정](#회원-정보-수정)
    - [회원 탈퇴](#회원-탈퇴)
    - [리뷰 수정/삭제](#리뷰-수정삭제)
    - [업체/디자이너/시술 수정/삭제](#업체디자이너시술-수정삭제)
  - [관리자페이지](#관리자페이지)
    - [모든 회원 조회](#모든-회원-조회)
    - [승인 요청 업체 조회/승인/거절](#승인-요청-업체-조회승인거절)
    - [모든 업체 조회](#모든-업체-조회)
- [6. Problem Solving](#problem-solving)
  - [OCR 데이터 처리 구조 개선](#ocr-데이터-처리-구조-개선)
  - [데이터 무결성 설계](#데이터-무결성-설계)
  - [LLM 리뷰 요약 처리 구조 개선](#llm-리뷰-요약-처리-구조-개선)
  - [리뷰 조회 API 성능 검증](#리뷰-조회-api-성능-검증)
- [7. Testing & Collaboration](#testing--collaboration)
- [8. 시연 영상](#시연-영상)


<br>

---

## 1. 프로젝트 소개

- 이 프로젝트는 **영수증 기반 리뷰 시스템**을 통해 거짓 리뷰를 최소화하고, 실제 이용자만 리뷰를 작성할 수 있도록 보장함으로써 신뢰성 높은 정보를 제공합니다.
- 사용자의 현재 위치 또는 특정 지역을 기반으로 원하는 뷰티샵을 쉽게 검색할 수 있으며, **필터링 기능**과 **지도 연동**을 통해 직관적으로 샵 위치를 파악하고 맞춤형 검색을 지원합니다.

<br>


## 팀원 구성

<div align="center">

| **김동현** | **민소원** | **이가영** | **이상우** |
| :------: | :------: | :------: | :------: |
| [@DHKim96](https://github.com/DHKim96) | [@wishs2](https://github.com/wishs2) | [@GaYoung28](https://github.com/GaYoung28) | [@sangwooLee1231](https://github.com/sangwooLee1231) |

</div>

### 팀원별 개발 내용

- **김동현**
  - 프로젝트 초기 설계
  - 스켈레톤 코드 작성

- **민소원**
  - 팀 리드 및 WBS 작성, 일정 관리
  - 영수증 기반 리뷰 작성 기능 및 NCP OCR API 연동
  - OCR 응답 기반 영수증 데이터 추출 및 파싱
  - 리뷰 조회/수정/삭제 및 작성자 기준 접근 제어
  - 영수증·리뷰 중복 방지를 위한 DB 무결성 설계
  - 영수증 식별정보 기반 복합 UNIQUE 제약조건 적용
  - LLM 기반 리뷰 요약 및 Scheduler 처리 구조 구현
  - Redis Set을 활용한 동일 업체 요약 대상 중복 제거
  - 영수증 관련 테이블 및 컬럼 설계
  - JUnit / Mockito 기반 비즈니스 로직 테스트
  - JMeter를 활용한 리뷰 조회 API 성능 검증
  - GitHub Branch / Pull Request / Code Review 기반 협업

- **이가영**
  - DB설계
  - 회원가입 및 로그인(JWT)
  - oauth2.0 로그인
  - ID/PW 찾기
  - 회원 정보 수정
  - 회원 탈퇴
  - 모든 회원 조회
  - 관리자 페이지
  - 메인 페이지

- **이상우**
  - 내주변 뷰티샵 조회
  - 업체/디자이너/시술 등록
  - 내 업체 조회
  - 업체/디자이너/시술 수정/삭제
  - 모든 업체 조회
  - 승인 요청 업체 조회/승인/거절
  - HTTPS 웹 서버 구축
  - ci/cd

<br>

---

## 2. 아키텍쳐와 브랜치 전략

### 아키텍쳐

<img width="9416" alt="최종 아키텍처" src="https://github.com/user-attachments/assets/2ba8c618-b078-4769-99a2-06c255a2b0c3" />

<br>

### 브랜치 전략

- Git-flow 전략을 기반으로 main, develop 브랜치와 feature 보조 브랜치를 운용했습니다.
- main, develop, Feat 브랜치로 나누어 개발을 하였습니다.
    - **main** 브랜치는 배포 단계에서만 사용하는 브랜치입니다.
    - **develop** 브랜치는 개발 단계에서 git-flow의 master 역할을 하는 브랜치입니다.
    - **Feat** 브랜치는 기능 단위로 독립적인 개발 환경을 위하여 사용하고 merge 후 각 브랜치를 삭제해주었습니다.

<br>


### 배포(재배포 예정)

1. **URL**
   - https://www.unnieuttae.store
   - 테스트용 계정
      - id: test@example.com
      - pwd: 1234

2. **GitHub Actions 기반의 CI/CD Pipeline 구축**

- Git, GitHub Actions, AWS Elastic Beanstalk(EC2: Nginx-Tomcat, 오토스케일링/로드밸런싱 지원), RDS(MySQL), Docker/Redis, S3를 연계하여 자동 빌드/무중단 배포를 구축하였습니다.

> 배포 및 CI/CD 구축은 팀원이 담당했으며, 위 내용은 프로젝트 전체의 배포 환경을 설명합니다.


<br>

---

## 3. 개발 환경

- **IDE:**
  [![Visual Studio Code](https://img.shields.io/badge/Visual%20Studio%20Code-0078d7?style=flat-square&logo=visual%20studio%20code&logoColor=white)](https://code.visualstudio.com/)
  [![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-000000?style=flat-square&logo=intellij-idea&logoColor=white)](https://www.jetbrains.com/idea/)


- **Frontends:**
  <img src="https://img.shields.io/badge/html5-E34F26?style=flat-square&logo=html5&logoColor=white"/>
  <img src="https://img.shields.io/badge/css3-1572B6?style=flat-square&logo=css3&logoColor=white"/>
  <img src="https://img.shields.io/badge/javascript-F7DF1E?style=flat-square&logo=javascript&logoColor=white"/>

- **Backends:**
  [![Java](https://img.shields.io/badge/Java-007396?style=flat-square&logo=java&logoColor=white)](https://www.oracle.com/java/)
  <img src="https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=Spring&logoColor=white"/>
  <img src="https://img.shields.io/badge/Mybatis-000000?style=flat-square&logo=Mybatis&logoColor=white"/>

- **Database:**
  [![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white)](https://www.mysql.com/)
  [![Redis](https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=redis&logoColor=white)](https://redis.io/)

- **Collaborates:**
  [![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=github&logoColor=white)](https://github.com/)
  [![Slack](https://img.shields.io/badge/Slack-4A154B?style=flat-square&logo=slack&logoColor=white)](https://slack.com/)
  [![Notion](https://img.shields.io/badge/Notion-000000?style=flat-square&logo=notion&logoColor=white)](https://www.notion.so/)

- **DevOps/ Infra:**
  [![AWS](https://img.shields.io/badge/AWS-232F3E?style=flat-square&logo=amazon-aws&logoColor=white)](https://aws.amazon.com/)
  [![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white)](https://www.docker.com/)


### 사용한 외부 API

 
- **API**
   - 다음카카오 주소 API
   - Google/Kakao/Naver 로그인 API
   - CoolSMS 핸드폰 문자인증 API
   - NCP OCR API
   - LLM API
   - NCP GeoCoding API
   - 공공데이터 포털 사업자 진위여부확인 API

<br>

---

## 4. 프로젝트 구조


```
📦src
 ┣ 📂main
 ┃ ┣ 📂generated
 ┃ ┣ 📂java
 ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┗ 📂ssginc
 ┃ ┃ ┃ ┃ ┗ 📂unnie
 ┃ ┃ ┃ ┃ ┃ ┣ 📂admin
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂member
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂report
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂shop
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂mapper
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂serviceImpl
 ┃ ┃ ┃ ┃ ┃ ┣ 📂common
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂converter
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂exception
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂handler
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂interceptor
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂listener
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂redis
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂util
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂generator
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂parser
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂validation
 ┃ ┃ ┃ ┃ ┃ ┣ 📂community
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂board
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂comment
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂member
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂mapper
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂serviceImpl
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂vo
 ┃ ┃ ┃ ┃ ┃ ┣ 📂like
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂mapper
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂serviceImpl
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂vo
 ┃ ┃ ┃ ┃ ┃ ┣ 📂media
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂mapper
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂serviceImpl
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂vo
 ┃ ┃ ┃ ┃ ┃ ┣ 📂member
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂mapper
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂serviceImpl
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂vo
 ┃ ┃ ┃ ┃ ┃ ┣ 📂mypage
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂community
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂member
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂review
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂shop
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂mapper
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂serviceImpl
 ┃ ┃ ┃ ┃ ┃ ┣ 📂notification
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂mapper
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂serviceImpl
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂vo
 ┃ ┃ ┃ ┃ ┃ ┣ 📂report
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂mapper
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂serviceImpl
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂vo
 ┃ ┃ ┃ ┃ ┃ ┣ 📂review
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂debounce
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂serviceImpl
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂mapper
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂ReviewOCR
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂serviceImpl
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂vo
 ┃ ┃ ┃ ┃ ┃ ┣ 📂shop
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂mapper
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂ServiceImpl
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂vo
 ┃ ┗ 📂resources
 ┃ ┃ ┣ 📂attach
 ┃ ┃ ┣ 📂mapper
 ┃ ┃ ┣ 📂static
 ┃ ┃ ┃ ┣ 📂assets
 ┃ ┃ ┃ ┣ 📂css
 ┃ ┃ ┃ ┃ ┣ 📂admin
 ┃ ┃ ┃ ┃ ┃ ┣ 📂member
 ┃ ┃ ┃ ┃ ┃ ┗ 📂shop
 ┃ ┃ ┃ ┃ ┣ 📂board
 ┃ ┃ ┃ ┃ ┣ 📂member
 ┃ ┃ ┃ ┃ ┣ 📂mypage
 ┃ ┃ ┃ ┃ ┃ ┣ 📂member
 ┃ ┃ ┃ ┃ ┃ ┣ 📂review
 ┃ ┃ ┃ ┃ ┃ ┣ 📂shop
 ┃ ┃ ┃ ┃ ┣ 📂review
 ┃ ┃ ┃ ┃ ┣ 📂shop
 ┃ ┃ ┃ ┣ 📂img
 ┃ ┃ ┃ ┃ ┣ 📂common
 ┃ ┃ ┃ ┃ ┣ 📂member
 ┃ ┃ ┃ ┃ ┣ 📂shop
 ┃ ┃ ┃ ┣ 📂js
 ┃ ┃ ┃ ┃ ┣ 📂admin
 ┃ ┃ ┃ ┃ ┃ ┣ 📂member
 ┃ ┃ ┃ ┃ ┃ ┗ 📂shop
 ┃ ┃ ┃ ┃ ┣ 📂member
 ┃ ┃ ┃ ┃ ┣ 📂mypage
 ┃ ┃ ┃ ┃ ┃ ┣ 📂member
 ┃ ┃ ┃ ┃ ┃ ┣ 📂review
 ┃ ┃ ┃ ┃ ┃ ┗ 📂shop
 ┃ ┃ ┃ ┃ ┣ 📂review
 ┃ ┃ ┃ ┃ ┣ 📂shop
 ┃ ┃ ┃ ┗ 📂upload
 ┃ ┃ ┣ 📂templates
 ┃ ┃ ┃ ┣ 📂admin
 ┃ ┃ ┃ ┃ ┣ 📂member
 ┃ ┃ ┃ ┃ ┗ 📂shop
 ┃ ┃ ┃ ┣ 📂community
 ┃ ┃ ┃ ┣ 📂member
 ┃ ┃ ┃ ┣ 📂mypage
 ┃ ┃ ┃ ┃ ┣ 📂member
 ┃ ┃ ┃ ┃ ┣ 📂review
 ┃ ┃ ┃ ┃ ┣ 📂shop
 ┃ ┃ ┃ ┣ 📂review
 ┃ ┃ ┃ ┣ 📂shop
 ┗ 📂test
 ┃ ┣ 📂generated_tests
 ┃ ┣ 📂java
 ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┗ 📂ssginc
 ┃ ┗ 📜test.iml
```

<br>

---


## 5. UI 구성 및 기능

### 기능

#### 회원가입
![회원가입](https://github.com/user-attachments/assets/c72cffb2-fc35-4c4d-b582-2ca77d3d9407)

#### 소셜로그인
![소셜로그인](https://github.com/user-attachments/assets/ac018eeb-fae8-4f2a-8b31-1947792938e9)

#### ID/PW 찾기
![ID/PW 찾기](https://github.com/user-attachments/assets/e414be75-1c5c-4f7e-8950-85445bac47c0)

#### 내주변 뷰티샵 조회
![내주변 뷰티샵 조회](https://github.com/user-attachments/assets/b49392b9-597e-40c2-be5e-5f30e25d4dbe)

- 사용자의 현재 위치를 가져오기 위해 브라우저의 Geolocation API를 사용하고, 해당 위치를 중심으로 네이버 지도를 초기화합니다.
- 각 상점의 위치 데이터를 바탕으로 Naver Maps의 마커를 생성하고, 카테고리에 따라 서로 다른 아이콘으로 표시합니다.
- 사용자의 좌표를 기반으로 실제 주소 정보를 받아와 페이지 상에 표시합니다.
- 상점 목록은 정렬(오름차순, 내림차순) 및 필터링된 결과를 렌더링하며, 각 상점에 대한 미디어 이미지를 캐러셀 형태로 보여줍니다.
- Daum 우편번호 서비스를 사용해 사용자가 주소를 검색하면, 해당 주소를 네이버 지도 Geocoder를 통해 좌표로 변환한 후 지도를 재설정합니다.
- 바텀시트(하단 패널)의 드래그 기능을 통해 높이를 조절할 수 있으며, 카테고리 버튼을 클릭하면 해당 카테고리의 상점 목록이 다시 로드됩니다.
- 상세 페이지에서는 탭 버튼을 통해 홈, 디자이너, 시술, 정보 탭 간 전환이 가능합니다.


#### 리뷰 작성
![리뷰 작성](https://github.com/user-attachments/assets/dce3c816-f7ae-4893-ab5f-024edf35bc47)

### 마이페이지

#### 업체 등록
![업체 등록](https://github.com/user-attachments/assets/f24ec2d3-0b7b-4cf0-8a1c-bb1207376755)

- 입력 받은 업체 정보에서 날짜 형식을 정리하고, 사업자 등록번호의 유효성을 검증합니다.
- Daum 우편번호 API를 활용하여 사용자가 주소를 검색하면, 선택한 주소가 입력 필드에 자동으로 채워집니다.
- 업체 등록 성공 후 반환된 업체 ID를 활용하여, 미디어 파일을 S3에 저장합니다.
- 업체 등록 및 미디어 파일 업로드가 완료되면, 자동으로 디자이너 등록 페이지로 리다이렉션됩니다.

#### 디자이너 등록
![디자이너 등록](https://github.com/user-attachments/assets/7b8989e7-47b6-4d0d-a8a1-1186d977915a)
  - 사용자가 모달 창에서 디자이너 이름, 소개, 그리고 프로필 이미지를 입력 및 업로드합니다.
  - 입력된 정보와 파일은 Designers 배열에 임시 저장되고, UI에 미리보기와 함께 목록으로 표시됩니다.
  - 수정 및 삭제 기능을 통해 사용자가 입력한 항목을 관리할 수 있습니다.
  - 등록 버튼을 누르면, Designers 배열의 데이터와 파일들이 FormData로 묶여 성공 시 시술 등록 페이지로 이동합니다.


#### 시술 등록
![시술 등록](https://github.com/user-attachments/assets/fd0221f0-c54b-437a-9ae2-f5df17a8b83b)
  - 사용자가 모달 창에서 시술 이름, 소개, 그리고 프로필 이미지를 입력 및 업로드합니다.
  - 입력된 정보와 파일은 Procedures 배열에 임시 저장되고, UI에 미리보기와 함께 목록으로 표시됩니다.
  - 수정 및 삭제 기능을 통해 사용자가 입력한 항목을 관리할 수 있습니다.
  - 등록 버튼을 누르면, Procedures 배열의 데이터와 파일들이 FormData로 묶여 성공 시 메인페이지로 이동합니다.

#### 회원 정보 수정
![회원 정보 수정](https://github.com/user-attachments/assets/48e72e69-b9c1-4db1-9e7b-557f032a862b)

#### 회원 탈퇴
![회원 탈퇴](https://github.com/user-attachments/assets/77a10514-7d03-4639-b2b1-878960306298)

#### 리뷰 수정/삭제
![리뷰 수정/삭제](https://github.com/user-attachments/assets/ab791ce0-c815-49da-be98-307a9f4d76d3)

#### 업체/디자이너/시술 수정/삭제
![업체/디자이너/시술 수정/삭제](https://github.com/user-attachments/assets/a1c6142f-c9b8-47e9-8853-05c84b544cd7)

- 수정시 업체/디자이너/시술의 상세 정보를 가져와 폼 필드에 자동으로 채웁니다.
- 업체 삭제 시, 스토어드 프로시저를 사용하여 관련된 여러 테이블에서 순차적으로 삭제 작업을 수행하도록 구현합니다.
- 이를 통해 개별 DELETE 쿼리를 여러 번 호출하는 대신, 단일 트랜잭션 내에서 모든 작업을 처리할 수 있어 데이터베이스의 네트워크 왕복 시간을 줄이고, 데이터 일관성을 유지하며, 전체 삭제 성능이 향상되는 효과를 얻었습니다.


### 관리자페이지

#### 모든 회원 조회
![모든 회원 조회](https://github.com/user-attachments/assets/fb942fd4-34c1-4f84-97bf-593d1221fff8)

#### 승인 요청 업체 조회/승인/거절
![승인 요청 업체 조회/승인/거절](https://github.com/user-attachments/assets/ef569a60-ddca-4c61-8685-04d862b29b07)

- 업체 목록은 페이지네이션을 적용하여, 이전/다음 버튼으로 데이터를 동적으로 업데이트합니다.
- 각 업체 행에 "상세보기" 버튼을 배치하여 드롭다운 방식으로 상세 정보를 표시하고, 승인/거절 버튼으로 상태 변경이 가능합니다.
- 승인 시, 업체 상태를 업데이트하고, 업체 주소를 기반으로 Naver Geocoding API를 호출하여 위도/경도 좌표를 업데이트합니다.
- 업체 소유자의 회원 역할을 "ROLE_MANAGER"로 변경하여 업체 담당자로 권한을 부여합니다.
- 스토어드 프로시저를 사용해 관련 테이블에서 순차적으로 데이터를 삭제함으로써, 개별 DELETE 쿼리를 여러 번 호출하는 것보다 네트워크 왕복 횟수를 줄이고 처리 속도를 개선합니다.

#### 모든 업체 조회
![모든 업체 조회](https://github.com/user-attachments/assets/ad3f853d-2d4a-47a3-8497-9cf8a133dca0)

- 업체 목록은 페이지네이션을 적용하여, 이전/다음 버튼으로 데이터를 동적으로 업데이트합니다.
- 각 업체 행에 "상세보기" 버튼을 배치하여 드롭다운 방식으로 상세 정보를 표시하고, 승인/거절 버튼으로 상태 변경이 가능합니다.

<br>

---

<a id="problem-solving"></a>
## 6. Problem Solving

프로젝트 개발 과정에서 발생한 문제를 분석하고,
데이터 처리 및 서비스 구조를 개선한 경험을 정리했습니다.

### OCR 데이터 처리 구조 개선

초기에는 OCR API 응답을 하나의 문자열로 변환한 뒤
Regex를 이용하여 영수증 정보를 추출했습니다.

그러나 영수증마다 OCR 결과의 형식과 배치가 달라
상호명, 결제금액, 날짜 등의 데이터가 일관되게 추출되지 않는 문제가 발생했습니다.

#### 개선

OCR 응답의 Token / Line 구조를 활용하고,
필드별 데이터 추출 책임을 분리하는 구조로 개선했습니다.

- OCR Token → Line 구조화
- 필드별 Extractor 분리
- 키워드 및 문맥 기반 데이터 추출
- Confidence를 활용한 추출 결과 분류
- 사용자의 확인이 필요한 데이터 구분

#### 개선 구조

```text
OCR Response
    ↓
OCR Token
    ↓
OCR Line
    ↓
Field Extractor
    ↓
ExtractedField
    ↓
ReceiptAssembler
    ↓
Confirmed / Uncertain

```

프로젝트 종료 후 진행한 OCR 구조 개선 과정은
Portfolio의 Problem Solving에서 자세히 확인할 수 있습니다.

#### 데이터 무결성 설계

영수증 등록 및 리뷰 작성 과정에서
동일한 데이터가 중복 저장될 수 있는 문제를 방지하기 위해
DB 레벨에서 중복 저장 기준을 정의했습니다.

#### 영수증 중복 기준
```text
사업자번호 + 승인번호 + 결제일자 + 결제금액 + 매장명
```

#### 리뷰 중복 기준
```text
회원 ID + 영수증 ID
```

#### 적용
DB에 복합 UNIQUE 제약조건을 적용하고,
제약조건 위반을 서비스 계층에서 예외로 처리했습니다.

또한 OCR 결과의 매장명에 포함된 불필요한 공백을 정규화하여
동일 매장명의 표현 차이로 인해 식별값이 달라지는 문제를 줄였습니다.

```text
"        스타벅스   강남점  "
  ↓
"스타벅스 강남점"
```

자세한 데이터 무결성 설계 및 예외 처리 과정은
Portfolio의 Problem Solving에서 확인할 수 있습니다.

### LLM 리뷰 요약 처리 구조 개선

기존에는 리뷰가 작성될 때마다
해당 업체의 리뷰 요약을 LLM에 요청하는 방식으로 처리했습니다.

동일 업체에 여러 리뷰가 작성되는 경우
같은 업체에 대한 요약 요청이 반복될 수 있다는 문제가 있었습니다.
<br>

#### 개선
리뷰 작성 이벤트에서 변경된 업체 ID를 Redis Set에 수집하고,
Scheduler를 통해 일정 주기마다 변경된 업체만 처리하도록 구조를 개선했습니다.

#### 처리 흐름
```text
리뷰 작성
    ↓
ReviewCreatedEvent
    ↓
Redis Set에 Shop ID 저장
    ↓
중복 Shop ID 제거
    ↓
Scheduler 실행
    ↓
변경된 업체의 리뷰 조회
    ↓
LLM 요약
    ↓
업체 리뷰 요약 갱신
```

Redis Set을 활용하여 동일 업체에 대한 여러 변경 요청을
하나의 처리 대상으로 집계할 수 있도록 구성했습니다.

자세한 처리 구조 및 핵심 코드는
Portfolio의 Problem Solving에서 확인할 수 있습니다.
<br>

### 리뷰 조회 API 성능 검증

다수의 리뷰 데이터와 이미지가 포함된 리뷰 조회 API를 대상으로
Apache JMeter를 활용하여 부하 테스트를 수행했습니다.

총 30,000건의 요청을 대상으로
이미지 처리 방식 변경 전후의 성능 변화를 비교했습니다.

#### Test
- Tool: Apache JMeter
- Target: 업체 리뷰 목록 조회 API
- Total Requests: 30,000건

Result
| Metric     |      Before |         After |
| ---------- | ----------: | ------------: |
| Average    |   약 2,000ms |         956ms |
| Median     |   약 2,061ms |         935ms |
| P95        |   약 2,528ms |       1,255ms |
| P99        |   약 2,753ms |       1,392ms |
| Throughput | 약 159 req/s | 약 1,004 req/s |
| Error      |          0% |            0% |

이미지 처리 방식 변경 전후의 성능을 비교하여
리뷰 조회 API의 응답 시간과 처리량 변화를 검증했습니다.

자세한 테스트 조건과 결과는
Portfolio의 Problem Solving에서 확인할 수 있습니다.

<br>

---

<a id="testing--collaboration"></a>
### 7. Testing & Collaboration

#### Testing
 - Postman / Swagger
   - REST API 및 주요 CRUD 기능 검증

 - JUnit / Mockito
   - 비즈니스 로직 중심 단위 테스트

 - Apache JMeter
   - 리뷰 조회 API 부하 테스트
   - 총 30,000건 요청 기반 성능 검증

#### Collaboration
- WBS 작성 및 일정 관리
- GitHub Branch 전략을 활용한 기능별 개발
- Pull Request 기반 코드 통합
- Code Review를 통한 코드 검토
- Notion을 활용한 프로젝트 문서 관리

<br>

---

<a id="시연-영상"></a>
8. 시연 영상
https://youtu.be/EprBTZnKQQE



