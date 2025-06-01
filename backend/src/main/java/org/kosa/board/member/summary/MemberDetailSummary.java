package org.kosa.board.member.summary;

import java.time.LocalDate;

import org.kosa.board.common.enums.Gender;

public interface MemberDetailSummary {
    String getId();
    String getName();
    String getEmail();
    LocalDate getBirthDate();
    Gender getGender();
    String getPhoneNumber();
    String getPostCode();
    String getAddress();
    String getDetailAddress();
}
