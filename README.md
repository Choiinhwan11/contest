상세 계획서
1. 프로젝트 개요
•	프로젝트 이름: 자연재해 프로젝트
•	프로젝트 목적: 자연재해로 인한 피해를 예방하고 줄이기 위한 시스템 구축.
•	배경: 전 세계적으로 증가하는 자연재해, 특히 중국의 홍수 등으로 인한 피해가 심각.
•	목표: 자연재해로 인한 인적 및 물적 피해를 최소화하고 예방할 수 있는 시스템을 개발.
 
2. 기능 요구사항
•	기능 목록: 
o	간편 로그인
o	GPS 기반 위치 추적
o	공공데이터 지도를 통한 상습 피해지역 및 대피장소 표시
o	현재 위치의 위험 상태 알림
o	피해지역 알림 게시판
o	네이버 클라우드를 이용한 문자 메시지 기능
o	네이버 결제 시스템을 통한 후원 기능
o	봉사활동 예약을 위한 캘린더 기능
•	기능 설명: 
o	간편 로그인: ( 1일 ) 
	사용 API: 네이버, 카카오, 구글 (추후 한 가지로 축소될 가능성 있음)
	이유: 회원가입 절차를 간소화하여 사용자 편의성 증대.


o	GPS 기반 위치 추적: 
	사용 API: Geolocation API (HTML5), Google Maps API
	구현 예상 시간: 2일
	이유: 사용자의 현재 위치를 기반으로 상습 침수 지역, 


o	네이버 클라우드: 
	Image 저장: 버킷을 활용한 이미지 저장 기능.
	SMS 문자 서비스: 
	119 긴급연락 (개발 단계에서는 사용자 핸드폰으로 전송).
	관심 지역의 홍수 및 재난 알림 기능 구현.
o	캘린더 일정 관리: 
	사용 API: 네이버 클라우드
	봉사활동 예약 및 관리 기능 제공.


o	후원 기능: 
	사용 API: 네이버 결제 시스템
	후원 내역 관리 및 결제 기능 제공.
3. 비기능 요구사항
•	성능: 대용량 API 호출을 감당할 수 있어야 하며, 실시간으로 데이터를 처리해야 함.
•	보안: OAuth2를 통한 사용자 인증 및 데이터 보호.
•	호환성: 웹 애플리케이션으로 다양한 브라우저 및 기기에서 동작 가능해야 함.
•	유지보수: 도메인 기반 구조로 유지보수 용이성 확보.
4. 기술 스택
•	프론트엔드: React
•	백엔드: Java, Spring Boot, JPA
•	기타 기술: 
o	네이버 클라우드 (SMS 및 이미지 저장)
o	Apache Kafka (선택적 사용)
o	Figma (디자인 및 프로토타이핑)
•	데이터베이스: MySQL, JPA
5. UI/UX 디자인
•	와이어프레임: 각 페이지의 레이아웃을 보여주는 와이어프레임을 제작.
•	디자인 가이드라인: 
o	색상: 파란색 계열 (물과 관련된 이미지 강조).
o	폰트 크기: 제목 13px.
o	디자인 요소: 곡선 15% 적용.
•	프로토타입: Figma를 사용하여 인터랙티브 프로토타입 제작.
6. API 명세서
자연재해 프로젝트에서 사용될 주요 API에 대한 구체적인 명세서입니다. 각 API의 주요 기능, 사용 방법, 엔드포인트, 요청 및 응답 형식을 상세히 설명합니다.
1. 로그인 관련 API
1.1. 카카오 로그인 API
•	엔드포인트: https://kauth.kakao.com/oauth/authorize
•	메서드: GET
•	기능: 카카오 계정을 통해 사용자가 간편하게 로그인할 수 있도록 인증 토큰을 발급.
•	요청 파라미터: 
o	client_id: 카카오 개발자 콘솔에서 발급받은 앱 키
o	redirect_uri: 인증 후 리디렉션할 URI
o	response_type: 토큰을 받을 방법 (code로 설정)
•	응답 형식: 
o	code: 인증 코드, 이후 이 코드를 사용하여 액세스 토큰을 요청
1.2. 네이버 로그인 API
•	엔드포인트: https://nid.naver.com/oauth2.0/authorize
•	메서드: GET
•	기능: 네이버 계정을 통해 간편 로그인.
•	요청 파라미터: 
o	client_id: 네이버 개발자 콘솔에서 발급받은 클라이언트 ID
o	response_type: code로 설정하여 인증 코드 획득
o	redirect_uri: 인증 후 리디렉션할 URI
o	state: CSRF 공격 방지를 위한 임의의 문자열
•	응답 형식: 
o	code: 인증 코드, 이후 이 코드를 사용해 액세스 토큰을 요청
o	state: 요청 시 전달한 상태값 그대로 반환
1.3. 구글 로그인 API
•	엔드포인트: https://accounts.google.com/o/oauth2/auth
•	메서드: GET
•	기능: 구글 계정을 사용한 간편 로그인.
•	요청 파라미터: 
o	client_id: 구글 클라우드 플랫폼에서 발급받은 클라이언트 ID
o	redirect_uri: 인증 후 리디렉션할 URI
o	response_type: code로 설정하여 인증 코드 획득
o	scope: 사용자 정보에 접근하기 위한 권한 요청 (예: email, profile)
o	state: 요청 시 임의로 생성한 상태값
•	응답 형식: 
o	code: 인증 코드, 이후 액세스 토큰 요청에 사용
o	state: 요청 시 전달한 상태값 그대로 반환
 
2. 지도 및 위치 관련 API
2.1. Google Maps API
•	엔드포인트: https://maps.googleapis.com/maps/api/js
•	메서드: GET
•	기능: 지도 표시, 마커 추가, 현재 위치 기반 지도 표시 등 다양한 지도 기능을 제공.
•	요청 파라미터: 
o	key: Google Cloud Platform에서 발급받은 API 키
o	callback: 지도 로드 후 호출할 함수명
o	libraries: 추가 기능 로드 (예: places 등)
•	응답 형식: JavaScript 라이브러리 형태로 반환되어 웹페이지에 삽입
2.2. Geolocation API
•	엔드포인트: 브라우저 내장 API (navigator.geolocation.getCurrentPosition)
•	메서드: N/A (브라우저 내장 메서드)
•	기능: 사용자의 현재 위치 정보를 가져옴.
•	요청 파라미터: 
o	successCallback: 위치 정보를 가져오는 데 성공했을 때 호출될 콜백 함수
o	errorCallback: 위치 정보를 가져오는 데 실패했을 때 호출될 콜백 함수
o	options: 위치 정확도, 타임아웃 등 옵션 설정
•	응답 형식: 
o	coords.latitude: 위도
o	coords.longitude: 경도
o	coords.accuracy: 위치의 정확도
 
3. 캘린더 관련 API
3.1. 네이버 캘린더 API
•	엔드포인트: https://apis.naver.com/naver_openapi/v1/calendar
•	메서드: POST, GET, PUT, DELETE
•	기능: 네이버 캘린더에 일정 추가, 수정, 삭제 및 조회 기능을 제공.
•	요청 파라미터 (일정 추가 예시): 
o	title: 일정 제목
o	calendarId: 캘린더 ID
o	startTime: 일정 시작 시간 (ISO 8601 형식)
o	endTime: 일정 종료 시간 (ISO 8601 형식)
o	description: 일정 설명
o	location: 일정 장소
•	응답 형식: 
o	일정 추가 시, 생성된 일정의 eventId 반환
o	조회 시, 일정 목록 및 각 일정의 상세 정보 반환
3.2. Google Calendar API
•	엔드포인트: https://www.googleapis.com/calendar/v3/calendars
•	메서드: POST, GET, PUT, DELETE
•	기능: Google 캘린더에 일정 추가, 수정, 삭제 및 조회 기능을 제공.
•	요청 파라미터 (일정 추가 예시): 
o	calendarId: 캘린더 ID (primary 사용 가능)
o	summary: 일정 제목
o	location: 일정 장소
o	description: 일정 설명
o	start: 일정 시작 시간 (dateTime 형식, 타임존 포함)
o	end: 일정 종료 시간 (dateTime 형식, 타임존 포함)
•	응답 형식: 
o	일정 추가 시, 생성된 일정의 id 반환
o	조회 시, 일정 목록 및 각 일정의 상세 정보 반환
 
4. 기상 및 재난 관련 API
4.1. 기상청 현재 날씨 API
•	엔드포인트: http://apis.data.go.kr/1360000/VilageFcstInfoService/getUltraSrtNcst
•	메서드: GET
•	기능: 기상청의 초단기 실황 정보를 제공. 사용자의 현재 위치 또는 지정된 지역의 실시간 날씨 데이터를 가져옴.
•	요청 파라미터: 
o	serviceKey: 공공데이터포털에서 발급받은 서비스 키
o	numOfRows: 한 번의 호출에 가져올 데이터의 개수
o	pageNo: 페이지 번호
o	dataType: 반환할 데이터의 형식 (JSON 또는 XML)
o	base_date: 기준 날짜 (YYYYMMDD 형식)
o	base_time: 기준 시간 (HHMM 형식)
o	nx: 격자 X 좌표
o	ny: 격자 Y 좌표
•	응답 형식: 
o	baseDate: 발표 일자
o	baseTime: 발표 시각
o	category: 자료 구분 코드 (예: T1H는 기온, RN1은 강수량)
o	obsrValue: 자료 값

4.2. 공공데이터포털 상습 침수 피해지역 API
•	엔드포인트: http://apis.data.go.kr/1230000/FloodProneAreaService/getFloodProneAreaList
•	메서드: GET
•	기능: 상습 침수 피해지역 정보를 조회하여 현재 위치와 비교, 해당 지역이 상습 침수 위험 지역인지 확인.
•	요청 파라미터: 
o	serviceKey: 공공데이터포털에서 발급받은 서비스 키
o	numOfRows: 한 번의 호출에 가져올 데이터의 개수
o	pageNo: 페이지 번호
o	type: 데이터의 반환 형식 (JSON 또는 XML)
•	응답 형식: 
o	floodAreaId: 상습 침수 지역 ID
o	location: 지역명
o	riskLevel: 위험 등급
o	coordinates: 위치 좌표 (위도, 경도)


4.3. 공공데이터포털 상습 범람 지역 API
•	엔드포인트: http://apis.data.go.kr/1230000/FloodingAreaService/getFloodingAreaList
•	메서드: GET
•	기능: 상습 범람 지역 정보를 조회하여 현재 위치와 비교, 해당 지역이 범람 위험 지역인지 확인.
•	요청 파라미터: 
o	serviceKey: 공공데이터포털에서 발급받은 서비스 키
o	numOfRows: 한 번의 호출에 가져올 데이터의 개수
o	pageNo: 페이지 번호
o	type: 데이터의 반환 형식 (JSON 또는 XML)
•	응답 형식: 
o	floodingAreaId: 범람 지역 ID
o	location: 지역명
o	riskLevel: 위험 등급
o	coordinates: 위치 좌표 (위도, 경도)
7. 데이터 모델링
•	ERD (Entity-Relationship Diagram): 데이터베이스 구조를 시각적으로 표현.
•	데이터베이스 스키마: 데이터베이스 테이블 및 관계 정의.
8. 일정 및 마일스톤
•	개발 일정: 주요 개발 단계와 완료 예상 날짜.
•	마일스톤: 프로젝트의 주요 이정표와 목표.
9. 테스트 계획
•	테스트 종류: 단위 테스트, 통합 테스트, 시스템 테스트 등.
•	테스트 케이스: 각 기능에 대한 테스트 시나리오와 예상 결과.
10. 배포 계획
•	배포 전략: 소프트웨어 배포 방법과 절차.
•	환경: 배포할 환경 (개발, 스테이징, 프로덕션).
11. 리스크 관리
•	리스크 식별: 프로젝트에서 예상되는 리스크.
•	리스크 완화 전략: 리스크를 줄이기 위한 계획.
12. 기타 사항
•	법적 요구사항: GPS 사용에 대한 동의 절차를 법적 요구사항에 맞게 처리.

![Uploading image.png…]()
