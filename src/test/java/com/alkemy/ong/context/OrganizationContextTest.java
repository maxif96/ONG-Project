package com.alkemy.ong.context;


import com.alkemy.ong.dto.OrganizationRequestDTO;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.repository.OrganizationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = InMemoryUserDetails.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class OrganizationContextTest {
    @Autowired
    protected MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    protected OrganizationRepository organizationRepository;

    protected String createRequest(String name, String image, String address, Integer phone,
                                   String email, String welcomeText, String aboutUsText,
                                   String urlFacebook, String urlInstagram, String urlLinkedin) throws JsonProcessingException {

        return objectMapper.writeValueAsString(OrganizationRequestDTO.builder()
                .name(name)
                .image(image)
                .address(address)
                .phone(phone)
                .email(email)
                .welcomeText(welcomeText)
                .aboutUsText(aboutUsText)
                .urlFacebook(urlFacebook)
                .urlInstagram(urlInstagram)
                .urlLinkedin(urlLinkedin)
                .build());
    }

    protected void createOrganization(){
        organizationRepository.save(Organization
                .builder()
                .name("Organization Test")
                .image("imageTest")
                .address("Address Test")
                .phone(465626865)
                .email("mail@test.com")
                .welcomeText("Welcome")
                .aboutUsText("About organization test")
                .build());
    }

   
}
