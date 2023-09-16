package hanium.where2go.domain.owner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

public class OwnerDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateRequest {
        @NotBlank
        private String name;
        @NotBlank
        private String password;
        @NotBlank
        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$")
        private String phoneNum;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BusinessNumStatus {
        @NotBlank
        private String businessNum;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DuplicateEmailRequest {
        @NotBlank
        @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i")
        private String email;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DuplicateEmailResponse{
        private Boolean isDuplicated;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateResponse {
        private String name;
        private String email;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PatchRequest {
        private String name;
        @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i")
        private String email;
        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$")
        private String phoneNum;
    }
}
