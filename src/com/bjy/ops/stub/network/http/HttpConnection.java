
package com.bjy.ops.stub.network.http;

public class HttpConnection {

    public void cancelAll() {
    	HttpScheduler.getInstance().cancel(this);
    }

}
