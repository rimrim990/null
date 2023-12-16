package com.project.undefined.retrospect.entity;

import com.project.undefined.common.entity.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Retrospect extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String goodPoint;

    @Column(nullable = false)
    private String badPoint;

    @Column(nullable = false)
    private String summary;

    @Column
    private Long stageId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name ="score"))
    private Score score;

    @Builder
    private Retrospect(final String content, final String goodPoint, final String badPoint, final String summary,
            final Long stageId, final Score score) {
        this(null, content, goodPoint, badPoint, summary, stageId, score);
    }
}
