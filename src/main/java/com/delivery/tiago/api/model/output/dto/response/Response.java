package com.delivery.tiago.api.model.output.dto.response;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

	private T data;
	private Object errors;
	

    public void addErrorMsgToResponse(String msgError) {
        ResponseError error = new ResponseError()
        		.setDetails(msgError)
                .setTimestamp(LocalDateTime.now());
        setErrors(error);
    }
}