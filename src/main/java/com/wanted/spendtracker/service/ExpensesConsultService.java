package com.wanted.spendtracker.service;

import com.wanted.spendtracker.domain.Member;
import com.wanted.spendtracker.dto.response.CategoryAmountResponse;
import com.wanted.spendtracker.dto.response.ExpensesRecommendResponse;
import com.wanted.spendtracker.repository.BudgetRepository;
import com.wanted.spendtracker.repository.ExpensesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.round;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExpensesConsultService {

    private final ExpensesRepository expensesRepository;
    private final BudgetRepository budgetRepository;
    private final Long MIN_EXPENSES_AMOUNT = 10000L;

    /**
     * 오늘의 지출 추천
     * @param member
     * @return 추천 지출 총액과 카테고리 별 추천 지출 금액
     */
    public ExpensesRecommendResponse recommendExpenses(Member member) {
        LocalDate currentDate = LocalDate.now();
        long daysLeftOfThisMonth = calculateDaysLeft(currentDate);

        Long totalBudgetOfThisMonth = getTotalBudgetOfThisMonth(member, currentDate);
        Long totalExpensesUntilToday = getExpensesUntilToday(member, currentDate);
        Long totalAvailableExpenses = calculateTotalAvailableExpenses(totalBudgetOfThisMonth, totalExpensesUntilToday, daysLeftOfThisMonth);

        List<CategoryAmountResponse> totalCategoryBudgetOfThisMonth = getTotalCategoryBudgetOfThisMonth(member, currentDate);
        List<CategoryAmountResponse> availableExpensesByCategory = getAvailableExpensesByCategory(totalCategoryBudgetOfThisMonth, totalBudgetOfThisMonth, totalAvailableExpenses);

        return ExpensesRecommendResponse.of(totalAvailableExpenses, availableExpensesByCategory);
    }

    private long calculateDaysLeft(LocalDate currentDate) {
        LocalDate lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
        return  ChronoUnit.DAYS.between(currentDate, lastDayOfMonth) + 1;
    }

    private Long getTotalBudgetOfThisMonth (Member member, LocalDate currentDate) {
        return budgetRepository.getTotalBudgetAmountByMonth(member.getId(), currentDate.getMonthValue());
    }

    private Long getExpensesUntilToday (Member member, LocalDate currentDate) {
        return expensesRepository.getTotalExpensesAmountUntilToday(member, currentDate);
    }

    private List<CategoryAmountResponse> getTotalCategoryBudgetOfThisMonth(Member member, LocalDate currentDate) {
        return budgetRepository.getTotalCategoryAmountByMonth(member.getId(), currentDate.getMonthValue());
    }

    /**
     * 카테고리 별 추천 지출 금액을 반환하는 메소드
     * @param totalCategoryBudgetOfThisMonth 카데고리 별 총 예산 금액을 담은 리스트
     * @param totalBudgetOfThisMonth 이번 달 총 예산 금액
     * @param totalAvailableExpenses 이번 달 사용 가능한 지출 금액
     * @return 카테고리 별 추천 지출 금액
     */
    private List<CategoryAmountResponse> getAvailableExpensesByCategory(List<CategoryAmountResponse> totalCategoryBudgetOfThisMonth,
                                                                        Long totalBudgetOfThisMonth,
                                                                        Long totalAvailableExpenses) {
        List<CategoryAmountResponse> availableExpensesByCategory = new ArrayList<>();
        for(CategoryAmountResponse CategoryBudgetOfThisMonth : totalCategoryBudgetOfThisMonth) {
            CategoryAmountResponse categoryAmount = CategoryAmountResponse.builder()
                    .categoryId(CategoryBudgetOfThisMonth.getCategoryId())
                    .amount(calculateCategoryAmount(CategoryBudgetOfThisMonth.getAmount(), totalBudgetOfThisMonth, totalAvailableExpenses)).build();
            availableExpensesByCategory.add(categoryAmount);
        }
        return availableExpensesByCategory;
    }

    /**
     * 추천 지출 총액을 계산하는 메소드
     * @param totalBudgetOfThisMonth 이번 달 총 예산 금액
     * @param totalExpensesUntilToday 이번 달 총 지출 금액 (오늘 기준)
     * @param daysLeftOfThisMonth 이번 달 남은 날짜의 수
     * @return 추천 지출 총액 반환 (백의 자리 반올림)
     */
    private Long calculateTotalAvailableExpenses(Long totalBudgetOfThisMonth, Long totalExpensesUntilToday, long daysLeftOfThisMonth) {
        long totalBudgetLeft = totalBudgetOfThisMonth - totalExpensesUntilToday;

        // 남아 있는 예산 금액이 0보다 작거나 같으면 최소 금액 반환
        if(totalBudgetLeft <= 0) {
            return MIN_EXPENSES_AMOUNT;
        }
        return round((totalBudgetLeft / (double) daysLeftOfThisMonth) / 1000.0) * 1000;
    }

    /**
     * 각 카테고리 별 추천 금액을 계산하는 메소드
     * @param totalCategoryBudgetOfThisMonth 카테고리 별 예산 총액
     * @param totalBudgetOfThisMonth 이번 달 총 예산 금액
     * @param totalAvailableExpenses 오늘 지출 가능한 총액
     * @return 각 카테고리 별 추천 금액 (십의 자리 반올림)
     */
    private Long calculateCategoryAmount(Long totalCategoryBudgetOfThisMonth, Long totalBudgetOfThisMonth, Long totalAvailableExpenses) {
        return round(((totalCategoryBudgetOfThisMonth / (double)totalBudgetOfThisMonth) * totalAvailableExpenses) / 100.0) * 100;
    }

}
