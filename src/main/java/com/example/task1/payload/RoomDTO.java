package com.example.task1.payload;

import lombok.Data;

@Data
public class RoomDTO {
    private Integer number;
    private Integer floor;
    private String size;
    private Integer hotel_id;
}
