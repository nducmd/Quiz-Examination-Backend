package com.bdgh.examsystem.dto.Result;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResultBasicDto {
    Long id;
    Long soCauDung;
}
