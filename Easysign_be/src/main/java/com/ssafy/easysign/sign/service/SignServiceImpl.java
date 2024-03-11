package com.ssafy.easysign.sign.service;

import com.ssafy.easysign.global.jpaEnum.Gubun;
import com.ssafy.easysign.sign.dto.response.CategoryResponse;
import com.ssafy.easysign.sign.dto.response.SignResponse2;
import com.ssafy.easysign.sign.entity.SignCategory;
import com.ssafy.easysign.sign.entity.SignInfo;
import com.ssafy.easysign.sign.mapper.SignCategoryMapper;
import com.ssafy.easysign.sign.mapper.SignMapper;
import com.ssafy.easysign.sign.repository.SignCategoryRepository;
import com.ssafy.easysign.sign.repository.SignRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignServiceImpl implements SignService {

    private final SignRepository signRepository;
    private final SignCategoryRepository signCategoryRepository;
    private final SignMapper signMapper;
    private final SignCategoryMapper signCategoryMapper;

    @Override
    public List<CategoryResponse> getCategoryList() {
        List<SignCategory> signCategories = signCategoryRepository.findAll();
        log.info("signCategories : " + signCategories);
        List<CategoryResponse> categoryResponses = signCategories.stream()
                .map(signCategoryMapper::toCategoryResponse)
                .toList();
        log.info("categoryResponses : " + categoryResponses);
        return categoryResponses;
    }

    @Override
    public List<SignResponse2> getSignResponseList(String categoryName, Gubun gubun) {
        SignCategory signCategory = signCategoryRepository.findByCategoryName(categoryName)
                .orElseThrow();
        Long categoryId = signCategory.getCategoryId();
        List<SignInfo> signInfos = signRepository.findAllByCategoryId(categoryId);
        log.info("signInfos : " + signInfos );
        return signInfos.stream()
                .map(signMapper::toSignResponse2)
                .toList();
    }
}
