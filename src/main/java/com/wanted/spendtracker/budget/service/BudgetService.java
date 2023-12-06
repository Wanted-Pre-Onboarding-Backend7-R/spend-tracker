package com.wanted.spendtracker.budget.service;

import com.wanted.spendtracker.budget.domain.Budget;
import com.wanted.spendtracker.category.domain.Category;
import com.wanted.spendtracker.member.domain.Member;
import com.wanted.spendtracker.budget.dto.request.BudgetRecommendRequest;
import com.wanted.spendtracker.budget.dto.request.BudgetSetRequest;
import com.wanted.spendtracker.budget.dto.request.BudgetSetRequest.BudgetRequest;
import com.wanted.spendtracker.budget.dto.response.BudgetRecommendResponse;
import com.wanted.spendtracker.category.dto.CategoryAmountResponse;
import com.wanted.spendtracker.exception.CustomException;
import com.wanted.spendtracker.budget.repository.BudgetRepository;
import com.wanted.spendtracker.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.wanted.spendtracker.exception.ErrorCode.CATEGORY_NOT_EXISTS;
import static java.lang.Math.round;

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

    /**
     * 예산 추천 기능
     * @param budgetRecommendRequest 사용자가 설정한 예산 금액을 담은 dto
     * @return 카테고리 별 추천 예산 금액과 총 추천 예산 금액
     */
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

    /**
     * 각 카테고리 별 예산 금액을 추천하기 위한 계산 로직
     * @param totalCategoryAmount 카데고리 별 총 예산
     * @param amount 사용자가 설정한 총 예산 금액
     * @param totalBudgetAmount DB의 총 예산 금액
     * @return 해당 카테고리 추천 예산 금액 (백의 자리에서 반올림)
     */
    public Long calculateCategoryAmount(Long totalCategoryAmount, Long totalBudgetAmount, Long amount) {
        return round(((totalCategoryAmount / (double)totalBudgetAmount) * amount) / 1000.0) * 1000;
    }

}
