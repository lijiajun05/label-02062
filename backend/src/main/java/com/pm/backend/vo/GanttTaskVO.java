package com.pm.backend.vo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class GanttTaskVO {
    private Long id;
    private String name;
    private LocalDate start;
    private LocalDate end;
    private Integer progress;
    private String status;
    private Integer priority;
    private List<GanttTaskVO> children;
}
