# 섹션 4. 생애 최초 JPA 사용하기

## 문자열 SQL을 직접 사용하는 것이 너무 어려움
- SQL을 직접 작성하면?
- 1. 오류가 "컴파일(실행하는 순간)"시점에 발견되지 않고, "런타임(서버가 동작하고 난 뒤)"시점에  발견됨.
- 2. 특정 DB에 종속적이게 됨.
- 3. 반복 작업이 많아짐. 테이블을 하나 만들떄 마다 CRUD 쿼리가 항상 필요함.
- 4. DB의 테이블과 객체는 패러다임이 다름.(종속, 상속)
- JPA(Java Persistence API)를 사용하여 해결.
- 자바 진영의 ORM(Object-Relational Mapping)
```
JPA
JAVA : 자바
Persistence : 영속성
-> 서버가 재시작되어도 "데이터는 영구적으로 저장"되는 속성
API : 정해진 규칙

ORM
Object : 객체
Relational : 관계형 DB의 테이블 의미
Mapping : 둘을 짝짓는다!(테이블과 객체)

총 정리 -JPA 란?
객체와 관계형 DB의 테이블을 짝지어
데이터를 영구적으로 보관하기 위해 JAVA 진영에서 정해진 "규칙"

규칙을 구현해서 코드로 작성한게 Hibernate이며, 구현체라고함.
Hibernate는 내부적으로 JDBC를 사용중임.
```

## 유저 테이블에 대응되는 Entity Class 만들기
- @Entity : 스프링이 user 객체와 user 테이블을 같은것으로 바라보도록 하는 것.
- DB에서의 Entity의 의미 : 저장되고, 관리되어야 하는 데이터
- @Id : 이 필드를 PK로 간주
- @GeneratedValue : pk는 자동생성되는 값.(Identity는 자동생성전략임. mysql에서의 auto_increment와 동일)
- JPA를 사용하려면, 기본생성자가 반드시 필요함. 
- @Column : 객체의 필드와 Table의 필드를 매핑함. (테이블과 변수의 이름이 완전이 일치할경우, 안써도됨.)

```
yml 파일 설명
spring:
  jpa:
    hibernate:
      ddl-auto: none -> 스프링이 시작할때, DB에 있는 테이블을 어떻게 처리할것인가.
    properties:
      hibernate:
        show_sql: true -> JPA를 사용해 DB에 SQL을 날릴떄, SQL을 보여줄거니?
        format_sql: true -> SQL을 보여줄때 예쁘게 포맷팅 할거야?
        dialect: org.hibernate.dialect.MySQL8Dialect -> 이 옵션으로 DB를 특정하면 조금씩 다른 SQL을 수정해줌.

ddl-auto 종류
create : 기존 테이블이 있다면 삭제 후 다시 생성
create-drop : 스프링이 종료될 떄 테이블을 모두 제거
update : 객체와 테이블이 다른 부분만 변경
validate : 객체와 테이블이 동일한지 확인
none : 별다른 조치를 하지 않음

dialect : 방언, 사투리. 
```

## Spring Data JPA를 이용해 자동으로 쿼리 날리기.
- SQL 작성 안하고, CRU 기능 리팩토링 해보기.
1. UserRepository 인터페이스를 user에 하나 만들기
2. JPARepository 상속받기 (extends) <객체, PK의 속성>

```
Create
- save 메소드에 객체를 넣어주면 insert sql이 자동으로 날라감. -> save 후에는 id 값이 들어가 있음 (내가 지금 auto로 넣어놨으니까!)

Read
- findAll을 사용하면 모든 데이터를 가져온다 (select * from user;)

Update
- id를 이용해 user을 가져오고, user가 있는지 없는지 확인 -> user가 있으면 update 쿼리날려 데이터 수정
- findById를 사용해 id 기준 1개 데이터 가져오고 
 -> Optional의 orElseThrow를 사용해 USer가 없다면 예외 던짐
 -> 예외가 안던져지면 객체를 업데이트해주기. (updateName이라는 Constructor 새로 만들기)
 -> 객체를 업데이트 해주고 save 메소드 호출. --> Update SQL 날라감.
```

```
Spring Data JPA 는 JPA를 쓰고 있음.
복잡한 JPA코드를 스프링과 함께 쉽게 사용할 수 있도록 도와주는 라이브러리

Spring Data JPA <- JPA(ORM) <- Hibernate(JPA구현체) <- JDBC 순으로 진화함
```
## Spring Data JPA를 이용해 다양한 쿼리 작성하기
- 삭제기능을 Spring Data JPA로 변경하기.
```
Delete
- interface에 findByName을 만들어줌
- > User findByName(String name);
-- 반환 타입은 user이다. 유저가 없다면 null
- find by name : by뒤에 붙는 필드 이름으로 select 쿼리의 where문이 작성됨!!
- select * from user where name =?;
- userRepository에 delete가 있음 ㅎ
```
- Spring Data JPA 쿼리 정리
```
- By 앞에 들어갈 수 있는 구절 정리
find : 1건을 가져옴. 반환 타입은 객체, Optional<타입>이 될수도 있음.
findAll : 쿼리의 결과물이 N개인 경우 사용. List<타입> 반환.
exists : 쿼리 결과가 존재하는지 확인. 반환타입은 boolean
count : SQL의 결과 개수를 셈. 반환 타입은 Long

- By 뒤에 들어갈 수 있는 구절
- 각 구절은 And 나 Or로 조합 할 수 있음.(findAllByNameAndAge)
Greater(Less)Then : 초과(미만)
Greater(Less)ThanEqual : 이상(이하)
Between : ~사이에 ex( findAllByAgeBetween(int startAge, int endAge);
Starts(Ends)With : ~로 시작하는(끝나는)

```
## 트랜잭션 이론편(서비스 계층이 해야하는 역할)
- 트랜잭션 : 쪼갤수 없는 업무의 최소 단위.
- start transaction; : 트랜잭션 시작
- 정상 종료하기 (SQL 반영) : commit;
- 실패 처리하기 (SQL 미반영) : rollback;
## 트랜잭션 적용과 영속성 컨텍스트
- @Transactional에서 select문만 있으면 readOnly = true를 걸어주기.
- IOException()와 같은 Checked Exception은 롤백이 일어나지 않음!!
- 영속성 컨텍스트 : 테이블과 매핑된 Entity 객체를 관리/보관하는 역할
- 스프링에서는 트랜잭션을 사용하면 영속성 컨텍스트가 생겨나고, 트랜잭션이 종료되면 영속성 컨텍스트가 종료됨!
```
영속성 트랜잭션의 능력
1. 변경 감지 (Dirty Check)
- 영속성 컨텍스트 안에서 불러와진 Entity는 명시적으로 save 하지 않아도, "변경을 감지해" 자동으로 저장된다.
save를 안써도 저장..!

2. 쓰기 지연
- DB의 insert, update, delete sql을 바로날리지 않고, commit 될때 모아서 한번만 날림.

3. 1차 캐싱
- ID를 기준으로 Entity를 기억함
- 캐싱(영속성 컨텍스트가 잠깐 저장한 객체)된 객체는 완전히 동일하다. (고유한 주소까지 동일)
- 한번 조회된건 계에속 기억하고 있음.
```