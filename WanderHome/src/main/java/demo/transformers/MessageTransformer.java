package demo.transformers;

import demo.dto.MessageDTO;
import demo.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageTransformer implements Transformer<Message, MessageDTO>{

    @Override
    public MessageDTO fromEntity(Message message){
        //null?

        MessageDTO messageDTO = new MessageDTO();

        messageDTO.setMessageId(message.getMessageId());
        messageDTO.setContent(message.getContent());

        return messageDTO;
    }

    @Override
    public Message fromDTO(MessageDTO messageDTO){

        Message messageEntity = new Message();

        messageEntity.setMessageId(messageDTO.getMessageId());
        messageEntity.setContent(messageDTO.getContent());

        return messageEntity;
    }
}
