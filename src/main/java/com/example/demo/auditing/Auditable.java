package com.example.demo.auditing;

import java.time.LocalDateTime;

public interface Auditable {
    LocalDateTime getCreatedAt();
}
