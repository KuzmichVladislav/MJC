package com.epam.esm.dto.mapper;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;

public class TagDtoMapper {
    public TagDto toDto(Tag tag) {
        new TagDto();
        return TagDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }

    public Tag toTag(TagDto tagDto) {
        new Tag();
        return Tag.builder()
                .id(tagDto.getId())
                .name(tagDto.getName())
                .build();
    }
}
