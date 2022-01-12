package com.epam.esm.util;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.TagController;
import com.epam.esm.controller.UserController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParameterDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.QueryParameterDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The class LinkCreator for creating HATEOAS links.
 */
@Component
public class LinkCreator {

    private static final String DELETE = "delete";
    private static final String UPDATE = "update";
    private static final String NEXT_PAGE = "next page";
    private static final String PREVIOUS_PAGE = "previous page";

    /**
     * Add tag HATEOAS links.
     *
     * @param tag the tag DTO
     */
    public void addTagLinks(TagDto tag) {
        tag.add(linkTo(methodOn(TagController.class).getTagById(tag.getId())).withSelfRel());
        tag.add(linkTo(methodOn(TagController.class).deleteTag(tag.getId())).withRel(DELETE));
    }

    /**
     * Add gift certificate HATEOAS links.
     *
     * @param giftCertificate the gift certificate DTO
     */
    public void addGiftCertificateLinks(GiftCertificateDto giftCertificate) {
        giftCertificate.add(linkTo(methodOn(GiftCertificateController.class)
                .getGiftCertificateById(giftCertificate.getId())).withSelfRel());
        giftCertificate.add(linkTo(methodOn(GiftCertificateController.class)
                .updateGiftCertificate(giftCertificate.getId(), giftCertificate)).withRel(UPDATE));
        giftCertificate.add(linkTo(methodOn(GiftCertificateController.class)
                .deleteGiftCertificate(giftCertificate.getId())).withRel(DELETE));
        addTagLinksToGiftCertificate(giftCertificate.getTags());
    }

    /**
     * Add user HATEOAS links.
     *
     * @param user the user DTO
     */
    public void addUserLinks(UserDto user) {
        user.add(linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel());
    }

    /**
     * Add order HATEOAS links.
     *
     * @param order the order DTO
     */
    public void addOrderLinks(OrderDto order) {
        order.add(linkTo(methodOn(OrderController.class).getOrderById(order.getId())).withSelfRel());
        order.add(linkTo(methodOn(OrderController.class).deleteOrder(order.getId())).withRel(DELETE));
        order.getOrderCertificateDetailsDtos().forEach(od -> addGiftCertificateLinks(od.getGiftCertificate()));
    }

    /**
     * Add gift certificate pagination HATEOAS links.
     *
     * @param giftCertificateQueryParameterDto the gift certificate query parameter DTO
     * @param giftCertificatePage              the gift certificate page number
     */
    public void addGiftCertificatePaginationLinks(GiftCertificateQueryParameterDto giftCertificateQueryParameterDto, PagedModel<GiftCertificateDto> giftCertificatePage) {
        if (giftCertificatePage.getMetadata().getTotalPages() > giftCertificateQueryParameterDto.getPage()) {
            int page = giftCertificateQueryParameterDto.getPage() + 1;
            addGiftCertificatePaginationLink(giftCertificateQueryParameterDto, giftCertificatePage, NEXT_PAGE, page);
        }
        if (giftCertificateQueryParameterDto.getPage() > 1) {
            int page = giftCertificateQueryParameterDto.getPage() - 1;
            addGiftCertificatePaginationLink(giftCertificateQueryParameterDto, giftCertificatePage, PREVIOUS_PAGE, page);
        }
    }

    /**
     * Add order pagination HATEOAS links.
     *
     * @param queryParameterDto the query parameter DTO
     * @param orderPage         the order page number
     */
    public void addOrderPaginationLinks(QueryParameterDto queryParameterDto, PagedModel<OrderDto> orderPage) {
        if (orderPage.getMetadata().getTotalPages() > queryParameterDto.getPage()) {
            orderPage.add(linkTo(methodOn(OrderController.class).getAllOrders(queryParameterDto.getPage() + 1,
                    queryParameterDto.getSize(), queryParameterDto.getSortingDirection())).withRel(NEXT_PAGE));
        }
        if (queryParameterDto.getPage() > 1) {
            orderPage.add(linkTo(methodOn(OrderController.class).getAllOrders(queryParameterDto.getPage() - 1,
                    queryParameterDto.getSize(), queryParameterDto.getSortingDirection())).withRel(PREVIOUS_PAGE));
        }
    }

    /**
     * Add tag pagination HATEOAS links.
     *
     * @param queryParameterDto the query parameter DTO
     * @param tagPage           the tag page number
     */
    public void addTagPaginationLinks(QueryParameterDto queryParameterDto, PagedModel<TagDto> tagPage) {
        if (tagPage.getMetadata().getTotalPages() > queryParameterDto.getPage()) {
            tagPage.add(linkTo(methodOn(TagController.class).getAllTags(queryParameterDto.getPage() + 1,
                    queryParameterDto.getSize(), queryParameterDto.getSortingDirection())).withRel(NEXT_PAGE));
        }
        if (queryParameterDto.getPage() > 1) {
            tagPage.add(linkTo(methodOn(TagController.class).getAllTags(queryParameterDto.getPage() - 1,
                    queryParameterDto.getSize(), queryParameterDto.getSortingDirection())).withRel(PREVIOUS_PAGE));
        }
    }

    /**
     * Add user pagination HATEOAS links.
     *
     * @param queryParameterDto the query parameter DTO
     * @param userPage          the user page number
     */
    public void addUserPaginationLinks(QueryParameterDto queryParameterDto, PagedModel<UserDto> userPage) {
        if (userPage.getMetadata().getTotalPages() > queryParameterDto.getPage()) {
            userPage.add(linkTo(methodOn(UserController.class).getAllUsers(queryParameterDto.getPage() + 1,
                    queryParameterDto.getSize(), queryParameterDto.getSortingDirection())).withRel(NEXT_PAGE));
        }
        if (queryParameterDto.getPage() > 1) {
            userPage.add(linkTo(methodOn(UserController.class).getAllUsers(queryParameterDto.getPage() - 1,
                    queryParameterDto.getSize(), queryParameterDto.getSortingDirection())).withRel(PREVIOUS_PAGE));
        }
    }

    private void addTagLinksToGiftCertificate(List<TagDto> tags) {
        if (tags != null) {
            tags.forEach(t ->
                    t.add(linkTo(methodOn(TagController.class).getTagById(t.getId())).withSelfRel()));
        }
    }

    private void addGiftCertificatePaginationLink(GiftCertificateQueryParameterDto giftCertificateQueryParameterDto,
                                                  PagedModel<GiftCertificateDto> giftCertificatePage,
                                                  String linkName, int page) {
        giftCertificatePage.add(Link.of(linkTo(methodOn(GiftCertificateController.class)
                .getAllGiftCertificates(page,
                        giftCertificateQueryParameterDto.getSize(),
                        giftCertificateQueryParameterDto.getName(),
                        giftCertificateQueryParameterDto.getDescription(),
                        giftCertificateQueryParameterDto.getTagNames(),
                        giftCertificateQueryParameterDto.getSortParameter(),
                        giftCertificateQueryParameterDto.getSortingDirection()))
                .toString().replaceAll("\\{.*?\\}", "")).withRel(linkName));
    }
}
