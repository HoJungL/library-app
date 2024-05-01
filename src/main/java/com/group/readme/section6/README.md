# 생애 최초 배포 준비하기

## 배포란?
- 최종 사용자에게 SW를 전달하는 과정 = 전용 컴퓨터에 우리의 서버를 옮겨 실행시키는 것.
- AWS할때 주의할점. OS(운영체제)를 선택해야함. - 서버용 컴퓨터는 리눅스를 많이 씀!

## Profile 과 H2 DB
- 참고 : https://jojoldu.tistory.com/547 intelliJ 무료버전의 경우에는 옆의 사이트 참고.
- 유료 버전과 유사하게 Edit Configurations 에 들여가셔서 Modify option -> VM options -> -Dspring.profiles.active=프로파일이름을 적어주면 됨.
- 똑같은 서버 코드를 실행시키고 싶지만 실행될때 "설정"을 다르게 하고 싶다! -> profile이라는 개념이등장.
- local이라는 profile 입력하면 H2 DB, dev라는 profile 입력하면 MySQL DB 사용하게!
- H2 DB : 경량 DB로, 개발단계에서 많이 사용하고, 디스크가 아닌 메모리에 데이터를 저장함.(휘발성임)
- edit configuration의 active profile을 수정하면 됨! 이건 내가 설정한 yml파일에서의 on-profile을 확인할것.
```
spring:
  config:
    activate:
      on-profile: local
 -> profile이 local일때 실행한다. (일반적으로 H2 DB)
 
spring:
  config:
    activate:
      on-profile: dev
 -> profile이 dev일때 실행한다. (본 실습에서는 MySQL)
```

## Git 과 Github
- git : 코드를 쉽게 관리 할 수 있도록 해주는 "버전 관리 프로그램"
- github : git으로 관리되는 프로젝트의 코드가 저장되는 저장소

## git 기초 사용법
```
git init : 이 프로젝트를 이제 git이 관리하겠다.
git remote add origin [각자 주소]
git add . : 파일 담기 (.은 모든 파일) 
git status : 현재상황 확인하기

git commit -m "메세지"

https://velog.io/@bami/GitGithub-%EC%9B%90%EA%B2%A9-%EC%A0%80%EC%9E%A5%EC%86%8C%EC%9D%98-%ED%8C%8C%EC%9D%BC%EC%9D%84-%EC%A7%80%EC%9A%B0%EA%B8%B0
사이트 참조.
```