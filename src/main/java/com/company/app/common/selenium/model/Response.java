package com.company.app.common.selenium.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.openqa.selenium.devtools.v120.network.model.RequestId;

import java.util.concurrent.atomic.AtomicReference;


@Accessors(chain = true)
public class Response {

    private AtomicReference<RequestId> requestIdAtomicReference = new AtomicReference<>();
    private AtomicReference<String> bodyAtomicReference = new AtomicReference<>();

    @Getter
    @Setter
    private String url;
    @Getter
    @Setter
    private String partOfUrl;

    public boolean isReadyToGetBody(RequestId requestId) {
        RequestId innerRequestId = requestIdAtomicReference.get();
        return innerRequestId != null && innerRequestId.toString().equals(requestId.toString());
    }

    public Response setRequestId(RequestId requestId) {
        this.requestIdAtomicReference.set(requestId);
        return this;
    }

    public String getBody() {
        return this.bodyAtomicReference.get();
    }

    public Response setBody(String body) {
        this.bodyAtomicReference.set(body);
        return this;
    }

}