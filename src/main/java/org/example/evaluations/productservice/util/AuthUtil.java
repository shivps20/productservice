package org.example.evaluations.productservice.util;

import org.example.evaluations.productservice.dtos.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthUtil {

    private RestTemplate restTemplate;

    public AuthUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean validateToken(String tokenValue) {

        /**
         *  Make a call to User Service's /users/validate/{token} endpoint to validate the token
         *  Now this API returns a UserDto object in response body so to handle that we can create a UserDto class in product service as well
         */
        ResponseEntity<UserDto> response = restTemplate.getForEntity("http://localhost:8080/users/validate/"+tokenValue, UserDto.class);

        UserDto userDto = response.getBody();
        if (userDto == null) {
            //throw new IllegalArgumentException("Invalid token");
            return false;
        }

        return true;
//        // In a real scenario, implement JWT or other token validation logic here
    }
}
