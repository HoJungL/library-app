# 섹션 1. API

## 단어 설명
- 의존성 : 프로젝트에서 사용하는 라이브러리, 프레임워크를 뜻함
- 라이브러리 : 프로그래밍을 개발할때, 미리 만들어져 있는 기능을 가져다 사용하는 것
- 프레임워크 : 프로그래밍을 개발할때, 미리 만들어져 있는 구조에 코드를 가져다 끼워 넣는 것.

## @SpringBootApplication과 서버
- 어노테이션 (@) : 자동으로 설정을 해주는 것.
- Server : "기능"을 제공하는 "것" (회원가입 기능,추천 기능 등)
- - 어떠한 기능을 제공하는 프로그램. 그 프로그램을 실행하고 있는 컴퓨터를 "서버"라고 함.

## 네트워크란?
- 컴퓨터별 고유 주소(IP)
- 포트(port):특정 하나의 사용하는 프로그램
- Domain Name : IP 244.66.451 이건 외우기 힘드니, spring.com 이런식으로 바꾼거
- 이거를 DNS(Domain Name System)이라고 함.

## HTTP와 API란?
- 데이터를 보내기 위한 표준이 존재함.
- 표준을 HTTP라고 함!! (HyperTextTransfer Protocol)
- 예시 (Get, post)
```
GET/portion?color=red&count=2
Host:spring.com:3000 -> HTTP 요청을 받는 컴퓨터와 프로그램 정보
GET : HTTP 요청을 받는 컴퓨터에게 요청하는 "행위" (http method)
portion : HTTP 요청을 받는 컴퓨터에게 원하는 "자원" (Path라고함)
color=red&count=2 : 요구 조건을 Query라고 함.

POST/oak/leather
Host:spring.com:3000 -> 헤더(여러줄 가능)
<한줄 띄우기>
오크가죽 정보 -> Body라고 함.(여러줄 가능) 
```
```
GET : 데이터를 달라 , 쿼리
POST : 데이터를 저장하라, 바디
PUT : 데이터를 수정하라, 바디
DELETE : 데이터를 삭제하라, 쿼리
```
- API(Application Programming Interface) (규칙)
- 정해진 약속을 해서, 특정 기능을 수행하는 것
- URL(Uniform Resource Locator)
```
http://spring.com:3000/portion?color=red&count=2
http : 프로토콜
:// : 구분기호
spring.com:3000 : 도메인 이름 (포트, 도메인 이름은 IP로 대체가능!)
portion : 자원의 경로 (path)
color=red&count=2 : 쿼리(추가정보)
```
- 데이터 요청에 대한 응답을 제공한 컴퓨터 == 서버
- 데이터를 요청한 컴퓨터 == 클라이언트

## GET API
- 쿼리를 사용해서 API에서 데이터를 받음
- DTO(Data Transfer Object)를 사용하자.
- @RestController //API의 진입지점을 말해주는것.
- lombok 쓰는게 편함. 게터세터...

## POST API
- Body를 이용하여 데이터를 받음.
- JSON(JavaScript Object Notation)형식을 받음!
```
JSON : 객체 표기법. 즉, 무언가를 표현하기 위한 형식.
JAVA의 Map<Object, Object>와 유사.
Json안에 Json이 올 수 있음.
표기방법
{
"key" : value
"name" : "이호정",
"age" : 90
"house": {
    "address" : "서울",
    "hasDoor" : true
}
```
- @RequestBody : HTTP Bdoy로 들어오는 Json을 객체(dto)로 변화시킬 수 잇음.

```
도서관리 앱의 요구사항
사용자
- 도서관의 사용자를 등록할 수 있다.(이름 필수, 나이 선택)
- 도서관 사용자의 목록을 볼 수 있다.
- 도서관 사용자 이름을 업데이트 할 수 있다.
- 도서관 사용자를 삭제할 수 있다.

책
- 도서관에 책을 등록 및 삭제할 수 있다.
- 사용자가 책을 빌릴 수 있다. (만약 다른 사람이 그 책을 빌렸다면 빌릴 수 없다.)
- 사용자가 책을 반납할 수 있다.
```
## 유저 생성 API
- 생성 순서
1. UserController 생성
2. dto 생성
3. user라는 domain 만들기 (이때 예외사항 처리 잘하기), constructor 안에
4. 생성한 Controller에 user domain을 담을 리스트 생성하기.
5. users.add를 통해 넣기.

## 유저 조회 API
- Controller에서 getter가 있는 객체를 반환하면 JSON이 됨!
- id? : 유저별로 겹치지 않는 유일한 번호(PK)


