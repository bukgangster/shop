(1) git 설치
sudo yum install -y git
git --version

(2) java 설치
> 설치 가능한 자바 버전 확인
yum list java*jdk-devel

ex) java-11-openjdk-devel.x86_65 설치
sudo yum install java-11-openjdk-devel.x86_64
yes
java -version

배포하기
(1) git Clone
sudo git clone https://주소

