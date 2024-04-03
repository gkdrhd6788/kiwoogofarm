package b303.farm.crop.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CropDto {
    private Long id;
    private String name;
    private String thumbnailUrl;
    private String imageUrl;
    private String category;
    private String temperature;
    private String difficulty;
    private String sunshine;
    private String growTime;
    private String height;
    private String waterPeriod;
    private String humidity;
    private Boolean isHydroponics;
    private String growStart;
    private String waterExit;
    private String cropInfo;
    private String waterPeriodInfo;
    private String sunshineInfo;
    private String humidityInfo;
    private String temperatureInfo;
    private String effectInfo;
    private String link;
    private String growInfo;
    private int likes;

    @Builder
    public CropDto(Long id, String name, String thumbnailUrl, String imageUrl, String category, String temperature, String difficulty, String sunshine, String growTime, String height, String waterPeriod, String humidity, Boolean isHydroponics, String growStart, String waterExit, String cropInfo, String waterPeriodInfo, String sunshineInfo, String humidityInfo, String temperatureInfo, String effectInfo, String link, String growInfo, int likes) {
        this.id = id;
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.imageUrl = imageUrl;
        this.category = category;
        this.temperature = temperature;
        this.difficulty = difficulty;
        this.sunshine = sunshine;
        this.growTime = growTime;
        this.height = height;
        this.waterPeriod = waterPeriod;
        this.humidity = humidity;
        this.isHydroponics = isHydroponics;
        this.growStart = growStart;
        this.waterExit = waterExit;
        this.cropInfo = cropInfo;
        this.waterPeriodInfo = waterPeriodInfo;
        this.sunshineInfo = sunshineInfo;
        this.humidityInfo = humidityInfo;
        this.temperatureInfo = temperatureInfo;
        this.effectInfo = effectInfo;
        this.link = link;
        this.growInfo = growInfo;
        this.likes = likes;
    }
}
