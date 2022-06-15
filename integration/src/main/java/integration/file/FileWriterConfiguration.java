package integration.file;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;

import java.nio.file.Paths;

/**
 * @author : liuanglin
 * @date : 2022/6/15 11:34
 * @description :
 */
@Configuration
public class FileWriterConfiguration {

    /**
     * 定义 IntegrationFlow
     * @return
     */
    @Bean
    public IntegrationFlow fileWriterFlow() {
        return IntegrationFlows
            // inbound channel
            .from(MessageChannels.direct("textInChannel"))
            // transformer
            .<String,String>transform(String::toUpperCase)
            // 处理文件写入
            .handle(
                Files.outboundAdapter(Paths.get("/Users/liuanglin/data/tmp/integration.txt").toFile())
                    .fileExistsMode(FileExistsMode.APPEND)
                    .appendNewLine(true))
            .get();
    }
}
