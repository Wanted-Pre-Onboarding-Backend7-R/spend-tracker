package com.wanted.spendtracker.global.external.discord;

import com.wanted.spendtracker.domain.category.dto.CategoryAmountResponse;
import com.wanted.spendtracker.domain.category.repository.CategoryRepository;
import com.wanted.spendtracker.domain.expenses.dto.response.ExpensesNotificationResponse;
import com.wanted.spendtracker.domain.expenses.dto.response.ExpensesRecommendResponse;
import com.wanted.spendtracker.domain.expenses.service.ExpensesConsultService;
import com.wanted.spendtracker.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WebHookService {

    @Value("${discord.webhookURL}")
    private String webhookURL;
    private final ExpensesConsultService expensesConsultService;
    private final CategoryRepository categoryRepository;

    /**
     * 오늘의 지출 추천 디스코드 웹훅 알림 기능
     * @param member 인증된 사용자
     */
    public void webHookRecommendExpenses(Member member) {
        ExpensesRecommendResponse expensesRecommendResponse = expensesConsultService.recommendExpenses(member);
        String content = createRecommendationContent(member.getAccountName(), expensesRecommendResponse);
        send(content);
    }

    /**
     * 오늘의 지출 안내 디스코드 웹훅 알림 기능
     * @param member 인증된 사용자
     */
    public void webHookNotifyExpenses(Member member) {
        ExpensesNotificationResponse expensesNotificationResponse = expensesConsultService.notifyExpenses(member);
        String content = createNotificationContent(member.getAccountName(), expensesNotificationResponse);
        send(content);
    }

    /**
     * 지출 추천의 디스코드 웹훅 알림을 위해 알림내용(content)을 생성하는 메소드
     * @param accountName 인증된 사용자의 이름
     * @param expensesRecommendResponse 지출 추천 내역을 담고 있는 dto
     * @return String 타입의 알림 내용(content)
     */

    private String createRecommendationContent(String accountName, ExpensesRecommendResponse expensesRecommendResponse) {
        StringBuilder commentBuilder = new StringBuilder();

        String startComment = "## 💡 " + accountName + "님, 오늘의 추천 지출 내역입니다.";
        commentBuilder.append(startComment).append("\n");

        Long recommendAmount = expensesRecommendResponse.getTotalAvailableExpenses();
        String recommendAmountComment = "### 💵 추천 지출 총 금액 : `" + recommendAmount + "`원";
        commentBuilder.append(recommendAmountComment).append("\n");

        commentBuilder.append("### 카테고리별 추천 지출 금액 👛").append("\n");
        List<CategoryAmountResponse> availableExpensesByCategoryList = expensesRecommendResponse.getAvailableExpensesByCategoryList();
        for (CategoryAmountResponse availableExpenses : availableExpensesByCategoryList) {
            commentBuilder.append("\n");
            String categoryName = categoryRepository.getCategoryNameById(availableExpenses.getCategoryId());
            Long amount = availableExpenses.getAmount();
            String recommendComment =
                    "> 📂 카테고리 : `" + categoryName + "`\n"
                            + "> 🪙 추천 금액 : `" + amount + "`원";

            commentBuilder.append(recommendComment).append("\n");
        }

        commentBuilder.append("\n").append("(사용자들의 총 예산 금액 대비 카테고리별 예산 금액의 평균 비율을 적용하여 추천합니다.)").append("\n");

        return commentBuilder.toString();
    }

    /**
     * 지출 안내의 디스코드 웹훅 알림을 위해 알림 내용(content)을 생성하는 메소드
     * @param accountName 인증된 사용자의 이름
     * @param expensesNotificationResponse 지출 안내 내역을 담고 있는 dto
     * @return String 타입의 알림 내용(content)
     */
    private String createNotificationContent(String accountName, ExpensesNotificationResponse expensesNotificationResponse) {
        StringBuilder commentBuilder = new StringBuilder();

        String startComment = "## 📣 " + accountName + "님, 오늘의 지출 내역입니다.";
        commentBuilder.append(startComment).append("\n");

        Long totalExpenses = expensesNotificationResponse.getTodayTotalExpenses();
        String totalExpensesComment = "### 총 지출 금액 : `" + totalExpenses + "`원 💸";
        commentBuilder.append(totalExpensesComment).append("\n");

        commentBuilder.append("### 카테고리별 지출 금액 👛").append("\n");
        List<CategoryAmountResponse> todayTotalCategoryExpensesList = expensesNotificationResponse.getTodayTotalCategoryExpensesList();
        for (CategoryAmountResponse categoryExpenses : todayTotalCategoryExpensesList) {
            commentBuilder.append("\n");
            String categoryName = categoryRepository.getCategoryNameById(categoryExpenses.getCategoryId());
            Long amount = categoryExpenses.getAmount();
            String recommendComment =
                    "> 📂 카테고리 : `" + categoryName + "`\n"
                            + "> 🪙 지출 금액 : `" + amount + "`원";

            commentBuilder.append(recommendComment).append("\n");
        }

        Long availableExpenses =  expensesNotificationResponse.getExpensesRatioResponse().getAvailableExpenses();
        Long todayTotalExpenses = expensesNotificationResponse.getTodayTotalExpenses();
        Double ratioOfExpenses = expensesNotificationResponse.getExpensesRatioResponse().getRatioOfExpenses();
        commentBuilder.append("\n").append(" 오늘의 예산 금액 : `" + availableExpenses + "`원").append("\n");
        commentBuilder.append(" 오늘의 지출 금액 : `" + todayTotalExpenses + "`원").append("\n");
        commentBuilder.append("### 📊 예산 대비 지출 비율 : " + ratioOfExpenses + " ﹪").append("\n");

        return commentBuilder.toString();
    }

    /**
     * 디스코드 봇에 데이터 전송
     * @param content 전송할 내용
     */
    private void send(String content) {
        JSONObject data = new JSONObject();
        data.put("content", content);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(data.toString(), headers);
        restTemplate.postForObject(webhookURL, entity, String.class);
    }

}
