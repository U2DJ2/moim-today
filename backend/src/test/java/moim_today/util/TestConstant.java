package moim_today.util;

import moim_today.domain.member.enums.Gender;
import moim_today.persistence.entity.member.MemberJpaEntity;

import java.time.LocalDate;

public enum TestConstant {

    EMAIL("email"),
    AJOU_EMAIL("test@ajou.ac.kr"),
    WRONG_EMAIL("wrongEmail"),
    ALREADY_EXIST_EMAIL("alreayExist@ajou.ac.kr"),

    PASSWORD("password"),
    NEW_PASSWORD("newPassword"),
    WRONG_PASSWORD("wrongPassword"),

    EVERY_TIME_ID("everyTimeId"),
    WRONG_EVERY_TIME_ID("wrong everyTimeId"),

    CERTIFICATION_TOKEN("certificationToken"),
    NEW_CERTIFICATION_TOKEN("newCertificationToken"),
    WRONG_CERTIFICATION_TOKEN("wrongCertificationToken"),

    FILE_NAME("file"),
    ORIGINAL_FILE_NAME("file.txt"),
    FILE_CONTENT("content"),

    UNIVERSITY_NAME("universityName"),
    DEPARTMENT_NAME("departmentName"),
    USERNAME("username"),
    STUDENT_ID("studentId"),
    PROFILE_IMAGE_URL("profileImageUrl"),
    UNIV_ID("194"),
    DEPARTMENT_ID("9369"),

    STATUS_CODE("200"),
    MESSAGE("message"),

    LOCAL_DATE_FORMAT("yyyy-MM-dd");

    private final String value;

    TestConstant(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
