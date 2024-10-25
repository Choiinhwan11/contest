    package org.example.contest.domain.interested.controller;

    import lombok.RequiredArgsConstructor;
    import org.example.contest.domain.interested.DTO.InterestDTO;
    import org.example.contest.domain.interested.entity.InterestedInformation;
    import org.example.contest.domain.interested.service.InterestedService;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Sort;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/Interested")
    @RequiredArgsConstructor
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public class InterestedController {

        private final InterestedService interestedService;





        @PostMapping("/createInterest/{userId}")
        public ResponseEntity<?> createInterest(@PathVariable("userId") String userId,
                                                @RequestBody InterestDTO interestDTO) {
            System.out.println("userId = " + userId) ;
            interestedService.saveInterest(interestDTO, userId);
            return ResponseEntity.ok("관심 정보가 성공적으로 등록되었습니다.");
        }



        /**
         * @Param userId
         * @Param page , size
         *
         * userId 를 찾아서 List interest 하기 .
         * */

        // 관심 목록 페이징 처리, 내림차순으로 정렬

        @GetMapping("/list/{userId}")
        public ResponseEntity<?> getInterestListByUser(
                @PathVariable("userId") String userId,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "5") int size) {

            // 유저가 존재하는지 확인
            boolean userExists = interestedService.checkIfUserExists(userId);
            if (!userExists) {
                return new ResponseEntity<>("해당 유저가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
            }

            // 페이징 요청 객체 생성 (내림차순 정렬)
            PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

            // 관심 정보 리스트 서비스 호출
            Page<InterestedInformation> interestPage = interestedService.getInterestListByUserId(userId, pageRequest);

            // 결과를 클라이언트로 반환
            return ResponseEntity.ok(interestPage);
        }
    }
