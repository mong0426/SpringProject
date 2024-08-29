package com.example.project3.DTO;

import com.example.project3.Entity.Stores;
import lombok.Data;

@Data
public class UserLikeStoreDTO {

    private Long lno;
    private long sno;
    private String likes;
    private String id;
}
