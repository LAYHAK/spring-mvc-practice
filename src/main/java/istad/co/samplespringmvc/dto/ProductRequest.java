package istad.co.samplespringmvc.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

// for user input
@Builder
public record ProductRequest(  String title,
         String description,
         float price,
         String imageUrl) {
}
