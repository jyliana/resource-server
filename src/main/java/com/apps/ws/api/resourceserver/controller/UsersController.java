package com.apps.ws.api.resourceserver.controller;

import com.apps.ws.api.resourceserver.response.UserRest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UsersController {

  Environment env;

  @GetMapping("/status/check")
  public String status() {
    var port = env.getProperty("local.server.port");
    log.info("request was accepted on port: " + port);
    return "Working on port: " + port;
  }

  @PreAuthorize("hasAuthority('ROLE_developer') or #id == #jwt.subject")
  //@Secured("ROLE_developer")
  @DeleteMapping(path = "/{id}")
  public String deleteUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
    return "Deleted user with id " + id + " and JWT subject " + jwt.getSubject();
  }


  @PostAuthorize("returnObject.id == #jwt.subject")
  @GetMapping(path = "/{id}")
  public UserRest getUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
    return new UserRest("Name", "LastName", "5f3fb480-f86c-4514-8d23-ca88d66c6253");
  }
}
