# 네이버 코드 컨벤션

코딩 컨벤션이란 가독성이 좋고 관리하기 쉬운 코드를 작성하기 위한 코딩 스타일 규약을 말한다.

코딩 컨벤션을 준수하면 가독성이 좋아지고 성능에 영향을 주거나 오류를 발생시키는 잠재적인 위험 요소를 줄여줘 유지보수 비용을 줄일 수 있다.

코딩 컨벤션을 적용하기 위해 정적 코드 분석 도구를 도입하는데, 보통 자바스크립트에서는 ESLint, Java에서는 Checkstyle과 같은 도구를 주로 사용한다.

Java에서는 구글이나, 네이버에서 지정한 코딩 컨벤션이 유명하므로 오늘은 InteliJ에 네이버 코딩 컨벤션을 Code Style Formatter로 설정하고, Checkstyle까지 적용하는 과정을 설명한다.

네이버 캠퍼스 핵데이 Java 코딩 컨벤션에 대해 자세한 명세 사항은 아래 링크에서 확인할 수 있다.

[https://naver.github.io/hackday-conventions-java/](https://naver.github.io/hackday-conventions-java/)

# InteliJ Formatter 적용

## **1.1.1. 1. Fomatter 다운로드**

[https://github.com/naver/hackday-conventions-java/blob/master/rule-config/naver-intellij-formatter.xml](https://github.com/naver/hackday-conventions-java/blob/master/rule-config/naver-intellij-formatter.xml)

## **1.1.2. 2. Scheme 설정**

1. InteliJ에서 File → Settings 메뉴를 연다. (Alt + Shift + S)
2. Editor → Code Style → Java 항목으로 이동한다.
3. Scheme 항목의 오른쪽에 있는 톱니바퀴 아이콘을 선택한다.
4. Import Scheme → InteliJ IDEA Code Style XML을 선택한다.
5. 다운로드한 naver-intelij-formatter.xml 파일을 선택한 후 OK 버튼을 누른다.
6. 그럼 To 항목에 이름을 정할 수 있는데, 디폴트 값으로 설정해도 상관 없지만, 포멧터를 커스터마이징 했거나 프로젝트마다 다른 포멧터 설정을 사용한다면 스키마의 이름을 유일성 있게 수정한다.

![https://blog.kakaocdn.net/dn/boJRNr/btrGgsikKAR/nMhVWmQCRAvY5wq9SMOCPK/img.png](https://blog.kakaocdn.net/dn/boJRNr/btrGgsikKAR/nMhVWmQCRAvY5wq9SMOCPK/img.png)

![https://blog.kakaocdn.net/dn/6jgsI/btrGflxwrx9/8onq08T98wdhljuNCCIetk/img.png](https://blog.kakaocdn.net/dn/6jgsI/btrGflxwrx9/8onq08T98wdhljuNCCIetk/img.png)

간단한 위 과정을 완료하면 Code Style Formatter 설정이 끝난다.

소스코드 또는 패키지나 프로젝트 디렉터리를 선택 한후 Ctrl + Alt + L 을 누르면 지정한 코드 스타일에 맞게 자동으로 코드에 포멧터가 적용된다.

그러나 일일히 이렇게 단축키를 눌러가며 적용하는건 귀찮기도 하고 깜빡할 위험이 있다. 그렇기 때문에 저장할 때마다 포멧터를 자동으로 적용하는 설정을 추가하도록 한다.

## **1.1.3. 저장 시 마다 코딩 컨벤션 자동 적용하기**

1. File → Settings → Tools → Actions on Save 또는 Settings 검색창에 Save라고 검색해서 나온 항목을 선택한다.
2. Reformat code(저장 시 자동으로 포맷 적용)와 Optimize imports(저장 시 사용하지 않는 import 제거)를 체크한다.

![https://blog.kakaocdn.net/dn/d8sTh8/btrGjblTNiI/BbexIDyLanDKLgz7h2LYo1/img.png](https://blog.kakaocdn.net/dn/d8sTh8/btrGjblTNiI/BbexIDyLanDKLgz7h2LYo1/img.png)

## **1.2. Checkstyle 적용하기**

Checkstyle이란 Java 소스 코드가 지정된 코딩 컨벤션을 준수하는지 확인하기 위한 정적 코드 분석 도구이다. 지정된 규칙에 어긋나는 경우 컴파일 시 경고나 에러를 띄워준다.

진행하기 전에 아래 링크에서 **naver-checkstyle-rules.xml과 naver-checkstyle-suppressions.xml를 다운로드 한다.**

[https://github.com/naver/hackday-conventions-java/blob/master/rule-config/naver-checkstyle-rules.xml](https://github.com/naver/hackday-conventions-java/blob/master/rule-config/naver-checkstyle-rules.xml)

[https://github.com/naver/hackday-conventions-java/blob/master/rule-config/naver-checkstyle-suppressions.xml](https://github.com/naver/hackday-conventions-java/blob/master/rule-config/naver-checkstyle-suppressions.xml)

## **1.2.1. 1. Checkstyle 플러그인 설치**

1. File → Settings → Plugins 메뉴로 이동한다.
2. Marketplace에 CheckStyle을 검색하여 CheckStyle-IDEA 플러그인을 설치한다.
3. InteliJ를 재시작한다.

## **1.2.2. 2. Checkstyle 설정**

1. File → Settings → Tools에서 Checkstyle 항목을 선택한다.
2. Scan scope를 All sources including tests로 설정한다.
3. Treat Checkstyle errors as warnings를 체크한다.
4. Configuration File에서 + 버튼을 클릭한다.
5. Description은 **Naver Checkstyle Rules [버전]** 으로 지정하는 것이 권장되지만 프로젝트별로 커스터마이징 했다면 프로젝트 이름 등을 붙인다.
6. Use a Local Checkstyle File을 선택하고 Browse 버튼을 눌러서 naver-checkstyle-rules.xml를 지정하고 Next 버튼을 누른다.
7. suppressionFile 변수를 설정하라는 창이 뜨면 Value에 **naver-checkstyle-suppressions.xml**를 입력하고 Next 버튼을 누른다.
8. Naver Checkstyle Rules의 Active를 체크한다.

![https://blog.kakaocdn.net/dn/tUvua/btrGgFanGng/EfvKq07n4pO2cHdN4qhko0/img.png](https://blog.kakaocdn.net/dn/tUvua/btrGgFanGng/EfvKq07n4pO2cHdN4qhko0/img.png)

Ok 버튼을 눌러 설정을 완료하면, InteliJ 하단에 Check Style 탭이 생기고 좌측에서 Check Current File 또는 Check Module, Check Project 등을 선택하여 코딩 컨벤션 준수 여부를 확인할 수 있다.

![https://blog.kakaocdn.net/dn/oCpb0/btrGhWvZN8K/DK1jds9TS1ir3htOSChKd0/img.png](https://blog.kakaocdn.net/dn/oCpb0/btrGhWvZN8K/DK1jds9TS1ir3htOSChKd0/img.png)

변수명에 소문자 카멜 케이스를 적용해야 한다는 컨벤션을 지키지 않자 CheckStyle에서 경고를 띄워주는 모습을 확인할 수 있다.