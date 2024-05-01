# 배포 시작하기

## EC2에 접속해 리눅스 명령어 다뤄보기
```
AWS 접근 방법 1 : 다운로드 받은 키 페어를 이용하는 방법
- 1. 우리가 접속하려는 EC2의 IP주소 필요
- 2. 다운 받은 키페어
- 3. 접속하기 위한 프로그램(git CLI or Mac terminal) git bash

git bash -> chmod 400 [키페어 드래그]
ssh -i [키페어 드래그] ec2-user@ip주소

장점 : 터미널로 쉽게 접근 가능 (많을 경우)

AWS 접근 방법2
- aws화면에서 연결 -> 연결 다시누르면 끝

장점 : 쉽다.
```

```
기본적인 리눅스 5가지 명령어
mkdir : 폴더를 만드는 명령어 (mkdir folder1) (make directory)
ls : 현재 위치에서 폴더나 파일을 확인하는 명령어 
ls -l : 조금 더 자세한 정보를 확인 할 쑤 있음. 
cd : 폴더 안으로 들어가는 명령어 (change directory)
cd .. : 상위 폴더로 올라가는 명령어
pwd : 현재 위치를 확인하는 명령어 (print working directory)
rmdir : 비어있는 폴더(디렉토리)를 제거하는 명렁어 (remove directory)

drwxr-xr-x 2 ec2-user ec2-user 6 날짜
d : 폴더이다. (없으면 파일이라는 소리임)
rwx : 읽기, 쓰기, 실행하기 
나눠진 구분에 따라 (폴더 소유자의 권한-rwx / 폴더 소유그룹의 권한 xr / 아무나 접근했을때의 권한 x)
2 : 폴더에 걸려있는 바로가기 개수
ec2-user : 이 폴더의 소유주 이름 (첫번째)
ec2-user : 폴더의 소유그룹 이름 (두번째)
6 : 이 폴더(파일)의 크기, byte 단위
날짜 : 최종변경 날짜
```

## 배포를 위한 프로그램 설치하기
```
1. 코드를 가져오기 위한 git
2. 우리가 만든 서버를 구동할 java
3. 데이터베이스의 역할을 할 mysql
```
- 리눅스에서 스프링 서버 배포를 위한 프로그램 설치
```
sudo yum update
sudo : 관리자 권한으로 실행
yum : 리눅스 패키지 관리 프로그램 (gradle과 비슷한 역할)
yum update : 현재 설치된 여러 프로그램들을 최신화 하기.
```
- git 설치, java 설치
```
sudo yum install git : git 설치하기
sudo yum install git -y : 중간에 y/n 안물어보고 바로 설치
sudo yum install java-17-amazon-corretto -y
```
- MySQL 설치 (리눅스 2023쓸때는 아래의 코드를 작성)
```
참고 : https://jinhos-devlog.tistory.com/entry/MySQL-8-Community-Edition-%EC%84%A4%EC%B9%98-%EC%A4%91-%EC%98%A4%EB%A5%98
sudo dnf install https://dev.mysql.com/get/mysql80-community-release-el9-1.noarch.rpm

처음할때는
sudo rpm --import https://repo.mysql.com/RPM-GPG-KEY-mysql-2022
sudo yum update
를 써준뒤에 
sudo dnf install mysql-community-server 를 다시 작성

sudo systemctl status mysqld : 현재 보이지 않는 프로그램을 관리하는 명령어
sudo systemctl restart mysqld : mysqld 프로그램을 재시작!

mysql -u root -p  root를 내꺼로 바꾸면 되겠지?

비번바꾸는 방법
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY "비번";
비번 규칙 : 8자이상, 대소문자,숫자 특수문자 다 있어야함 ㅎ 
exit; : 나가기

https://yumserv.tistory.com/136 : ec2 서버에서도 내 아이디를 새로 만들고... 해야 됩니다 ㅠㅠ

``` 
