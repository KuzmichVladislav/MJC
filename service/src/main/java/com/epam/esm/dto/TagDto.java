package com.epam.esm.dto;

import com.epam.esm.entity.GiftCertificate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO Class GiftCertificate for tag DTO object
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDto {

    private long id;
    private String name;
//    List<GiftCertificateDto> giftCertificates = new ArrayList<>();

//    public void addGiftCertificate(GiftCertificateDto giftCertificate){
//        this.getGiftCertificates().add(giftCertificate);
//    }
}
