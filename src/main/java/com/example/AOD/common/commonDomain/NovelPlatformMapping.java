package com.example.AOD.common.commonDomain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "novel_platform_mapping")
@Getter
@Setter
@NoArgsConstructor
public class NovelPlatformMapping {
    @Id
    private Long id;  // Common의 ID와 동일하게 설정

    @OneToOne
    @MapsId  // ID를 공유하도록 설정
    @JoinColumn(name = "common_id")
    private NovelCommon novelCommon;

    // 플랫폼 ID들 (null이면 해당 플랫폼에 없음)
    private Long naverSeriesId;  // 네이버시리즈의 novel ID
    private Long kakaoPageId;    // 카카오페이지의 novel ID
    private Long ridibooksId;    // 리디북스의 novel ID

    // 추후 다른 플랫폼 추가 시 여기에 추가
    // private Long munpiaId;
    // private Long joareId;

    // 메타 정보
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // 편의 메서드들
    public boolean hasNaverSeries() {
        return naverSeriesId != null && naverSeriesId > 0;
    }

    public boolean hasKakaoPage() {
        return kakaoPageId != null && kakaoPageId > 0;
    }

    public boolean hasRidibooks() {
        return ridibooksId != null && ridibooksId > 0;
    }

    public void setNaverSeriesNovel(Long naverSeriesId) {
        this.naverSeriesId = (naverSeriesId != null && naverSeriesId > 0) ? naverSeriesId : null;
    }

    public void setKakaoPageNovel(Long kakaoPageId) {
        this.kakaoPageId = (kakaoPageId != null && kakaoPageId > 0) ? kakaoPageId : null;
    }

    public void setRidibooksNovel(Long ridibooksId) {
        this.ridibooksId = (ridibooksId != null && ridibooksId > 0) ? ridibooksId : null;
    }

    public void removeFromNaverSeries() {
        this.naverSeriesId = null;
    }

    public void removeFromKakaoPage() {
        this.kakaoPageId = null;
    }

    public void removeFromRidibooks() {
        this.ridibooksId = null;
    }

    @Transient                 // DB 칼럼 X
    public int getPlatformCount() {
        int cnt = 0;
        if (hasNaverSeries()) cnt++;
        if (hasKakaoPage())   cnt++;
        if (hasRidibooks())   cnt++;
        return cnt;
    }
}