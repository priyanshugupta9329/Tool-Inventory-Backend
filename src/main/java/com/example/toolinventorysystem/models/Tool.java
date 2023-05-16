package com.example.toolinventorysystem.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Document
public class Tool extends BaseModel{
    private UUID toolType;
    private String toolTypeName;
    private Boolean inUse= Boolean.FALSE;
    private Float lifecycle;
    private Integer numberOfResharpen;
    @Pattern(regexp = "\\d+")
    private Integer numberOfTimesUsed;

    // when resharpened, number of times used must be set to zero again
    // lets resharpen after lifecycle is below 60\
    // one tool can be resharpened twice and discard later
    //for every ten times of usage the accuracy decreases by 10 percent

}
