package easymall.service;


import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.UUID;

@Service
public class TestServiceImpl{

    @Resource
    private RabbitTemplate rabbitTemplate;

    public String send(String message) {
        // 封装Message，直接发送message对象
        final String corrId = UUID.randomUUID().toString();
        Message newMessage = convertMessage(corrId,message);

        Message result = rabbitTemplate.sendAndReceive("topic-exchange", "order", newMessage);
        String response = "";
        if (result != null) {
            String correlationId = result.getMessageProperties().getCorrelationId();

            // 客户端从回调队列获取消息，匹配与发送消息correlationId相同的消息为应答结果
            if (corrId.equals(correlationId)) {
                // 提取RPC回应内容body
                response = new String(result.getBody());
                System.out.println(response);
            }
        }
        return response;
    }

    /**
     * 将发送消息封装成Message
     *
     * @param message
     * @return org.springframework.amqp.core.Message
     * @Author Liuyongfei
     * @Date 下午1:23 2020/5/27
     **/
    public Message convertMessage(String corrId,String message) {
        MessageProperties mp = new MessageProperties();
        byte[] src = message.getBytes(Charset.forName("UTF-8"));
        // 注意：由于在发送消息的时候，系统会自动生成消息唯一id，因此在这里手动设置的方式是无效的
        // CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        mp.setCorrelationId(corrId);
        mp.setContentType("application/json");
        mp.setContentEncoding("UTF-8");
        mp.setContentLength((long) message.length());
        return new Message(src, mp);
    }
}
