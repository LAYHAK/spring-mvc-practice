package istad.co.samplespringmvc.dto;


import lombok.Builder;

//return value back to user
@Builder
public record CategoryResponse(int id , String title , String description) {
}
