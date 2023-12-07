package com.wanted.spendtracker.domain.budget.service;

import com.wanted.spendtracker.domain.budget.domain.Budget;
import com.wanted.spendtracker.domain.category.domain.Category;
import com.wanted.spendtracker.domain.budget.repository.BudgetRepository;
import com.wanted.spendtracker.domain.category.repository.CategoryRepository;
import com.wanted.spendtracker.domain.member.domain.Member;
import com.wanted.spendtracker.domain.budget.dto.request.BudgetRecommendRequest;
import com.wanted.spendtracker.domain.budget.dto.request.BudgetSetRequest;
import com.wanted.spendtracker.domain.budget.dto.request.BudgetSetRequest.BudgetRequest;
import com.wanted.spendtracker.domain.budget.dto.response.BudgetRecommendResponse;
import com.wanted.spendtracker.domain.category.dto.CategoryAmountResponse;
import com.wanted.spendtracker.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.wanted.spendtracker.global.exception.ErrorCode.CATEGORY_NOT_EXISTS;
import static java.lang.Math.round;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;

    /***
     * 예산 설정 기능
     * @param member 인증된 사용자
     * @param budgetSetRequest 사용자가 설정한 카테고리별 예산 금액
     */
    @Transactional
    public void setBudget(Member member, BudgetSetRequest budgetSetRequest) {
        List<BudgetRequest> budgetRequests = budgetSetRequest.getBudgetRequestList();
        for (BudgetRequest budgetRequest : budgetRequests) {
            Category category = checkCategory(budgetRequest.getCategoryId());
            Budget budget = Budget.of(budgetRequest.getAmount(), budgetRequest.getMonth(), member, category);
            budgetRepository.save(budget);
        }
    }

    /**
     * 예산 추천 기능
     * @param budgetRecommendRequest 사용자가 설정한 예산 금액을 담은 dto
     * @return 카테고리 별 추천 예산 금액과 총 추천 예산 금액
     */
    @Transactional(readOnly = true)
    public BudgetRecommendResponse recommendBudget(BudgetRecommendRequest budgetRecommendRequest) {
        Long amount = budgetRecommendRequest.getAmount();
        Long totalBudgetAmount = budgetRepository.getTotalBudgetAmount();
        List<CategoryAmountResponse> totalCategoryAmounts = budgetRepository.getTotalCategoryAmount();
        List<CategoryAmountResponse> recommendedCategoryAmounts = new ArrayList<>();
        Long totalRecommendedBudget = 0L;
        for(CategoryAmountResponse totalCategoryAmount : totalCategoryAmounts) {
            CategoryAmountResponse categoryAmount = CategoryAmountResponse.builder()
                    .categoryId(totalCategoryAmount.getCategoryId())
                    .amount(calculateCategoryAmount(totalCategoryAmount.getAmount(), totalBudgetAmount, amount)).build();
            recommendedCategoryAmounts.add(categoryAmount);
            totalRecommendedBudget += categoryAmount.getAmount();
        }
        return BudgetRecommendResponse.of(totalRecommendedBudget, recommendedCategoryAmounts);
    }

    private Category checkCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(CATEGORY_NOT_EXISTS));
    }

    /**
     * 각 카테고리 별 예산 금액을 추천하기 위한 계산 로직
     * @param totalCategoryAmount 카데고리 별 총 예산
     * @param amount 사용자가 설정한 총 예산 금액
     * @param totalBudgetAmount DB의 총 예산 금액
     * @return 해당 카테고리 추천 예산 금액 (백의 자리에서 반올림)
     */
    private Long calculateCategoryAmount(Long totalCategoryAmount, Long totalBudgetAmount, Long amount) {
        return round(((totalCategoryAmount / (double)totalBudgetAmount) * amount) / 1000.0) * 1000;
    }

}
