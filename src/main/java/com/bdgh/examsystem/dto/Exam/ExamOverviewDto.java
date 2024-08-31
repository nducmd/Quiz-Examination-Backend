package com.bdgh.examsystem.dto.Exam;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExamOverviewDto {
    Long soCauHoi;
    Long soNguoiThamGia;
    Long soLuotLamBai;
    Double diemCaoNhat;
    Double diemThapNhat;
    Double diemTrungBinh;
}
