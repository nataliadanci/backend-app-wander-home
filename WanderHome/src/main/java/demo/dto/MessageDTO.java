package demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class MessageDTO implements Serializable {

    @JsonProperty("message_id")
    private int messageId;

    @JsonProperty("content")
    private String content;

    //TODO: SENDER_ID ; RECEIVER_ID ; SENT_AT ??


}
