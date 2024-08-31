package com.bdgh.examsystem.dto.Teacher;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherBasicDto {
    Long id;
    String ho;
    String ten;
    String title;
}
