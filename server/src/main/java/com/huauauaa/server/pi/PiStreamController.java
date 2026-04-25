package com.huauauaa.server.pi;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

/**
 * Streams π as plain text: {@code 3.} plus 100,000 fractional digits from {@code classpath:pi-digits.txt}.
 */
@RestController
@RequestMapping("/api/pi")
public class PiStreamController {

    private final byte[] piUtf8;
    private final long chunkDelayMs;
    private final int chunkSizeBytes;

    public PiStreamController(
            @Value("classpath:pi-digits.txt") Resource piResource,
            @Value("${app.pi-stream.chunk-delay-ms:45}") long chunkDelayMs,
            @Value("${app.pi-stream.chunk-size-bytes:1024}") int chunkSizeBytes)
            throws IOException {
        String text = StreamUtils.copyToString(piResource.getInputStream(), StandardCharsets.UTF_8).trim();
        this.piUtf8 = text.getBytes(StandardCharsets.UTF_8);
        this.chunkDelayMs = Math.max(0, chunkDelayMs);
        this.chunkSizeBytes = Math.max(1, chunkSizeBytes);
    }

    /**
     * Chunked {@code text/plain} response so clients can read π incrementally (100,000 digits after the decimal
     * point, plus {@code 3.}).
     */
    @GetMapping(value = "/stream", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<StreamingResponseBody> streamPi() {
        StreamingResponseBody body =
                outputStream -> {
                    for (int offset = 0; offset < piUtf8.length; offset += chunkSizeBytes) {
                        int len = Math.min(chunkSizeBytes, piUtf8.length - offset);
                        outputStream.write(piUtf8, offset, len);
                        outputStream.flush();
                        if (chunkDelayMs > 0 && offset + len < piUtf8.length) {
                            sleepBetweenChunks();
                        }
                    }
                };
        return ResponseEntity.ok()
                .header(HttpHeaders.CACHE_CONTROL, "no-store")
                .body(body);
    }

    private void sleepBetweenChunks() {
        try {
            Thread.sleep(chunkDelayMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
