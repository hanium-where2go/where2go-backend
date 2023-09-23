package hanium.where2go.domain.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

public class CustomerDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DuplicateEmailRequest {
        private String email;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DuplicateEmailResponse{
        private Boolean isDuplicated;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FavorCategoryRequest {
        private Long categoryId;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FavorCategoryResponse {
        private List<Long> favorCategories;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public class FavorLiquorRequest{
        private Long liquorId;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FavorLiquorResponse {
        private List<Long> favorLiquors;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindEmailRequest {
        private String name;
        private String phoneNumber;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindEmailResponse {
        private String email;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        private String email;
        private String password;
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResponse {
        private String accessToken;
        private String refreshToken;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public class InfoRequest {
        private String nickname;
        private String password;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InfoResponse {
        private String name;
        private String nickname;
        private String email;
        private String phoneNumber;
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PointResponse {
        private int point;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResetPasswordRequest {
        private String email;
        private String name;
        private String password;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignupRequest {
        private String email;
        private String password;
        private String name;
        private String phoneNumber;
        private String nickname;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransactionResponse {
        private Page<TransactionDto> transactions;
    }
}
