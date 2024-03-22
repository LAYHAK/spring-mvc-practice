package istad.co.samplespringmvc.dto;

import lombok.Builder;

// for user input
@Builder
public record CategoryRequest(String title,
                              String description
                             ) {
}
