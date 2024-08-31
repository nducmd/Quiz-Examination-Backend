package com.bdgh.examsystem.dto.Student;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentBasicDto {
    Long id;
    String ho;
    String ten;
    String msv;
}
