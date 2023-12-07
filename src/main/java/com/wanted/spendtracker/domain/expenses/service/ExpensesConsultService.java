package com.wanted.spendtracker.domain.expenses.service;

import com.wanted.spendtracker.domain.expenses.dto.response.ExpensesRecommendResponse;
import com.wanted.spendtracker.domain.expenses.repository.ExpensesRepository;
import com.wanted.spendtracker.domain.member.domain.Member;
import com.wanted.spendtracker.domain.category.dto.CategoryAmountResponse;
import com.wanted.spendtracker.domain.expenses.dto.response.ExpensesRatioResponse;
import com.wanted.spendtracker.domain.expenses.dto.response.ExpensesNotificationResponse;
import com.wanted.spendtracker.global.exception.CustomException;
import com.wanted.spendtracker.domain.budget.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.wanted.spendtracker.global.exception.ErrorCode.BUDGET_NOT_EXISTS;
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
     * @param member 인증된 사용자
     * @return 추천 지출 총액과 카테고리 별 추천 지출 금액
     */
    @Transactional(readOnly = true)
    public ExpensesRecommendResponse recommendExpenses(Member member) {
        LocalDate currentDate = LocalDate.now();
        Long totalBudgetOfThisMonth = getTotalBudgetOfThisMonth(member, currentDate);
        Long totalAvailableExpenses = createTotalAvailableExpenses(member, totalBudgetOfThisMonth, currentDate);
        List<CategoryAmountResponse> availableExpensesByCategoryList = createAvailableExpensesByCategory(member, currentDate, totalBudgetOfThisMonth, totalAvailableExpenses);
        return ExpensesRecommendResponse.of(totalAvailableExpenses, availableExpensesByCategoryList);
    }

    /**
     * 오늘의 지출 안내
     * @param member 인증된 사용자
     * @return 오늘 쓴 지출 총액, 카테고리 별 지출 총액 및 오늘 지출 가능한 금액 대비 실제 쓴 지출의 비율
     */
    @Transactional(readOnly = true)
    public ExpensesNotificationResponse notifyExpenses(Member member) {
        LocalDate currentDate = LocalDate.now();
        Long todayTotalExpenses = getTodayTotalExpenses(member, currentDate);
        List<CategoryAmountResponse> todayTotalCategoryExpensesList = expensesRepository.getTodayTotalCategoryExpenses(member, currentDate);
        ExpensesRatioResponse expensesRatioResponse = createExpensesRatioResponse(member, currentDate, todayTotalExpenses);
        return ExpensesNotificationResponse.of(todayTotalExpenses, todayTotalCategoryExpensesList, expensesRatioResponse);
    }

    private Long getTotalBudgetOfThisMonth (Member member, LocalDate currentDate) {
        return budgetRepository.getTotalBudgetAmountByMonth(member.getId(), currentDate.getMonthValue())
                .orElseThrow(() -> new CustomException(BUDGET_NOT_EXISTS));
    }

    private Long getExpensesUntilToday (Member member, LocalDate currentDate) {
        if(expensesRepository.getTotalExpensesAmountUntilToday(member, currentDate) == null) {
            return 0L;
        } else return expensesRepository.getTotalExpensesAmountUntilToday(member, currentDate);
    }

    private Long getTodayTotalExpenses(Member member, LocalDate currentDate) {
        if(expensesRepository.getTotalExpensesByToday(member.getId(), currentDate) == null) {
            return 0L;
        } else return expensesRepository.getTotalExpensesByToday(member.getId(), currentDate);
    }

    private List<CategoryAmountResponse> getTotalCategoryBudgetOfThisMonth(Member member, LocalDate currentDate) {
        return budgetRepository.getTotalCategoryAmountByMonth(member.getId(), currentDate.getMonthValue());
    }

    private Long createTotalAvailableExpenses(Member member, Long totalBudgetOfThisMonth, LocalDate currentDate) {
        long daysLeftOfThisMonth = calculateDaysLeft(currentDate);
        Long totalExpensesUntilToday = getExpensesUntilToday(member, currentDate);
        return calculateTotalAvailableExpenses(totalBudgetOfThisMonth, totalExpensesUntilToday, daysLeftOfThisMonth);
    }

    /**
     * 카테고리 별 추천 지출 금액을 반환하는 메소드
     * @param totalBudgetOfThisMonth 이번 달 총 예산 금액
     * @param totalAvailableExpenses 이번 달 사용 가능한 지출 금액
     * @return 카테고리 별 추천 지출 금액
     */
    private List<CategoryAmountResponse> createAvailableExpensesByCategory(Member member,
                                                                           LocalDate currentDate,
                                                                           Long totalBudgetOfThisMonth,
                                                                           Long totalAvailableExpenses) {
        List<CategoryAmountResponse> totalCategoryBudgetOfThisMonth = getTotalCategoryBudgetOfThisMonth(member, currentDate);
        List<CategoryAmountResponse> availableExpensesByCategory = new ArrayList<>();
        for(CategoryAmountResponse CategoryBudgetOfThisMonth : totalCategoryBudgetOfThisMonth) {
            CategoryAmountResponse categoryAmount = CategoryAmountResponse.builder()
                    .categoryId(CategoryBudgetOfThisMonth.getCategoryId())
                    .amount(calculateCategoryAmount(CategoryBudgetOfThisMonth.getAmount(), totalBudgetOfThisMonth, totalAvailableExpenses)).build();
            availableExpensesByCategory.add(categoryAmount);
        }
        return availableExpensesByCategory;
    }

    private ExpensesRatioResponse createExpensesRatioResponse(Member member, LocalDate currentDate, Long todayTotalExpenses) {
        Long totalBudgetOfThisMonth = getTotalBudgetOfThisMonth(member, currentDate);
        Long totalAvailableExpenses = createTotalAvailableExpenses(member, totalBudgetOfThisMonth, currentDate);
        Double RatioOfExpenses = calculateRatioOfExpenses(totalAvailableExpenses, todayTotalExpenses);
        return ExpensesRatioResponse.of(totalAvailableExpenses, todayTotalExpenses, RatioOfExpenses);
    }

    private long calculateDaysLeft(LocalDate currentDate) {
        LocalDate lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
        return  ChronoUnit.DAYS.between(currentDate, lastDayOfMonth) + 1;
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

    /**
     *  오늘 지출 가능한 금액 대비 실제 쓴 지출의 비율을 계산하는 메소드
     * @param availableExpenses 오늘 지출 가능한 금액
     * @param todayExpenses 실제 쓴 지출
     * @return 오늘 지출 가능한 금액 대비 실제 쓴 지출의 비율 (소수점 첫 째 자리 반올림)
     */
    private Double calculateRatioOfExpenses(Long availableExpenses, Long todayExpenses) {
        return (double) round((todayExpenses * 100) / (double) availableExpenses);
    }

}
