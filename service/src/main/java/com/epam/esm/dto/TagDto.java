package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

/**
 * DTO Class GiftCertificate for tag DTO object
 */
@Data
@Builder
public class TagDto extends RepresentationModel<TagDto> {

    private long id;
    private String name;

    @JsonCreator
    public TagDto(@JsonProperty("id") long id, @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    public TagDto() {
    }
//    List<GiftCertificateDto> giftCertificates = new ArrayList<>();

//    public void addGiftCertificate(GiftCertificateDto giftCertificate){
//        this.getGiftCertificates().add(giftCertificate);
//    }
}
