
package com.alkemy.ong.dto.response;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactResponseDTO {

    private Long idContact;
    private String name;
    private Integer phone;
    private String email;
    private String message;

}
