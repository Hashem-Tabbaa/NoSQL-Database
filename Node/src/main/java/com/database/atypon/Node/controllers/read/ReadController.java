package com.database.atypon.Node.controllers.read;

import com.database.atypon.Node.services.authentication.AuthenticationService;
import com.database.atypon.Node.services.read.ReadService;
import com.database.atypon.Node.utils.response.Response;
import com.database.atypon.Node.utils.response.ResponseType;
import org.springframework.web.bind.annotation.*;

import java.util.Queue;

@RestController
@RequestMapping("/user/read")
public class ReadController {

   private final ReadService readService;
   private final AuthenticationService authenticationService;
   private Queue<Response> responses;
   public ReadController(ReadService readService, AuthenticationService authenticationService) {
       this.readService = readService;
       this.authenticationService = authenticationService;
   }

   @RequestMapping("/document")
    public Response fetchById(@RequestParam String id, @RequestParam String databaseName,
                              @RequestParam String schemaName,
                              @RequestHeader("authorization") String token) {
       //validate input parameters
       if(id == null || id.isEmpty())
           return new Response(ResponseType.ERROR, "Id is null or empty");
       if(databaseName == null || databaseName.isEmpty())
           return new Response(ResponseType.ERROR, "Database name is null or empty");
       if(schemaName == null || schemaName.isEmpty())
           return new Response(ResponseType.ERROR, "Schema name is null or empty");

       //validate token
       if(!authenticationService.isUserToken(token))
           return new Response(ResponseType.ERROR, "You are not a user");

       return readService.fetchById(id, databaseName, schemaName);
   }

    @RequestMapping("/all")
     public Response fetchAll(@RequestParam String databaseName,
                              @RequestParam String schemaName,
                              @RequestHeader("authorization") String token) {
       if(databaseName == null || databaseName.isEmpty())
           return new Response(ResponseType.ERROR, "Database name is null or empty");
       if(schemaName == null || schemaName.isEmpty())
          return new Response(ResponseType.ERROR, "Schema name is null or empty");

       if(!authenticationService.isUserToken(token))
           return new Response(ResponseType.ERROR, "You are not a user");

       return readService.fetchAll(databaseName, schemaName);
    }
}
