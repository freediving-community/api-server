package com.freediving.notiservice.adapter.out.persistence;

import com.freediving.common.domain.noti.NotiCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "noti_link")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class NotiLinkJpaEntity {

    @Id
    @Column(name = "link_code", nullable = false)
    private String linkCode;

    @Column(name = "title", nullable = false)
    private String title;


    @Column(name = "description")
    private String description;

    @Column(name = "path_variable_tf", nullable = false)
    private boolean pathVariableTf;

    @Column(name = "path_variable")
    private String pathVariable;

    public static NotiLinkJpaEntity fromNotiCode(NotiCode notiCode) {
        return NotiLinkJpaEntity.builder()
                .linkCode(notiCode.getLinkCode())
                .title(notiCode.getTitle())
                .description(notiCode.getDescription())
                .pathVariableTf(notiCode.isPathVariableTf())
                .pathVariable(notiCode.getPathVariable())
                .build();
    }
}
