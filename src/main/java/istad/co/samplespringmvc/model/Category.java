package istad.co.samplespringmvc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


// this will be used for the database
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private int id;
    private String title;
    private String description;
//    private int categoryId;
}
