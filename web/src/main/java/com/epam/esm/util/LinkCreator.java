package com.epam.esm.util;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.TagController;
import com.epam.esm.controller.UserController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LinkCreator {

    private static final String DELETE = "delete";
    private static final String UPDATE = "update";

    public void addTagLinks(TagDto tag) {
        tag.add(linkTo(methodOn(TagController.class).getTagById(String.valueOf(tag.getId()))).withSelfRel());
        tag.add(linkTo(methodOn(TagController.class).deleteTag(String.valueOf(tag.getId()))).withRel(DELETE));
    }

    public void addGiftCertificateLinks(GiftCertificateDto giftCertificate) {
        giftCertificate.add(linkTo(methodOn(GiftCertificateController.class)
                .getGiftCertificateById(String.valueOf(giftCertificate.getId()))).withSelfRel());
        giftCertificate.add(linkTo(methodOn(GiftCertificateController.class)
                .updateGiftCertificate(String.valueOf(giftCertificate.getId()), giftCertificate)).withRel(UPDATE));
        giftCertificate.add(linkTo(methodOn(GiftCertificateController.class)
                .deleteGiftCertificate(String.valueOf(giftCertificate.getId()))).withRel(DELETE));
        addTagLinksToGiftCertificate(giftCertificate.getTags());
    }

    public void addUserLinks(UserDto user) {
        user.add(linkTo(methodOn(UserController.class).getUserById(String.valueOf(user.getId()))).withSelfRel());
    }

    public void addOrderLinks(OrderDto order) {
        order.add(linkTo(methodOn(OrderController.class).getOrderById(String.valueOf(order.getId()))).withSelfRel());
        order.add(linkTo(methodOn(OrderController.class).deleteOrder(String.valueOf(order.getId()))).withRel(DELETE));
        order.getOrderCertificateDetails().forEach(od -> addGiftCertificateLinks(od.getGiftCertificate()));
    }

    private void addTagLinksToGiftCertificate(List<TagDto> tags) {
        if (tags != null) {
            tags.forEach(t ->
                    t.add(linkTo(methodOn(TagController.class).getTagById(String.valueOf(t.getId()))).withSelfRel()));
        }
    }
}
