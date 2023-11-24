package com.wanted.spendtracker.service;

import com.wanted.spendtracker.domain.Budget;
import com.wanted.spendtracker.domain.Category;
import com.wanted.spendtracker.domain.Member;
import com.wanted.spendtracker.dto.request.BudgetRecommendRequest;
import com.wanted.spendtracker.dto.request.BudgetSetRequest;
import com.wanted.spendtracker.dto.request.BudgetSetRequest.BudgetRequest;
import com.wanted.spendtracker.dto.response.BudgetRecommendResponse;
import com.wanted.spendtracker.dto.response.CategoryAmountResponse;
import com.wanted.spendtracker.exception.CustomException;
import com.wanted.spendtracker.repository.BudgetRepository;
import com.wanted.spendtracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.wanted.spendtracker.exception.ErrorCode.CATEGORY_NOT_EXISTS;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void setBudget(Member member, BudgetSetRequest budgetSetRequest) {
        List<BudgetRequest> budgetRequests = budgetSetRequest.getBudgetRequestList();
        for (BudgetRequest budgetRequest : budgetRequests) {
            Category category = categoryRepository.findById(budgetRequest.getCategoryId())
                    .orElseThrow(() -> new CustomException(CATEGORY_NOT_EXISTS));
            Budget budget = Budget.of(budgetRequest.getAmount(), budgetRequest.getMonth(), member, category);
            budgetRepository.save(budget);
        }

    }

    public BudgetRecommendResponse recommendBudget(BudgetRecommendRequest budgetRecommendRequest) {
        Long budgetAmount = budgetRecommendRequest.getAmount();
        Long totalAmount = budgetRepository.getTotalBudgetAmount();
        List<CategoryAmountResponse> categoryAmountResponses = budgetRepository.getCategoriesAverageAmount();
        List<CategoryAmountResponse> recommendedAmount = new ArrayList<>();
        for(CategoryAmountResponse response : categoryAmountResponses) {
            CategoryAmountResponse averageAmount = CategoryAmountResponse.builder()
                    .categoryId(response.getCategoryId())
                    .amount(recommend(response, budgetAmount, totalAmount)).build();
            recommendedAmount.add(averageAmount);
        }
        return BudgetRecommendResponse.of(budgetAmount, recommendedAmount);
    }

    /**
     * 각 카테고리 별 예산 금액을 추천하기 위한 계산 로직
     * @param response 카데고리 별 총 예산과 카데고리Id 를 담은 dto
     * @param budgetAmount 사용자가 설정한 총 예산 금액
     * @param totalAmount DB의 총 예산 금액
     * @return 해당 카테고리 추천 예산 금액
     */

    public Long recommend(CategoryAmountResponse response, Long budgetAmount, Long totalAmount) {
        return (long) (((double)response.getAmount() / (double)totalAmount) * budgetAmount);
    }

}
