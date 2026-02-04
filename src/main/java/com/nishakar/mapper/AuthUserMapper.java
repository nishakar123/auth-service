package com.nishakar.mapper;

import com.nishakar.commons.dto.AuthUserDTO;
import com.nishakar.entity.AuthUser;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface AuthUserMapper {

    AuthUser map(AuthUserDTO authUserDTO);

    AuthUserDTO entityTODTO(AuthUser authUser);

    List<AuthUserDTO> entityListToDTOList(List<AuthUser> authUserList);
}
