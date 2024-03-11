package com.ssafy.easysign.sign.controller;

import com.ssafy.easysign.global.jpaEnum.Gubun;
import com.ssafy.easysign.sign.dto.response.CategoryResponse;
import com.ssafy.easysign.sign.dto.response.SignResponse2;
import com.ssafy.easysign.sign.service.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sign")
@RequiredArgsConstructor
@Slf4j
public class SignController {

    private final SignService signService;

    @GetMapping("/signUrl")
    public ResponseEntity<List<String>> getJihwaURLData(
            @RequestParam(value = "categoryname", required = false) String categoryName) {
        // EC2 서버에 있는 JSON 파일 경로
        String jsonFilePath = "/path/to/your/json/file.json";

        // URL 데이터를 저장할 리스트
        List<String> urlDataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(jsonFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // JSON 데이터를 읽어서 URL 데이터 생성
                String urlData = createURLDataFromJSON(line);
                urlDataList.add(urlData);
            }
        } catch (IOException e) {
            log.error("Error reading JSON file: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // URL 데이터를 클라이언트에 반환
        return new ResponseEntity<>(urlDataList, HttpStatus.OK);
    }

    // JSON 데이터를 기반으로 URL 데이터 생성하는 메서드
    private String createURLDataFromJSON(String jsonData) {
        // JSON 데이터를 파싱하여 필요한 정보를 추출하고 URL 데이터를 생성하는 로직 구현
        // 예시 코드에서는 단순히 JSON 데이터를 URL 데이터로 사용하는 것으로 가정
        return jsonData;
    }

    @GetMapping("/category")
    public ResponseEntity<List<CategoryResponse>> getCategoryResponseList() {
        List<CategoryResponse> CategoryResponse = signService.getCategoryList();
        log.info("categoryResponses : " + CategoryResponse);
        return new ResponseEntity<>(CategoryResponse, HttpStatus.OK);
    }

    @GetMapping("/jihwa")
    public ResponseEntity<List<SignResponse2>> getSignResponseListJihwa(
            @RequestParam(value = "categoryname", required = false) String categoryName) {
        return getSignResponseList(Gubun.jihwa, categoryName);
    }

    @GetMapping("/word")
    public ResponseEntity<List<SignResponse2>> getSignResponseListWord(
            @RequestParam(value = "categoryname", required = false) String categoryName) {
        return getSignResponseList(Gubun.word, categoryName);
    }

    @GetMapping("/sentence")
    public ResponseEntity<List<SignResponse2>> getSignResponseListSentence(@RequestParam(value = "categoryname", required = false) String categoryName) {
        return getSignResponseList(Gubun.sentence, categoryName);
    }

    private ResponseEntity<List<SignResponse2>> getSignResponseList(Gubun gubun, String categoryName) {
        if (categoryName != null) {
            try {
                List<SignResponse2> signResponses = signService.getSignResponseList(categoryName, gubun);
                log.info("signResponses : " + signResponses);
                return new ResponseEntity<>(signResponses, HttpStatus.OK);
            } catch (Exception e) {
                // 실패 시 400 Bad Request 반환
                log.error("Error retrieving sign responses: " + e.getMessage());
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            // 실패 시 403 Forbidden 반환
            log.info("gubun : " + gubun + ", categoryId: " + categoryName);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
