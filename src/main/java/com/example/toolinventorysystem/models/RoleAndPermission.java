package com.example.toolinventorysystem.models;

import com.example.toolinventorysystem.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Document
@Getter
@Setter
public class RoleAndPermission extends BaseModel{
    private RequestMethod httpMethodType;
    private String uri;
    private List<Role> roles;
}
