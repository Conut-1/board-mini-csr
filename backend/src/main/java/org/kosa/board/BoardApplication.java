package org.kosa.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}

}

// TODO: 로그인 5회 실패 시에 잠금
// TOOD: 회원정보 수정, 삭제 시에 본인 확인
// TODO: 아이디 중복 확인, 회원가입에서 중복 시 Conflict 응답

// TODO: 게시글 페이징
// TODO: 게시글 조회 시에 조회수 올리기
// TODO: 게시글 작성, 수정 시에 조회수 올림 X
// TODO: 검증에 실패한 곳에 focus
// TODO: 게시글 수정과 삭제 시에 비밀번호 확인

// TODO: 컴포넌트 분리
// TODO: 트랜잭션 확인, OSIV?
// TODO: 타임리프 의존성 제거
// TODO: Vue.js 빌드 설정

// TODO: DB에서 가져오는 값 줄이기

// TODO: 스프링 시큐리티 설정 정리
// TODO: 스프링 시큐리티 권한 실패 시에 json 응답
// TODO: invalid 시각화, 검증을 한 번에 띄우기
