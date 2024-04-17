# 섹션 2 DB 조작하기

## DB와 MySQL
```
CPU : 연산담당
RAM : 메모리, 단기기억
DISK : 장기기록
```
- 서버를 실행시켜 API를 동작시키기까지 일어나는 일
- 1. 개발하고 있는 서버 (코드)는 DISK에 저장되어있음
- 2. 서버를 실행시키면 DISK에 에 있는 코드 정보가 RAM으로 복사됨.
- 3. API가 실행되면 '연산'이 수행되며, CPU와 RAM을 왓다갓다함.
- 4. POST API를 통해 생긴 유저정보는 RAM에 저장됨
- 5. 서버가 종료되면 RAM에 있는 모든 정보가 사라짐.
- 6. 다시 서버를 시작하면... 유저정보가 없음...

- DB : 데이터를 "구조화" 시켜 저장하는 것.
- RDB(Relational DB) : 데이터를 "표처럼 구조화" 시켜 저장하는 DB
- SQL(Structured Query Language) : 표처럼 구조화된 데이터를 "조회하는 언어"

```
mysql 연동하는 방법
1. intelliJ Utimate 쓰면 DB 발 연결 가능
처음 시도시 에러가 뜰텐데, 이때는 아래와 같은 방법을 시도하자.(참고 : https://oneul-losnue.tistory.com/108)
mysql Unicode 실행
use mysql 입력
select user, host from user; 입력 
create user "하고픈 닉네임"@'%' identified by '하고픈 패스워드'; 입력 - 유저 등록
grant all privileges on *.* to '하고픈 닉네임'@'%' with grant option; 입력 - 권한 부여
flush privileges;

-  %는 모든 아이피 접속을 허용한다는 의미임!

2. IntelliJ 사용 안하고 CLI로 접근하기. 
MySql Command Line 사용하면 됨
```
## MySQL에서 톄이블 만들기 , 테이블의 데이터 조작
- CRUD API : 데이터 넣고, 조회하고, 수정하고, 삭제하는 API
- MySQL 타입 살펴보기
```
- 정수타입
tinyint : 1바이트 정수 (bite)
int : 4바이트 정수 (int)
bigint : 8바이트 정수 (Long)
double : 8바이트 실수 (double)
deciaml(A,B) : 소수점을 B개 가지고 있는 전체 A자릿수 실수

- 문자열 타입
char(A) : A글자가 들어갈 수 있는 문자열
varchar(A) : 최대 A글자가 들어갈 수 있는 문자열

- 날짜, 시간 타입
date : 날짜, yyyy-MM-dd
time : 시간, HH:mm:ss
datetime : 날짜와 시간을 합친 타입, yyyy-MM-dd HH:mm:ss
```
- DDL(Data Definition Language) : 정의어.
```
create: DB, table 생성 역할
alter : 테이블 수정
drop : DB, table 삭제
truncate : table 초기화 
```
- DML(Data Manipulation Language) : 조작어
```
select : 데이터 조회
insert : 데이터 삽임
update: 데이터 수정
delete : 데이터 삭제
```
-DCL(Data Control Language) : 제어어
```
grant : 특정 DB 사용자에게 특정 작업에 대한 수행 권한 부여
revoke : 특정 DB 사용자에게 특정 작업에 대한 권한 박탈, 회수
```
- TCL (Transaction Control Language) : 트랜젝션 제어어
```
commit : 트랜잭션의 작업이 정상적으로 완료되었음을 관리자에게 알림
rollback : 트랜잭션의 작업이 비정상적으로 종료 되었을때, 원래 상태로 복구
```

## Spring 에서 DB 사용
- Ctrl + Shift + N 누르면 바로 파일 갈수 잇음.
- JdbcTemplate을 통해 SQL을 날릴 수 있음.
- 생성자(컨트롤러에 템플릿 넣기)를 만들어 템플릿을 파라미터로 넣으면, 자동으로 sql이 들어옴.
- sql을 만들어서 문자열 변수로 저장 . 단 values를 물음표로. 왜냐하면 유동적이니까!

```
        return jdbcTemplate.query(sql, new RowMapper<UserResponse>() {
            @Override
            public UserResponse mapRow(ResultSet rs, int rowNum) throws SQLException {  // mapRow : 익명 클래스
                long id = rs.getLong("id"); // rs:결과값에서 가져오기
                String name = rs.getString("name");
                int age = rs.getInt("age");
                return new UserResponse(id, name, age); // userResponse에 생성자 추가. 그리고 new로 새로 받기.
            }
        });
    }
    
// 람다 사용시
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            return new UserResponse(id, name, age);
        });
    }

```
``` 겪은 에러
Failed to load driver cOlass com.mysql.jdbc.Driver from HikariConfig class classloader jdk.internal.loader.ClassLoaders$AppClassLoader@78308db1
Failed to load driver class com.mysql.cj.jdbc.Driver in either of HikariConfig class loader or Thread context classloader
ㅠㅠ
	
	해결방안
	dependency에 implementation 'org.springframework.boot:spring-boot-starter-jdbc' 추가, connect-java를 connect-j로 변경
<참고 : https://velog.io/@jin-dooly/Spring-com.mysql.cj.jdbc.Driver-%EC%97%90%EB%9F%AC-%ED%95%B4%EA%B2%B0>

```
## 유저 업데이트 API, 삭제 API 개발 및 예외 처리
