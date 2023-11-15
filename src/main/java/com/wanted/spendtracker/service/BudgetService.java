package com.wanted.spendtracker.service;

import com.wanted.spendtracker.domain.Budget;
import com.wanted.spendtracker.domain.Category;
import com.wanted.spendtracker.domain.Member;
import com.wanted.spendtracker.domain.MemberAdapter;
import com.wanted.spendtracker.dto.request.BudgetSetRequest;
import com.wanted.spendtracker.exception.CustomException;
import com.wanted.spendtracker.repository.BudgetRepository;
import com.wanted.spendtracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.wanted.spendtracker.exception.ErrorCode.BUDGET_CATEGORY_NOT_EXISTS;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void setBudget(MemberAdapter memberAdapter, BudgetSetRequest budgetSetRequest) {
        Member member = memberAdapter.getMember();
        List<BudgetSetRequest.BudgetRequest> budgetRequests = budgetSetRequest.getBudgetRequestList();
        for (BudgetSetRequest.BudgetRequest budgetRequest : budgetRequests) {
            Category category = categoryRepository.findById(budgetRequest.getCategoryId())
                    .orElseThrow(() -> new CustomException(BUDGET_CATEGORY_NOT_EXISTS));
            Budget budget = Budget.of(budgetRequest.getAmount(), budgetRequest.getMonth(), member, category);
            budgetRepository.save(budget);
        }

    }

}
