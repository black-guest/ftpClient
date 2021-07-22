package com.kubernetes.protocol.base;

import java.io.IOException;

public interface ProtocolBase {
    void runProtocol() throws InterruptedException, IOException;
}
