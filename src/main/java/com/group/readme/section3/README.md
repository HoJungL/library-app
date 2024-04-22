# 섹션 3. 역할의 분리와 스프링 컨테이너

## 좋은 코드(Clean Code)는 왜 중요한가?
- 코드는 "요구사항"을 표현하는 언어임!
- 개발자는 요구사항을 구현하기 위해 코드를 "읽고" 작성함.
- 
## Controller를 3단 분리하기 - Service, Repository
- API의 진입 지점으로써 HTTP Body를 객체로 변환 -> Controller
- 현재 유저가 있는지 없는지 등을 확인하고 예외 처리를 함 -> Service
- SQL을 사용해 실제 DB와의 통신 담당 -> Repository

-Repository -> Service ->(DTO) Controller 구조를 Layered Architecture 라고 함.

## UserController와 스프링 컨테이너
- static이 아닌 코드를 사용하려면 "인스턴스화"가 필요함
- UserController는 JdbcTemplate에 "의존"하고있다. == Jdbc가 없으면 동작하지 않는다 ㅠㅠ
- - @RestController를 통해 UserController 클래스를 API의 진입지점으로 만들고 + 컨트롤러를 "스프링 빈"으로 등록시킴!!!
- 서버가 시작되면, 스프링 서버 내부에 거대한 "컨테이너"를 만들고, 그 안에는 클래스가 들어감.
- 이 과정에서 "인스턴스화"가 이루어짐!
- 이때, 스프링 컨테이너 안에 들어간 "클래스"를 "스프링 빈"이라고 함!
```
서버가 시작되면
1. 스프링 컨테이너(클래스 저장소)가 시작됨
2. 많은 스프링 빈들이 등록됨
3. 우리가 설정해준 스프링 빈이 등록됨.(여기서는 UserController)
4. 필요한 의존성이 자동으로 설정됨 (여기서는 JdbcTemplate)
```
- Repository는 스프링빈이 아닌 그냥 클래스라서 jdbc를 못들고옴 -> 그럼 스프링 빈으로 등록하자^^ 
- Repository에 어노테이션(@Repository)를 쓰면 끝! / Service에는 @Service를 쓰기

- 서버 시작할때 순서
```
1. 스프링 컨테이너 생성 후, 기본적 스프링 빈 등록
2. Jdbc를 의존하는 repository가 빈으로 등록
3. repository를 의존하는 service가 빈으로 등록
4. service를 의존하는 controller가 빈으로 등록 
```
## 스프링 컨테이너를 사용하는 이유
- 스프링 컨테이너에 스프링빈을 담아놓으면, 컨테이너가 스스로 Service에 필요한 repository를 넣어주는 과정(수정이 용이해짐.)
- 이걸 의존성 주입(DI Dependency Injection)이라고 함. 이거 이거 이거 리얼 겁나 중요함
- 스프링 컨테이너가 어떤 구현타입을 쓸지, 대신 결정해주는 제어의 역전(IoC , Inversion of Control)의 방식을 썼음!
- 그덕에 코드 수정없이 구현이 가능함!!
- DI, IoC는 스프링에서 가장 중요한 개념이라고 생각해도 무관함! 반드시 알고 있어야함!
- 물론 위의 특징을 쓰기 위해서는 어떤 걸 우선적으로 써야하는지 컨테이너도 모르기 떄문에, Repository에 먼저쓸 곳에 @Primary를 붙어줘야함.

## 스프링 컨테이너를 다루는 방법
- 빈을 등록하는 방법
```
@Configuration
- "클래스"에 붙이는 어노테이션
- @Bean을 사용할 때 함께 사용해 주어야 함!

@Bean
- "메소드"에 붙이는 어노테이션
- 메소드에서 반환되는 객체를 "스프링 빈"에 등록함.
```
- 언제 @Service, @Repository 사용해야할까?
- > 개발자가 직접 만든 클래스를 스프링 빈으로 등록할때! (UserService 등)
- 언제 @Configuration + @Bean 사용해야할까?
- > 외부 라이브러리, 프레임워크에서 만든 클래스를 등록할 때! (JdbcTemplate 등)
- @Component? 언제 사용할까? (이미 @Service, Repository, Controller에 있음.)
- > 주어진 클래스를 '컴포넌트'로 간주함. 
  > 해당 클래스들은 스프링 서버가 뜰때 자동으로 감지됨.
  > CSR 모두 아니고, 개발자가 직접 작성한 클래스를 스프링 빈으로 등록할때 사용됨. (잘 안써용)

- 스프링 빈을 주입받는 방법
```
1. 생성자를 이용해 주입받는 방식 (가장 권장함)
- 변수 만들고, 생성자(public ~~)
- @Autowired 생략 가능

2. Setter와 @Autowired 사용
- 근데 서비스 만들때 세터는 흠...
- 누군가가 setter를 사용하면 오작동할 가능성

3. 필드에 직접 @Autowired 사용
- private JdbcTemplate jdbc~ 위에 @Autowired 등록
- 테스트를 어렵게 만드는 요인임..
```
- @Qualifier? 
- 여러개의 후보군이 있을때, 클래스의 이름을 써서 특정 서비스만 가져옴.
- @Qualifier("main")을 쓸경우, main을 가져옴.