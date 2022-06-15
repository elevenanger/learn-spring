package integration.file;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * @author : liuanglin
 * @date : 2022/6/15 11:31
 * @description :
 * Spring Integration 解析 @MessagingGateway 自动为其生成实现
 */
@MessagingGateway(defaultRequestChannel = "textInChannel")
public interface FileWriterGateway {

    void writeToFile(@Header(FileHeaders.FILENAME) String fileName, String data);

}
