package moim_today.domain.university;

import lombok.Builder;
import lombok.Getter;
import moim_today.persistence.entity.university.UniversityJpaEntity;

import java.util.List;

import static moim_today.global.constant.NumberConstant.MAX_SLASH_CNT;
import static moim_today.global.constant.SymbolConstant.*;
import static moim_today.global.constant.UniversityConstant.ASSOCIATE_DEGREE;
import static moim_today.global.constant.UniversityConstant.GRADUATE_DEGREE;

@Getter
public class ExtractUniversity {

    private String link;
    private final String schoolName;
    private final String schoolType;

    @Builder
    public ExtractUniversity(final String link, final String schoolName, final String schoolType) {
        this.link = link;
        this.schoolName = schoolName;
        this.schoolType = schoolType;
    }

    public String extractEmailExtension() {
        link = parseHttp(link);
        link = extractSubstringAfter(WWW.value());
        link = extractSubstringBefore(EMAIL_EXTENSION.value());

        return link;
    }

    private String parseHttp(String link) {
        int slashCnt = 0;
        while (slashCnt < MAX_SLASH_CNT.value()) {
            int index = link.indexOf(SLASH.value());
            if (index == -1) {
                break;
            }
            link = link.substring(index + 1);
            slashCnt++;
        }
        return link;
    }

    private String extractSubstringAfter(final String startString) {
        int startStringIndex = link.indexOf(startString);
        if (startStringIndex != -1) {
            link = link.substring(startStringIndex + startString.length());
        }
        return link;
    }

    private String extractSubstringBefore(final String startString) {
        int startStringIndex = link.indexOf(startString);
        if (startStringIndex != -1) {
            link = link.substring(0, startStringIndex + startString.length());
        }
        return link;
    }

    public boolean checkUniversityType() {
        List<String> universityType = List.of(ASSOCIATE_DEGREE.value(), GRADUATE_DEGREE.value());
        if (universityType.contains(this.schoolType)) {
            return true;
        }
        return false;
    }

    public UniversityJpaEntity toEntity() {
        return UniversityJpaEntity.builder()
                .universityEmail(this.link)
                .universityName(this.schoolName)
                .build();
    }
}
